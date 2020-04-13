package hs.spring.hsweb.service.user;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import hs.spring.hsweb.mapper.user.UserRememberMeMapper;
import hs.spring.hsweb.mapper.vo.user.UserRememberMeVO;

@Service
/* 인증 메일에서 '인증하기' 누를 경우 해당 URL을 받아 토큰을 인증해주는 필터 */
public class UserLoginRememberMeCertifyingFilter extends GenericFilterBean {

	@Autowired
	/* 인증 메일 객체 (디코딩 메소드 사용) */
	// 인증 필터와 메일은 한 세트이므로 의존 관계로 엮어줌
	UserLoginRememberMeCertifyingEmail emailSender;

	@Autowired
	/* DB 작업을 위한 mapper */
	UserRememberMeMapper mapper;

	/**
	 * 필터 (인터셉터 필터 직후 작동)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();

		// 이메일에서 링크 클릭했을 경우
		if (uri.equals("/rememberMeCertifying") && request.getParameter("key") != null) {

			// seriesm token 값 추출
			String[] decodedSeries = emailSender.decodeValues(request.getParameter("key"));

			// 정상적인 값일 경우에만 적용
			if (decodedSeries != null && decodedSeries.length == 2) {

				String series = decodedSeries[0];
				String token = decodedSeries[1];

				// token 정보 확인
				UserRememberMeVO rememberVO = mapper.selectUserToken(series);
				if (token.equals(rememberVO.getToken())) {

					// 인증 상태값 업데이트
					try {
						mapper.updateUserCertifying(series);
					} catch (DataAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

		chain.doFilter(request, response);
	}
}
