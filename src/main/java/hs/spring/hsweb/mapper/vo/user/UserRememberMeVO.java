package hs.spring.hsweb.mapper.vo.user;

import java.util.Date;

/* Remember-me Token VO */
public class UserRememberMeVO {

	private String username;
	private String series;
	private String token;
	private Date lastUsed;
	private String certified;

	public UserRememberMeVO(String username, String series, String token, Date lastUsed,
			String certified) {
		super();
		this.username = username;
		this.series = series;
		this.token = token;
		this.lastUsed = lastUsed;
		this.certified = certified;
	}

	// Mybatis를 위한 빈 생성자 오버로딩
	public UserRememberMeVO() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	public String getCertified() {
		return certified;
	}

	public void setCertified(String certified) {
		this.certified = certified;
	}
}
