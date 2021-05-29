// 회원가입 여부
var checkedEmail = false;
var checkedPW = false;
var checkedID = false;

// 중복확인을 완료하고 이메일을 수정하면 중복확인 취소
emailInput.addEventListener("change", function(event){
	if (checkedEmail === true){
		checkedEmail = false;
		checkEmailBtn.style.backgroundColor = "white";
		checkEmailBtn.style.color = "black";
		document.getElementById("check_email_m").innerHTML = '';
	}
});

// 이메일 형식 확인
function checkEmailForm(str) {                                                 
     var emailForm = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;

     if(!emailForm.test(str)) {                            
          return false;         
     }                            
     else {                       
          return true;         
     }                            
}

function checkEmail() {
	var email = document.getElementById("email").value;
    var emailM = document.getElementById("check_email_m");
    emailM.style.color = "red";
    
    // 입력값이 있는 지 확인
    if (email === ''){
    	emailM.innerHTML = '이메일을 입력하세요.';
    	return false;
    }
    
    // 이메일 형식 확인
    if (!checkEmailForm(email)) {
    	emailM.innerHTML = '유효한 이메일주소가 아닙니다.';
    	return false;
    }
    
    var xhttp = new XMLHttpRequest();
    
    xhttp.onreadystatechange = function(){
        if(xhttp.readyState == 4 && xhttp.status == 200){
            result = xhttp.responseText;
            if (result === "ok"){
            	checkedEmail = true;
            	emailM.style.color = "green";
            	emailM.innerHTML = '사용 가능한 이메일입니다.';
            } else {
            	checkedEmail = false;
            	email.value = "";		// input창에 입력된 값 삭제
            	emailM.innerHTML = '이미 존재하는 이메일입니다.';
            }
        }
    };
       
    xhttp.open('Post', './CheckEmail', true);
    xhttp.setRequestHeader('Content-Type', 'text/plain; charset=UTF-8');
    xhttp.send(email);
}

//비밀번호 확인
function checkPW() {
	var pw1 = document.getElementById("pw").value;	// 비밀번호
	var pw2 = document.getElementById("check_pw").value;	// 비밀번호 확인
	var checkMsg = document.getElementById("check_pw_m");	// 비밀번호 확인 결과 메세지
	checkMsg.style.color = "red";

	// 비밀번호가 입력되지 않은 경우
	if (pw1 == "") {
		checkMsg.innerHTML = '비밀번호를 입력하세요.';
		checkedPW = false;
	} else if(pw2 == "") {
		checkMsg.innerHTML = '비밀번호 확인을 입력하세요.';
		checkedPW = false;
	}else if (pw1 == pw2) {	// 비밀번호가 서로 같은 경우
		checkMsg.style.color = "green";
		checkMsg.innerHTML = '비밀번호 확인 완료.';
		checkedPW = true;
	} else {
		checkMsg.innerHTML = '비밀번호가 서로 다릅니다. 다시 확인해 주세요.';
		checkedPW = false;
	}
}

//학생 등록 아이디 체크
function checkID() {
	var userID = document.getElementById("user_id").value;
    var userIDM = document.getElementById("check_id_m");
    userIDM.style.color = "red";
    
    // 입력값이 있는 지 확인
    if (userID === ''){
    	userIDM.innerHTML = '아이디를 입력하세요.';
    	return false;
    }
    
    var xhttp = new XMLHttpRequest();
    
    xhttp.onreadystatechange = function(){
        if(xhttp.readyState == 4 && xhttp.status == 200){
            result = xhttp.responseText;
            if (result === "ok"){
            	checkedID = true;
            	userIDM.style.color = "green";
            	userIDM.innerHTML = '사용 가능한 아이디입니다.';
            } else {
            	checkedID = false;
            	userID.value = "";		// input창에 입력된 값 삭제
            	userIDM.innerHTML = '이미 존재하는 아이디입니다.';
            }
        }
    };
       
    xhttp.open('Post', './CheckID', true);
    xhttp.setRequestHeader('Content-Type', 'text/plain; charset=UTF-8');
    xhttp.send(userID);
}

//submit전 모든 입력 조건을 완료했는지 검사
function checkValue() {
	if(emailInput === ""){
		return false;
	}
	
	if (!checkedEmail) {
        alert('이메일을 확인해주세요!');
        return false;
    } else if (!checkedPW){
		alert('비밀번호확인을 해주세요!');
		return false;
	}

    if (checkPW() && checkName()) {
    	return true;
    }

    return true;
}
