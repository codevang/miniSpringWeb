package hs.spring.hsweb.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import hs.spring.hsweb.mapper.user.UserMapper;
import hs.spring.hsweb.mapper.vo.user.UserInfoVO;

@Service
/* 회원 정보 관련 작업 */
public class UserService {

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;

	/* 회원 가입(회원 정보 입력) */
	public boolean insertUserInfo(UserInfoVO userInfo) {
		
		// 존재하는 ID 여부 확인
		Integer userCount = mapper.selectUserInfoCount(userInfo.getUserId());
		if (userCount > 0) {
			return false;

		} else {

			// userInfo의 내용 중 패스워드를 암호화시켜서 바꿔줌
			userInfo.setUserPw(pwEncoder.encode(userInfo.getUserPw()));

			
			// 회원정보 및 디폴트 권한 DB 입력
			mapper.insertUserInfo(userInfo);
			mapper.insertUserAuthDefault(userInfo.getUserId());
			return true;
		}
	}
}