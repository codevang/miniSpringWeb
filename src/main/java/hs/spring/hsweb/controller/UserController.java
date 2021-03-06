package hs.spring.hsweb.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import hs.spring.hsweb.mapper.vo.user.UserInfoVO;
import hs.spring.hsweb.service.user.UserService;

@Controller
/* 메인, 사용자 관련 */
public class UserController {

	@Autowired
	UserService service;

	/**
	 * 메인 화면
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String main() {

		return "main";
	}

	/**
	 * 메일 인증 접속 시 리다이렉트
	 * 
	 * @return
	 */
	@RequestMapping("/rememberMeCertifying")
	public String rememberMeCertifying() {

		return "redirect:/";
	}

	/**
	 * 로그인 단순 화면 요청
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginView")
	public String loginView(HttpServletRequest request) {

		// 요청 시점의 사용자 URI 정보를 Session의 Attribute에 담아서 전달(잘 지워줘야 함)
		// 로그인이 틀려서 다시 하면 요청 시점의 URI가 로그인 페이지가 되므로 조건문 설정
		String uri = request.getHeader("Referer");
		if (uri != null && !uri.contains("/loginView")) {
			request.getSession().setAttribute("prevPage", request.getHeader("Referer"));
		}
		return "/user/login";
	}

	/**
	 * 회원가입 단순 화면 요청
	 * 
	 * @return
	 */
	@RequestMapping("/registerUserView")
	public String registerUserView() {

		return "/user/registerUser";
	}

	/**
	 * 회원가입 등록 요청
	 * 
	 * @param userInfo : 입력받은 정보 VO
	 * @param model    : 회원가입 결과 메세지 전달
	 * @return
	 */
	@RequestMapping("/user/registerAsk")
	public String registerAsk(@ModelAttribute UserInfoVO userInfo, Model model) {

		// 회원정보 및 디폴트 권한 입력
		boolean result = service.insertUserInfo(userInfo);

		if (result) {
			model.addAttribute("loginMsg", "회원 가입이 완료되었습니다. 로그인해주세요");
			return "/user/login";

		} else {
			model.addAttribute("registerUserMsg", "이미 존재하는 아이디입니다.");
			return "/user/registerUser";
		}
	}

	/**
	 * 회원정보 조회 요청
	 * 
	 * @param prin  : 사용자 권한 객체
	 * @param model : 회원정보 전달
	 * @return
	 */
	@RequestMapping("/user/userInfoAsk")
	public String userInfoAsk(Principal prin, Model model) {

		UserInfoVO userInfo = service.selectUserInfoOne(prin.getName());
		model.addAttribute("userInfo", userInfo);
		return "/user/userInfo";
	}
}