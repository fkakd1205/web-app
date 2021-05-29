package chae.web.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chae.web.dao.GeneralDao;
import chae.web.dto.User;

@WebServlet("/Join")
public class Join extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Join() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ServletContext context = getServletContext();
		Connection conn = (Connection) context.getAttribute("DBConnection");		// DB연결을 위한 객체 생성
		
		User user = new User();
		
		// 입력된 사용자 정보 User 객체에 저장
		user.setEmail(request.getParameter("user_email"));
		user.setPassword(request.getParameter("user_pw"));
		user.setName(request.getParameter("user_name"));
		
		try {
			boolean joinResult = GeneralDao.insertFacilitator(conn, user);

			String join_msg;						// 회원가입 성공여부에 따른 결과 URL저장

			if (joinResult == true) {							// Join성공
				System.out.println("Succeed user registration");
				join_msg = "../JoinLogin/login.jsp";		// login페이지로 이동
			} else {											// DB에 회원정보 INSERT 실패
				System.out.println("Fail user registration");
				join_msg = "../JoinLogin/join.jsp?join_msg=0";		// 회원가입 안내문구 전달
			}
			
			response.sendRedirect(join_msg); 	// 해당 URL로 이동
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}
