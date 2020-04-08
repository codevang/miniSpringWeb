package hs.spring.hsweb;

import java.util.Arrays;

import javax.mail.internet.MimeMessage;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.util.StringUtils;

public class RememberMeTest extends Configure {

	@Test
	@Ignore
	public void encodeCookie() {

		// 파라미터로 받을 것
		String[] dbTokens = new String[] { "apple", "banana", "pine", "melon" };

		StringBuilder sb = new StringBuilder();

		// Delimeter(:)를 구분자로 하나의 문자열로 합침 (마지막엔 안붙게 함)
		for (int i = 0; i < dbTokens.length; i++) {

			sb.append(dbTokens[i]);

			if (i < dbTokens.length - 1) {
				sb.append(":");
			}
		}

		String value = sb.toString();
		System.out.println(value);

		sb = new StringBuilder(new String(Base64.encode(value.getBytes())));
		System.out.println(sb.toString());
		System.out.println(sb.toString().length());

		if (sb.charAt(sb.length() - 1) == '=') {
			sb.deleteCharAt(sb.length() - 1);
		}
		System.out.println(sb.toString());

		String a = sb.toString();
		System.out.println(a.length() % 4);
		for (int i = 0; i < a.length() % 4; i++) {
			a = a + "=";
		}

		System.out.println(a);

		String decode = new String(Base64.decode(a.getBytes()));
		System.out.println(decode);

		String[] split = decode.split(":");
		System.out.println(Arrays.toString(split));

		String[] utils = StringUtils.delimitedListToStringArray(decode, ":");
		System.out.println(Arrays.toString(utils));

	}

	@Test
	@Ignore
	public void decodeTest() {

		String encodedCookieValue = "YXBwbGU6YmFuYW5hOnBpbmU6bWVsb24";

		// 뒤에 "="을 뺀 만큼 다시 붙여줌 (그렇지 않으면 디코딩이 제대로 안됨)
		for (int i = 0; i < encodedCookieValue.length() % 4; i++) {
			encodedCookieValue = encodedCookieValue + "=";
		}

		// 디코딩할 문자열이 Base64 타입이 맞는지 확인
		if (!Base64.isBase64(encodedCookieValue.getBytes())) {
			throw new InvalidCookieException("Cookie token was not Base64 encoded");
		}

		// 디코딩해서 문자열로 반환
		String decodedCookieValue = new String(
				Base64.decode(encodedCookieValue.getBytes()));

		System.out.println(decodedCookieValue);

		// Delimeter 기준으로 하나씩 분리해서 배열에 담아줌(split 메소드 써도 됨)
		String[] dbTokens = StringUtils.delimitedListToStringArray(decodedCookieValue,
				":");

		if ((dbTokens[0].equalsIgnoreCase("http")
				|| dbTokens[0].equalsIgnoreCase("https"))
				&& dbTokens[1].startsWith("//")) {
			// Assume we've accidentally split a URL (OpenID identifier)
			String[] newTokens = new String[dbTokens.length - 1];
			newTokens[0] = dbTokens[0] + ":" + dbTokens[1];
			System.arraycopy(dbTokens, 2, newTokens, 1, newTokens.length - 1);
			dbTokens = newTokens;
		}

		System.out.println(Arrays.toString(dbTokens));
	}

	@Test
	@Ignore
	public void mailTest() {
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) ctx.getBean("mailSender");

		// 메일 제목, 내용
		String subject = "제목입니당";
		String content = "내용입니당";

		// 보내는 사람
		String from = "codevang@naver.com";

		// 받는 사람
		String[] to = new String[2];
		to[0] = "codevang@naver.com";
		to[1] = "ulantj@naver.com";

		try {
			// 메일 내용 넣을 객체와, 이를 도와주는 Helper 객체 생성
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper mailHelper = new MimeMessageHelper(mail, "UTF-8");

			// 메일 내용을 채워줌
			mailHelper.setFrom(from); // 보내는 사람 셋팅
			mailHelper.setTo(to); // 받는 사람 셋팅
			mailHelper.setSubject(subject); // 제목 셋팅
			mailHelper.setText(content, true); // 내용 셋팅

			// 메일 전송
			mailSender.send(mail);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void decode() {

		String id = "codevang";

		if (Base64.isBase64(id.getBytes())) {
			System.out.println("true");
		} else {
			System.out.println("flase");
		}
	}

	@Test
	public void type() {
		
	}
}