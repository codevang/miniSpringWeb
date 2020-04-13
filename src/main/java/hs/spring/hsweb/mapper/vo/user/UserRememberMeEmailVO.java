package hs.spring.hsweb.mapper.vo.user;

/* Remember-me 인증 메일 내용 */
public class UserRememberMeEmailVO {

	private String ip;
	private String userAgent;
	private String certifyingLink; // 인증 링크 URI
	private String rollbackLink; // 롤백 링크 URI
	private String subject; // 메일 제목
	private String content; // 메일 본문

	public UserRememberMeEmailVO(String ip, String hostname, String certifyingLink,
			String rollbackLink) {

		this.ip = ip;
		this.userAgent = hostname;
		this.certifyingLink = certifyingLink;
		this.rollbackLink = rollbackLink;

		// 제목 삽입
		setSubject();

		// 내용 완성
		setContent();
	}

	// 제목 완성 (추후 프로퍼티로 대체)
	private void setSubject() {

		subject = "HSWEB 자동 로그인 인증 메일입니다.";

	}

	public String getSubject() {
		return subject;
	}

	// 내용 완성 (추후 프로퍼티로 대체)
	private void setContent() {

		content = "<p>IP :&nbsp;" + ip + "</p><p>Agent :&nbsp;" + userAgent + "</p><p><br></p><p>"
				+ "위 주소에서 로그인을 완료하였고, "
				+ "자동 로그인을 요청했습니다.</p><p><br></p><p>인증하기를 원하시면 아래 \"인증하기\"를 클릭해주세요.</p>"
				+ "<p><br></p><p><a href=\"" + certifyingLink
				+ "\" target=\"_blank\" style=\"cursor: "
				+ "pointer; white-space: pre;\" rel=\"noreferrer noopener\">인증하기</a><span></span>"
				+ "</p><p><br></p><p><br></p><p>만약 잘못된 요청이라면 아래 \"인증 정보 삭제하기\"를 클릭해주세요. "
				+ "모든 기기의 자동 로그인이 해제됩니다.</p><p><br></p><p><a href=\"" + rollbackLink
				+ "\" target=\"_blank\" style=\"cursor: "
				+ "pointer;\" rel=\"noreferrer noopener\">인증 정보 삭제하기</a></p><p><br></p><p><br>"
				+ "</p>\r\n" + "\r\n" + "";
	}

	public String getContent() {
		return content;
	}
}