package hs.spring.hsweb.mapper.user;

import hs.spring.hsweb.config.annotation.Mapper;
import hs.spring.hsweb.mapper.vo.user.UserRememberMeVO;

@Mapper
public interface UserRememberMeMapper {

	// 토큰 정보 검색
	public UserRememberMeVO selectUserToken(String series);
	// 특정 series 토큰 한 개 삭제
	public void deleteOneToken(String series);
	// 사용자 토큰 전체체 삭제
	public void deleteAllUserToken(String username);
	// 사용자 토큰 업데이트
	public void updateUserToken(UserRememberMeVO rememberMeVO);
	// 메일 인증 정보 업데이트
	public void updateUserCertifying(String series); 
	// 사용자 토큰 첫 등록
	public void insertUserToken(UserRememberMeVO rememberMeVO);
}
