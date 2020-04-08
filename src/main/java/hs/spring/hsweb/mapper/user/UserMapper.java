package hs.spring.hsweb.mapper.user;

import java.util.List;
import hs.spring.hsweb.config.annotation.Mapper;
import hs.spring.hsweb.mapper.vo.user.UserInfoVO;

@Mapper
public interface UserMapper {
	
	// 회원 존재 여부 확인
	public Integer selectUserInfoCount(String userId);
	// 회원정보 1명 검색
	public UserInfoVO selectUserInfoOne(String userId);
	// 회원정보 전체 검색
	public List<UserInfoVO> selectUserInfoList();
	// 사용자 권한 검색(1명)
	public List<String> selectUserAuthOne(String userId);
	// 회원정보 입력(가입)
	public void insertUserAuthDefault(String UserId);
	// 디폴트 권한 입력(가입) 
	public void insertUserInfo(UserInfoVO vo);
	// 회원정보 삭제(탈퇴)
	public void deleteUserInfo(String userId);
	// 회원정보 수정
	public void updateUserInfo(UserInfoVO vo);

	
}
