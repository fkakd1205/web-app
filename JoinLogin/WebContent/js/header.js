function myFunction() {
	var x = document.getElementById("mobileMenu");
	if (x.className.indexOf("w3-show") == -1) {
		x.className += " w3-show";
	} else {
		x.className = x.className.replace(" w3-show", "");
	}
}

//좌측 상단 헤더의 'Chaeeun's Web'을 눌렀을 경우
function reset() {
	window.location.href = "./index.jsp";
}

//로그아웃
function logout() {
	window.location.href = "./Logout";
}

//탈퇴
function withdraw() {
	window.location.href = "./Withdraw";
}