package hs.spring.hsweb.mapper.vo.user;

/* 사용자 정보 VO (user_info 테이블) */
public class UserInfoVO {

	private String userId;
	private String userPw;
	private String userName;
	private String userPhone;
	private String userEmail;
	private String userInitTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserInitTime() {
		return userInitTime;
	}

	public void setUserInitTime(String userInitTime) {
		this.userInitTime = userInitTime;
	}

}
