<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<%@ include file="head.html" %>

<body>
<jsp:include page="header.jsp"></jsp:include>

	<div class="page-max-w">
		<div class="w3-container join-login-box">
			<form method="POST" action="Join" name="join" onsubmit="return checkValue();">
				<h3 class="w3-container w3-margin w3-center">회원가입</h3>
				<input class="w3-input" id="email" type="email" name="user_email" placeholder="아이디(이메일주소)" 
						pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required/><br />
			    <span id="check_email_m" class="w3-margin-left">&nbsp;</span><br />
				<input type="button" class="w3-container w3-margin" value="중복확인" id="email" onclick="checkEmail()"/><br />	
							
				<input class="w3-input" type="password" name="user_pw"
					id="pw" onKeyUp="checkPW()" placeholder="비밀번호" pattern=".{6,}" required/><br />
				<input class="w3-input" type="password" name="user_pwchk"
					id="check_pw" onKeyUp="checkPW()" placeholder="비밀번호 확인" pattern=".{6,}" required/><br />
				<span id="check_pw_m" class="w3-margin-left"></span><br />
						
				<input class="w3-input" type="text" name="user_name" placeholder="이름" required/><br />
				
				<%
				String join_msg = request.getParameter("join_msg");
				
				if (join_msg != null && join_msg.equals("0")){
				%>
					<br />
					<p class="msg w3-text-red">시스템오류: 회원가입에 실패했습니다. 다시 시도해 주십시오.</p>
				<%} %>
				<div class="w3-center">
					<input type="submit" class="w3-btn w3-round-large w3-blue-grey w3-bottom-align" value="가입하기" id="submit">
				</div>		
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="js/check.js"></script>
</html>