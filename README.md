# :computer: web-app
웹 개발

## 1. [JoinLogin](./JoinLogin)
#### 회원가입, 로그인 구현   

[회원가입]  
<img src="https://user-images.githubusercontent.com/35367660/120069063-e5ffd500-c0be-11eb-8795-106ffdd62a16.PNG" width="500">
* 회원가입 조건 : 이메일(아이디), 비밀번호, 이름
* 이메일 형식 확인
* 아이디 중복 확인
* 비밀번호 6자 이상 가능
* 비밀번호 입력 확인
* 회원가입 완료 후 로그인 화면으로 이동
* 회원가입 실패 시 안내문구 제시

[로그인]   
<img src="https://user-images.githubusercontent.com/35367660/120069065-e7310200-c0be-11eb-9202-7be2af753ab9.PNG" width="500">
* 로그인 정보(이메일, 비밀번호) 입력되었는지 확인
* 비밀번호 6자리 이상 입력
* 로그인 완료 후 Main(index.jsp) 화면으로 이동
* 로그인 실패 시 안내문구 제시

#### 로그아웃, 회원탈퇴 구현   

[로그아웃, 탈퇴]  
<img src="https://user-images.githubusercontent.com/35367660/120106827-9215ee80-c199-11eb-8bb8-606597ad39b7.PNG" width="500">
* 로그인을 하면 header에 회원 이메일 표시되는데, 여기에 마우스 올려놓으면 드롭다운 펼쳐짐
* 로그아웃 - 현재 로그인 된 user의 세션삭제
* 탈퇴 - DB에서 현재 로그인 된 user의 정보 삭제
* 로그아웃, 탈퇴 후 Main(index.jsp) 화면으로 이동

#### Alert창 구현

[alert창]    
<img src="https://user-images.githubusercontent.com/35367660/120298596-336d8380-c305-11eb-84d2-13ba9e95d583.PNG" width="500">
* 로그아웃, 탈퇴 시 alert창 띄우기
* [SweetAlert](https://sweetalert.js.org/) 사용
* 확인 버튼 누르면 로그아웃 or 탈퇴 O.
* 취소 버튼 누르면 로그아웃 or 탈퇴 X.


## 참고자료
* 폰트 - [인티니티산스 Regular](https://noonnu.cc/font_page/426)
* Alert창 - [SweetAlert](https://sweetalert.js.org/)
