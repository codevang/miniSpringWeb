package hs.spring.hsweb.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hs.spring.hsweb.mapper.vo.board.BasicBoardVO;
import hs.spring.hsweb.service.board.BasicBoardService;
import hs.spring.hsweb.service.board.RootBoardService;

/* 게시판 처리 */

@Controller
public class BoardController {

	@Autowired
	/* 기본형 게시판 작업 서비스 */
	BasicBoardService bService;

	@Autowired
	/* 게시판 관리 작업 서비스 */
	RootBoardService bRootService;

	/**
	 * 게시판 리스트 화면 요청
	 * 
	 * @param boardName : 게시판 이름
	 * @param page      : 페이지 번호
	 * @param model
	 * @return
	 */
	@RequestMapping("/board/basicBoardList/{boardName}/{page}")
	public String basicBoardList(@PathVariable String boardName, @PathVariable int page,
			Model model) {

		// 게시판 이름 전달
		model.addAttribute("boardViewName", bRootService.getBoardName(boardName));

		// 페이징 처리
		int[] boardPaging = bService.basicBoardPaging(boardName, page);

		// 페이징이 null이 아닐 경우 처리(null일 경우 게시물이 0개인 경우)
		if (boardPaging != null) {

			// 페이징 전달
			model.addAttribute("boardPaging", boardPaging);

			// 리스트 전달
			model.addAttribute("boardList", bService.selectBoardList(boardName, page));
		}

		return "/board/basicBoardList";
	}

	/**
	 * 게시물 보기 화면 처리
	 * 
	 * @param boardName : 게시판 이름
	 * @param bId       : 게시물 번호
	 * @param model
	 * @return
	 */
	@RequestMapping("/board/basicBoardViewer/{boardName}/{bId}")
	public String basicBoardViewer(@PathVariable String boardName, @PathVariable int bId,
			Model model) {

		// 게시판 이름 전달
		model.addAttribute("boardViewName", bRootService.getBoardName(boardName));

		// select
		BasicBoardVO boardVO = bService.selectBoardOne(boardName, bId);

		if (boardVO != null) {
			model.addAttribute("boardVO", boardVO);
		}

		return "/board/basicBoardViewer";
	}

	/**
	 * 게시물 작성 화면 요청
	 * 
	 * @param boardName : 게시판 종류
	 * @param bId       : 새글쓰기(0), 답글쓰기(부모 게시물 bId)
	 * @param model
	 * @return
	 */
	@RequestMapping("/board/basicBoardWriter/{boardName}/{bId}")
	public String basicBoardWriter(@PathVariable String boardName, @PathVariable int bId,
			Model model) {

		// 게시판 이름 전달
		model.addAttribute("boardViewName", bRootService.getBoardName(boardName));

		// 작성할 게시판 정보 전달
		model.addAttribute("boardName", boardName);
		model.addAttribute("bId", bId);

		return "/board/basicBoardWriter";
	}

	/**
	 * 새글쓰기 작업 요청
	 * 
	 * @param boardVO   : 게시물 VO
	 * @param boardName : 게시판 이름
	 * @param model
	 * @return : 성공 시 리다이렉트, 실패 시 다시 작성 화면 전환 및 메세지 전송
	 */
	@RequestMapping("/board/basicBoardWriterAskNew/{boardName}")
	public String basicBoardWriterAskNew(@ModelAttribute BasicBoardVO boardVO,
			@PathVariable String boardName, Model model) {

		// 게시판 이름 전달
		model.addAttribute("boardViewName", bRootService.getBoardName(boardName));

		// Insert
		boolean result = bService.insertBoardWriterNew(boardName, boardVO);

		// Insert 실패 시 메세지 첨부 및 화면 전환
		if (!result) {
			model.addAttribute("boardWriterAskException", false);
			return "/board/basicBoardWriterAskNew/" + boardName;
		}

		return "redirect:/board/basicBoardList/" + boardName + "/1";
	}

	/**
	 * 답글쓰기 작업 요청
	 * 
	 * @param boardVO   : 게시물 VO
	 * @param boardName : 게시판 이름
	 * @param bId       : 부모 게시물 번호
	 * @param model
	 * @return : 성공 시 1페이지로 리다이렉트, 실패 시 다시 작성 화면 전환 및 메세지 전송
	 */
	@RequestMapping("/board/basicBoardWriterAskReply/{boardName}/{bId}")
	public String basicBoardWriterAskReply(@ModelAttribute BasicBoardVO boardVO,
			@PathVariable String boardName, @PathVariable int bId, Model model) {

		// 게시판 이름 전달
		model.addAttribute("boardViewName", bRootService.getBoardName(boardName));

		// Insert
		try {
			bService.insertBoardWriterReply(boardName, bId, boardVO);

			// Insert 실패 시 메세지 첨부 및 화면 전환
		} catch (DataAccessException e) {
			model.addAttribute("boardWriterAskException", false);
			return "/board/basicBoardWriterAskReply/" + boardName + "/" + bId;
		}

		return "redirect:/board/basicBoardList/" + boardName + "/1";
	}

	/**
	 * 공감(bGood) 업데이트 및 현재값 조회 (ajax)
	 * 
	 * @param boardName : 게시판 이름
	 * @param bId       : 게시물 번호
	 * @return : ResponseBody(json)
	 */
	@RequestMapping("/board/basicBoardBGood")
	@ResponseBody
	public Object updateBGood(@RequestParam String boardName, @RequestParam int bId) {

		HashMap<String, Integer> bGood = new HashMap<String, Integer>();
		Integer bGoodNum = bService.updateBGoodBHate(boardName, bId, "bGood");

		if (bGoodNum != null) {
			bGood.put("bGoodCount", bGoodNum);
			return bGood;
		} else {
			return null;
		}
	}

	/**
	 * 비공감(bHate) 업데이트 및 현재값 조회 (ajax)
	 * 
	 * @param boardName : 게시판 이름
	 * @param bId       : 게시물 번호
	 * @return : ResponseBody(json)
	 */
	@RequestMapping("/board/basicBoardBHate")
	@ResponseBody
	public Object updateBHate(@RequestParam String boardName, @RequestParam int bId) {

		HashMap<String, Integer> bHate = new HashMap<String, Integer>();
		Integer bHateNum = bService.updateBGoodBHate(boardName, bId, "bHate");

		if (bHateNum != null) {
			bHate.put("bGoodCount", bHateNum);
			return bHate;
		} else {
			return null;
		}
	}

	/**
	 * 게시물 삭제 요청 처리
	 * 
	 * @param boardName : 게시판 이름
	 * @param bId       : 게시물 번호
	 * @return : 리다이렉트
	 */
	@RequestMapping("board/contentDelAsk")
	public String deleteBoardContent(@RequestParam String boardName, @RequestParam int bId) {

		bService.deleteBoardContent(boardName, bId);

		// 리스트 첫 페이지로 리다이렉트
		return "redirect:/board/basicBoardList/" + boardName + "/" + 1;
	}
}