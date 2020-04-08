package hs.spring.hsweb;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hs.spring.hsweb.mapper.user.UserMapper;
import hs.spring.hsweb.mapper.vo.user.UserInfoVO;

// Configure 클래스를 상속받았으므로 따로 컨테이너 설정을 하지 않아도 됨
public class DBTest extends Configure {

	@Test
	@Ignore
	public void dbConnect() throws Exception {

		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
		Connection conn = (Connection) dataSource.getConnection();
		System.out.println("성공 : " + conn);
	}

	@Test
	@Ignore
	public void mybatisConnection() throws Exception {

		SqlSession session = (SqlSession) ctx.getBean("sqlSessoinTemplate");
		System.out.println("성공 : " + session);
	}

	// 회원정보 1명 가져오기
	@Test
	@Ignore
	public void selectOneTest() throws Exception {

		UserMapper mapper = (UserMapper) ctx.getBean("userInfoMapper");
		UserInfoVO vo = mapper.selectUserInfoOne("test");
		System.out.println(vo.getUserId());
		System.out.println(vo.getUserPw());
	}

	// 회원정보 삽입
	@Test
	@Ignore
	public void insertTest() throws Exception {
		UserMapper mapper = (UserMapper) ctx.getBean("userInfoMapper");
		UserInfoVO vo = new UserInfoVO();
		vo.setUserId("coconut");
		vo.setUserPw("123456");
		vo.setUserName("코코넛");
		vo.setUserPhone("010-7744-7735");
		mapper.insertUserInfo(vo);
	}

	// 회원정보 삭제
	@Test
	@Ignore
	public void deleteTest() throws Exception {
		UserMapper mapper = (UserMapper) ctx.getBean("userInfoMapper");
		mapper.deleteUserInfo("coconut");
	}

	@Test
	@Ignore
	public void passwordEncoding() {

		BCryptPasswordEncoder pwEncoder = (BCryptPasswordEncoder) ctx
				.getBean("passwordEncoder");

		String pw = "codevang1!";
		String encodedPw1 = pwEncoder.encode(pw);
		String encodedPw2 = pwEncoder.encode(pw);

		System.out.println("원본 : " + pw);
		System.out.println("첫번 째 인코딩 : " + encodedPw1);
		System.out.println("두번 째 인코딩 : " + encodedPw2);
		System.out.println(
				"matches 메소드 사용 비교 : " + pwEncoder.matches(pw, encodedPw1));

	}
}
