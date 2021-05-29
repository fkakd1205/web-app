package chae.web.dto;

public class User {
	private String email;			// email주소(아이디)
	private String password;	// 비밀번호
	private String name;	// 이름
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
