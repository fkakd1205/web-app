package chae.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chae.web.dao.GeneralDao;

@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Withdraw() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		ServletContext context = getServletContext();
		Connection conn = (Connection) context.getAttribute("DBConnection"); // DB연결을 위한 객체 생성

		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("id"); // 현재 로그인 중인 사용자의 아이디

		try {
			conn.setAutoCommit(false);

			int deleteCount = GeneralDao.deleteFacilitatorByEmail(conn, email); // 사용자 정보 삭제
			
			if (deleteCount == 1) {
				conn.commit();
				response.sendRedirect("./Logout");		// 탈퇴 시 로그아웃 실행
			} else {
				throw new Exception("Fail to delete User(" + email + ").");
			}

		} catch (Throwable e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

}
