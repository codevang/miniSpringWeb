package hs.spring.hsweb.service.user;

import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import hs.spring.hsweb.mapper.user.UserRememberMeMapper;
import hs.spring.hsweb.mapper.vo.user.UserRememberMeVO;

public class UserLoginRememberMeService extends AbstractRememberMeServices {

	@Autowired
	/* DB 작업을 위한 mapper */
	UserRememberMeMapper mapper;

	@Autowired
	/* 인증 메일 발송 객체 */
	UserLoginRememberMeCertifyingEmail emailSender;

	/* token값 신규 생성을 위한 랜덤 넘버 생성 객체 */
	SecureRandom random;

	/* 생성자 */
	public UserLoginRememberMeService(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
		random = new SecureRandom();
	}

	@Override
	/* 첫 로그인 시 쿠키 발행 및 토큰정보 DB 업데이트 */
	protected void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication successfulAuthentication) {

		// 사용자 쿠키 겁색
		String cookieValue = super.extractRememberMeCookie(request);

		// 사용자 쿠키가 존재할 경우 DB에서 해당 데이터를 삭제함
		// 2차 인증하지 않으면 자동로그인 되지 못하는 쿠키가 남아있을 수 있음
		if (cookieValue != null) {

			// series 값 기준으로 해당 토큰을 DB에서 삭제
			mapper.deleteOneToken(decodeCookie(cookieValue)[0]);
		}

		// 새로운 series, token 값 생성
		String username = successfulAuthentication.getName();
		String newSeriesValue = generateTokenValue();
		String newTokenValue = generateTokenValue();

		// 쿠키 발급 및 DB insert
		try {
			UserRememberMeVO rememberMeVO = new UserRememberMeVO(username, newSeriesValue,
					newTokenValue, new Date(), null);

			// DB insert
			mapper.insertUserToken(rememberMeVO);

			// 쿠키 발행
			String[] rawCookieValues = new String[] { newSeriesValue, newTokenValue };
			super.setCookie(rawCookieValues, getTokenValiditySeconds(), request, response);

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		// 2차 인증을 위한 메일 발송
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		String userAgent = request.getHeader("user-agent");

		boolean isSended = emailSender.sendSecondCertifyingEmail(username, newSeriesValue,
				newTokenValue, ip, userAgent);

		if (!isSended) {
			request.getSession().setAttribute("rememberMeMsg",
					"메일 전송에 실패했습니다. 등록된 메일 주소를 확인해주세요.");
		}
	}

	@Override
	/* 자동 로그인 로직 - 쿠키 유효성 검증 및 사용자 정보 객체 리턴 */
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response)
			throws RememberMeAuthenticationException, UsernameNotFoundException {

		// 쿠키 : series, token
		// 포함된 값이 2개가 아닌 경우
		if (cookieTokens.length != 2) {
			throw new RememberMeAuthenticationException("잘못된 쿠키");
		}

		String cookieSeries = cookieTokens[0];
		String cookieToken = cookieTokens[1];

		// DB 토큰 정보 확인
		UserRememberMeVO rememberMeVO = mapper.selectUserToken(cookieSeries);

		// DB에 정보가 없을 경우
		if (rememberMeVO == null) {
			throw new RememberMeAuthenticationException("존재하지 않는 series");
		}

		// DB에 series는 있는데 Token 값이 같지 않을 경우
		if (!cookieToken.equals(rememberMeVO.getToken())) {

			// DB에서 해당 데이터 삭제
			mapper.deleteOneToken(cookieSeries);

			throw new CookieTheftException("변조된 쿠키 발견");
		}

		// DB에 series는 있는데 certified가 null인 경우 (메일 인증되지 않은 쿠키)
		if (!rememberMeVO.getCertified().equals("true")) {

			throw new RememberMeAuthenticationException("메일 인증되지 않은 쿠키");
		}

		// 유효기간 검증
		if (rememberMeVO.getLastUsed().getTime()
				+ getTokenValiditySeconds() * 1000L < System.currentTimeMillis()) {

			// DB에서 해당 데이터 삭제
			mapper.deleteOneToken(cookieSeries);
			throw new RememberMeAuthenticationException("유효기간 만료 쿠키");
		}

		// 신규 token 값으로 업데이트
		String newToken = generateTokenValue();
		rememberMeVO.setToken(newToken);
		rememberMeVO.setLastUsed(new Date());

		try {
			// DB에 새로운 token 값 업데이트
			mapper.updateUserToken(rememberMeVO);

			// 변경된 token 값으로 새로운 쿠키 발행
			String[] rawCookieValues = new String[] { cookieSeries, newToken };
			super.setCookie(rawCookieValues, getTokenValiditySeconds(), request, response);

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new RememberMeAuthenticationException("새로운 token DB 업데이트 실패");
		}

		// 모두 인증됐으면 사용자 정보 객체 찾아서 반환 (예외 발생 시 super에서 처리해줌)
		return getUserDetailsService().loadUserByUsername(rememberMeVO.getUsername());
	}

	@Override
	/* 로그아웃 시 쿠키/DB 정보 삭제 */
	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		// 웹에서 모든기기 로그아웃 요청한 경우
		if (request.getParameter("logoutAllWeb") != null) {

			String username = (String) request.getParameter("logoutAllWeb");

			// 권한이 있을 경우에만 로그아웃 작동 (다른 사람이 URI로 직접 요청하는 경우를 막기 위함)
			if (authentication != null && authentication.getName().equals(username)) {
				mapper.deleteAllUserToken(username);
			}

		// 인증 메일에서 모든 기기 로그아웃 요청한 경우
		} else if (request.getParameter("logoutAllEmail") != null) {

			// 디코딩 후 사용자 토큰 데이터 모두 삭제
			String encodedUsername = (String) request.getParameter("logoutAllEmail");
			String[] username = emailSender.decodeValues(encodedUsername);
			if (username != null) {
				mapper.deleteAllUserToken(username[0]);
			}

		// 현재 기기 로그아웃 요청일 경우
		// series 기준으로 DB의 데이터 삭제
		} else {

			// DB token 삭제 (username이 아닌 해당 series의 정보만 삭제)
			String decodedCookieValue = super.extractRememberMeCookie(request);
			if (decodedCookieValue != null) {
				String[] cookieTokens = super.decodeCookie(decodedCookieValue);
				if (cookieTokens != null && cookieTokens.length == 2) {
					mapper.deleteOneToken(cookieTokens[0]);
				}
			}
		}

		// 쿠키 삭제
		super.logout(request, response, authentication);
	}

	/* Series, Token 랜덤값으로 생성후 인코딩 */
	private String generateTokenValue() {
		byte[] newToken = new byte[16];
		random.nextBytes(newToken);
		return new String(Base64.encode(newToken));
	}
}