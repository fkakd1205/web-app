<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	<%
		String id = (String)session.getAttribute("id");
	%>
	<!-- 헤더 메뉴 -->
	<header class="w3-blue-grey">
        <div class="page-max-w w3-display-container">
			<a class="w3-display-left w3-margin-left w3-xxlarge font" onclick="reset()">Chaeeun's Web</a>
			<div class="w3-display-right">
				<a href="join.jsp" class="w3-hide-small w3-padding-small w3-right">${id == NULL ? "회원가입" : ""}</a>
				<a onclick=${id == NULL ? "location='login.jsp'" : ""} class="w3-hide-small w3-padding-small w3-right">${id == NULL ? "로그인": ""}</a>
				<div class="w3-hide-small w3-padding-small w3-dropdown-hover">${id == NULL ? "" : id}
				<% if(id != null) {%>
					<div class="w3-dropdown-content w3-bar-block w3-border w3-center w-100 dropdown-right">
					    <a class="w3-bar-item w3-button" onclick="logout()">로그아웃</a>
					    <a class="w3-bar-item w3-button" onclick="withdraw()">탈퇴</a>
					</div>
				<% } %>
				</div>
			</div>
		
			<!-- 모바일에서의 메뉴 드롭다운 -->
			<div class="w3-dropdown-hover w3-display-right w3-hide-medium w3-hide-large">
				<button class="w3-button w3-padding-large">
					<i class="fa fa-bars header-down"></i>
				</button>
				<div class="w3-dropdown-content w3-bar-block w3-blue-grey w3-right" style="right: 0;">
					<%if(id == null) {%><a href="join.jsp" class="w3-bar-item w3-button w3-padding-large">회원가입</a><%} %>
					<a href=${id == NULL ? "login.jsp" : "./Logout"} class="w3-bar-item w3-button w3-padding-large">${id == NULL ? "로그인": "로그아웃"}</a>
					<%if(id != null) {%><div class="w3-bar-item w3-padding-large"><%=id %></div><%} %>
				</div>
			</div>
		</div>
	</header>
	<!-- 헤더 메뉴 끝 -->