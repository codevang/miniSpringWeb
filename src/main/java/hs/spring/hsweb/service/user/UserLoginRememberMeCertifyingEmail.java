package hs.spring.hsweb.service.user;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import hs.spring.hsweb.mapper.user.UserMapper;
import hs.spring.hsweb.mapper.vo.user.UserInfoVO;
import hs.spring.hsweb.mapper.vo.user.UserRememberMeEmailVO;

@Service
/* Remember-me 2차 인증 메일 전송 */
public class UserLoginRememberMeCertifyingEmail {

	@Autowired
	/* 메일 발송 객체 */
	JavaMailSenderImpl mailSender;

	@Autowired
	/* 사용자 Email 주소를 DB에서 가져오기 위한 mapper */
	UserMapper mapper;

	/* 사용자 정보에 등록된 Email로 자동 로그인 인증 메일을 발송 */
	public boolean sendSecondCertifyingEmail(String username, String series, String token, String ip,
			String userAgent) {

		// DB에서 사용자 정보 가져옴
		UserInfoVO userInfo = mapper.selectUserInfoOne(username);
		if (userInfo == null) {
			return false;
		}

		// 이메일 주소가 비어있을 경우
		if (userInfo.getUserEmail() == null || userInfo.getUserEmail().equals("")) {
			return false;
		}

		// username 인코딩 (타인이 URI에 ID를 넣어서 직접 롤백을 시도하는 것을 막기 위함)
		String encodedUsername = encodeValues(new String[] { username });

		// series, token 정보 인코딩 (노출 방지)
		String encodedSeries = encodeValues(new String[] { series, token });

		// 메일 정보 셋팅
		String from = "codevang@naver.com";
		String to = userInfo.getUserEmail();
		String certifyingLink = "http://localhost//rememberMeCertifying?key="
				+ encodedSeries;
		String rollbackLink = "http://localhost/logoutAsk?logoutAllEmail="
				+ encodedUsername;

		// 메일 내용 VO 객체
		UserRememberMeEmailVO emailVO = new UserRememberMeEmailVO(ip, userAgent,
				certifyingLink, rollbackLink);

		String subject = emailVO.getSubject();
		String content = emailVO.getContent();

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);

			mailSender.send(message);

			// 정상적으로 메일 발송됐으면 true 리턴
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 여기까지 왔다면 예외 발생한 경우
		return false;
	}

	/* username(ID) 인코딩 */
	public String encodeValues(String[] values) {

		// 값들을 붙여서 하나로 만들어 줌
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]);
			if (i < values.length - 1) {
				sb.append(":");
			}
		}

		// 인코딩
		String value = sb.toString();
		sb = new StringBuilder(new String(Base64.encode(value.getBytes())));

		// 뒤에 붙은 '='을 빼줌
		while (sb.charAt(sb.length() - 1) == '=') {
			sb.deleteCharAt(sb.length() - 1);
		}

		// 인코딩된 String 객체 리턴
		return sb.toString();
	}

	/* username 디코딩 */
	public String[] decodeValues(String encodedValue) {

		// 인코딩할 때 빼준 '='을 다시 붙여줌
		for (int j = 0; j < encodedValue.length() % 4; j++) {
			encodedValue = encodedValue + "=";
		}

		// 인코딩 타입 유효성 확인
		if (!Base64.isBase64(encodedValue.getBytes())) {
			return null;
		}

		// 디코딩
		String decodedValue = new String(Base64.decode(encodedValue.getBytes()));

		// 각 값으로 분리
		String[] values = decodedValue.split(":");

		// 디코딩된 문자열 리턴
		return values;
	}
}