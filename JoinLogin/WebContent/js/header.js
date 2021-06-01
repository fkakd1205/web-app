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
	swal({
		  title: "로그아웃",
		  text: "로그아웃하시겠습니까?",
		  icon: "warning",
		  buttons: ["취소", "확인"],
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
			  window.location.href = "./Logout";
		  }
		});
}

//탈퇴
function withdraw() {
	swal({
		  title: "탈퇴",
		  text: "정말로 탈퇴 하시겠습니까?",
		  icon: "warning",
		  buttons: ["취소", "확인"],
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
			  window.location.href = "./Withdraw";
		  }
		});
}