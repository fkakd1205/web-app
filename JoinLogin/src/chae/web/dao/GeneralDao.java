package chae.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chae.web.dto.User;

public class GeneralDao {
	/* web_user */
	// 아이디 중복확인
	private final static String SQLST_SELECT_EMAIL = "SELECT user_email FROM web_user WHERE user_email = ?";
	// 회원가입
	private final static String SQLST_INSERT_FACILITATOR = "INSERT INTO web_user(user_email, user_pw, user_name) VALUES(?, ?, ?)";
	// 로그인
	private final static String SQLST_SELECT_PASSWORD = "SELECT user_pw FROM web_user WHERE user_email =?";

	/* 주어진 email이 DB내 존재하는지 확인(아이디 중복확인)
	 * 
	 * 주어진 email이 DB내 존재하지 않을 경우 return true
	 * 주어진 email이 DB내 존재하거나 쿼리 실행 실패 시 return false
	 */
	public static boolean selectEmail(Connection conn, String email) {

		try (PreparedStatement pstmt = conn.prepareStatement(SQLST_SELECT_EMAIL);) {

			pstmt.setString(1, email);
			
			try (ResultSet rs = pstmt.executeQuery();) {
	
				if (rs.next()) {
					System.out.println("This id(e-mail) already exist.");
					return false;
				} else {
					return true; // 사용가능한 아이디(email)
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/* 주어진 사용자 정보를 DB에 삽입(회원가입)
	 * 
	 * 성공 시 return true
	 * 실패 시 return false
	 */
	public static boolean insertFacilitator(Connection conn, User user) {

		try (PreparedStatement pstmt = conn.prepareStatement(SQLST_INSERT_FACILITATOR);) {

			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());

			int insertCount = pstmt.executeUpdate();

			if (insertCount == 1) { // 회원정보 삽입에 성공한다면
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/* 주어진 email의 password 정보를 가져오기(로그인)
	 * 
	 * 가져온 password가 파라미터 password와 같을 때 return 1
	 * 가져온 password가 파라미터 password와 다를 때 return 0
	 * 주어진 email이 DB에 존재하지 않을 때 return 0
	 * 쿼리문 실행 실패 시 return -1
	 */
	public static int selectPassword(Connection conn, String email, String password) {

		try (PreparedStatement pstmt = conn.prepareStatement(SQLST_SELECT_PASSWORD);) {

			pstmt.setString(1, email);
			
			try (ResultSet rs = pstmt.executeQuery();) {

				if (rs.next()) { // 입력된 아이디에 해당되는 정보가 있을 경우
					if (rs.getString(1).equals(password)) { // 비밀번호가 입력받은 값(email)과 같다면
						return 1; // 로그인 성공
					} else {
						return 0; // 비밀번호 불일치
					}
				}
				return 0; // ID가 존재하지 않음
			
			} catch (Exception e) {
				e.printStackTrace();
				return -1; // DB 연결 오류
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1; // DB 연결 오류
		}
	}
}
