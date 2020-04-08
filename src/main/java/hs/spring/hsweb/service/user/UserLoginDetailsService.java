package hs.spring.hsweb.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import hs.spring.hsweb.mapper.user.UserMapper;
import hs.spring.hsweb.mapper.vo.user.UserDetailsVO;
import hs.spring.hsweb.mapper.vo.user.UserInfoVO;

@Service
public class UserLoginDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String inputUserId) {

		// 최종적으로 리턴해야할 객체
		UserDetailsVO userDetails = new UserDetailsVO();

		// 사용자 정보 select
		UserInfoVO userInfo = mapper.selectUserInfoOne(inputUserId);

		// 사용자 정보 없으면 예외 처리
		if (userInfo == null) {
			return null;

			// 사용자 정보 있을 경우 로직 전개 (userDetails에 데이터 넣기)
		} else {
			userDetails.setUsername(userInfo.getUserId());
			userDetails.setPassword(userInfo.getUserPw());
			userDetails.setUserFullName(userInfo.getUserName());

			// 사용자 권한 select해서 받아온 List<String> 객체 주입
			userDetails.setAuthorities(mapper.selectUserAuthOne(inputUserId));
		}

		return userDetails;
	}
}
