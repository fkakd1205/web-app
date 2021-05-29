<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<%@ include file="head.html" %>

<body>
<jsp:include page="header.jsp"></jsp:include>

	<div class="page-max-w">
		<div class="w3-container join-login-box login-box w3-center">
			<form name="login" method="POST" action="Login">
				<h3 class="w3-center">회원 로그인</h3>
				<input class="w3-input" type="email" name="email"
					maxlength="45" placeholder="ex) ce@ce.com" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required><br />
				<input class="w3-input" type="password" name="password"
					maxlength="45" placeholder="비밀번호 입력" pattern=".{6,}" required><br />
				<%
					String login_msg = request.getParameter("login_msg");

					if (login_msg != null && login_msg.equals("0")) {
						out.println("<p class=\"w3-text-red\">등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.</p>");
					} else if (login_msg != null && login_msg.equals("-1")) {
						out.println("<br />");
						out.println("<p>시스템 오류: DB에 연결할 수 없습니다.</p>");
					}
				%>
				<div class="w3-center">
					<input type="submit" class="w3-btn w3-round-large w3-blue-grey w3-bottom-align" value="로그인"">
					<br /><br />
					<a href="join.jsp" class="w3-padding-small w3-center w3-button w3-hover-white w3-hide-small">회원가입</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>