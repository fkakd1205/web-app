package chae.web.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chae.web.dao.GeneralDao;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ServletContext context = getServletContext();
		Connection conn = (Connection) context.getAttribute("DBConnection");		// DB연결을 위한 객체 생성
		
		String id = request.getParameter("email");			// 입력된 아이디(email)
		String pw = request.getParameter("password");		// 입력된 비밀번호
		String login_msg = null;							// 로그인 성공여부에 따른 결과 URL저장

		HttpSession session = request.getSession(); 		// 로그인 유지를 위한 로그인 정보 session

		int loginResult = GeneralDao.selectPassword(conn, id, pw); 	// 로그인 시도

		if (loginResult == 1) { 							// 로그인 성공
			session.setAttribute("id", id); 				// session에 현재 아이디값 저장
			login_msg = "./index.jsp";
		} else if (loginResult == 0) { 						// 비밀번호 불일치 or 아이디가 존재 x
			System.out.println("The ID does not exist or it is wrong PW");
			login_msg = "./login.jsp?login_msg=0"; 			// 로그인 안내문구 전달
		} else if (loginResult == -1) { 					// DB연결 실패
			System.out.println("DB connection error");
			login_msg = "./login.jsp?login_msg=-1"; 		// 로그인 안내문구 전달
		}

		response.sendRedirect(login_msg); 				// 해당 URL로 이동
		
	}

}
