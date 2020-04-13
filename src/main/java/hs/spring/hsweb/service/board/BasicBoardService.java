package hs.spring.hsweb.service.board;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import hs.spring.hsweb.mapper.board.BasicBoardMapper;
import hs.spring.hsweb.mapper.vo.board.BasicBoardVO;

/* 기본 형태 게시판 서비스 */
@Service
public class BasicBoardService {

	@Autowired
	BasicBoardMapper mapper;

	/* BASIC 게시판 페이징 상수 */
	private static final int CONTENTS_PER_PAGE = 10;
	private static int PAGING_PER_PAGE = 10;

	/**
	 * BASIC 게시물 리스트 출력 (3개)
	 * 
	 * @param boardName : 게시판 이름
	 * @param page      : 요청된 페이지
	 * @return : 3개의 게시판 리스트 객체
	 */
	public HashMap<String, List<BasicBoardVO>> selectBoardList(String boardName, int page) {

		// 게시판 이름, 시작 게시물, 페이지당 출력 게시물 수
		HashMap<String, Object> boardListSelectInfo = new HashMap<String, Object>();
		boardListSelectInfo.put("boardName", boardName);
		boardListSelectInfo.put("startContentIndex", (page - 1) * CONTENTS_PER_PAGE);
		boardListSelectInfo.put("CONTENTS_PER_PAGE", CONTENTS_PER_PAGE);

		// 메인 리스트 select
		List<BasicBoardVO> mainBoardList = mapper.selectBoardList(boardListSelectInfo);

		// 조회순 리스트 select
		List<BasicBoardVO> bHitBoardList = mapper.selectBoardListOderbyBHit(boardName);

		// 공감순 리스트 select
		List<BasicBoardVO> bGoodBoardList = mapper.selectBoardListOderbyBGood(boardName);

		HashMap<String, List<BasicBoardVO>> boardListsMap = new HashMap<>();
		boardListsMap.put("mainBoardList", mainBoardList);
		boardListsMap.put("bHitBoardList", bHitBoardList);
		boardListsMap.put("bGoodBoardList", bGoodBoardList);

		return boardListsMap;
	}

	/**
	 * BASIC 게시판 페이징 범위 산출
	 * 
	 * @param boardName : 게시판 이름
	 * @param page      : 요청된 페이지
	 * @return : 게시물 0개일 경우 null, 그 외 페이징 범위
	 */
	public int[] basicBoardPaging(String boardName, int page) {

		// 총 게시물 갯수
		int totalCount = mapper.selectBoardCount(boardName).intValue();

		// 게시물 없으면 null
		if (totalCount == 0) {
			return null;
		}

		// 총 페이지 계산
		int totalPage = totalCount / CONTENTS_PER_PAGE;

		// 나머지가 있다면 1페이지 추가
		if (totalCount % CONTENTS_PER_PAGE > 0) {
			totalPage++;
		}

		// 요청된 페이지가 전체 페이지 범위에 있지 않다면(없는 페이지 번호라면)
		if (page > totalPage) {
			return null;
		}

		int startPage = ((page - 1) / PAGING_PER_PAGE) * PAGING_PER_PAGE + 1;
		int endPage = startPage + PAGING_PER_PAGE - 1;

		// 끝 페이지가 총 페이지보다 커지면 총 페이지가 끝 페이지가 됨
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		return new int[] { startPage, endPage };
	}

	/**
	 * 게시물 보기 화면 처리 (1개 select)
	 * 
	 * @param boardName : 게시판 이름
	 * @param bId       : 게시물 번호
	 * @return
	 */
	public BasicBoardVO selectBoardOne(String boardName, int bId) {

		// 게시판 이름, 게시물 번호
		HashMap<String, Object> boardSelectInfo = new HashMap<>();
		boardSelectInfo.put("boardName", boardName);
		boardSelectInfo.put("bId", bId);

		// 조회수 업데이트 (업데이트 먼저 하고 게시물 가져옴)
		try {

			mapper.updateBHit(boardSelectInfo);

			// 조회수 업데이트 예외 발생해도 게시물은 가져갈 수 있게 예외 처리(트랜젝션 불필요)
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		// 게시물 select
		BasicBoardVO boardVO = mapper.selectBoardOne(boardSelectInfo);

		return boardVO;
	}

	/**
	 * BASIC 게시물 작성 (새글쓰기)
	 * 
	 * @param boardName : 게시판 이름
	 * @param boardVO   : 게시물 VO
	 * @return
	 */
	public boolean insertBoardWriterNew(String boardName, BasicBoardVO boardVO) {

		// bTitle, bContent, bUserId, bUsername은 뷰에서 set
		boardVO.setbId(0); // bId auto_increment
		boardVO.setbOrder(0); // 새 글 order 0
		boardVO.setbIndent(0); // 새 글 들여쓰기 0
		boardVO.setbHit(0); // 조회수 0부터 시작
		boardVO.setbGood(0); // 좋아요 0부터 시작
		boardVO.setbHate(0); // 싫어요 0부터 시작
		boardVO.setbDate(new Date()); // 작성 시간

		// 새글의 bGroup 설정 (새글은 Bid와 동일하게 셋팅)
		Integer maxBid = mapper.selectBoardBidMax(boardName);

		// 글이 있을 경우 bGroup = maxBid + 1, 없을 경우 bGroup = 1,
		int bGroup = maxBid != null ? maxBid + 1 : 1;
		boardVO.setbGroup(bGroup);

		// DB Insert (게시판 이름, 새글 정보 VO)
		HashMap<String, Object> boardInsertInfo = new HashMap<String, Object>();
		boardInsertInfo.put("boardName", boardName);
		boardInsertInfo.put("boardVO", boardVO);

		try {
			mapper.insertBoardInfo(boardInsertInfo);
			return true;

			// 롤백 불필요하므로 바로 예외 처리
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * BASIC 게시물 작성 (답글쓰기)
	 * 
	 * @param boardName : 게시판 이름
	 * @param bId       : 부모 게시물 번호
	 * @param boardVO
	 */
	public void insertBoardWriterReply(String boardName, int bId, BasicBoardVO boardVO) {

		// 부모 게시물 정보 파악
		HashMap<String, Object> boardSelectInfo = new HashMap<String, Object>();
		boardSelectInfo.put("boardName", boardName);
		boardSelectInfo.put("bId", bId);
		BasicBoardVO parentVO = mapper.selectBoardOne(boardSelectInfo);

		// bTitle, bContent, bUserId, bUsername은 포함돼있음
		boardVO.setbId(0);
		boardVO.setbGroup(parentVO.getbGroup()); // 부모글과 동일하게 함
		boardVO.setbOrder(parentVO.getbOrder() + 1); // 부모글 + 1
		boardVO.setbIndent(parentVO.getbIndent() + 1); // 부모글 + 1
		boardVO.setbHit(0); // 조회수 0부터 시작
		boardVO.setbGood(0); // 좋아요 0부터 시작
		boardVO.setbHate(0); // 싫어요 0부터 시작
		boardVO.setbDate(new Date()); // 작성시간

		try {
			// 현재 bOrder 기준 같은 그룹의 게시물 bOrder Update (+1씩 해줌)
			HashMap<String, Object> boardBOderUpdateInfo = new HashMap<String, Object>();
			boardBOderUpdateInfo.put("boardName", boardName);
			boardBOderUpdateInfo.put("bGroup", parentVO.getbGroup()); // 자신의 그룹 정보
			boardBOderUpdateInfo.put("bOrder", parentVO.getbOrder() + 1); // 자신의 오더 정보
			mapper.updateBOrderNum(boardBOderUpdateInfo);

			// 답글 Insert
			HashMap<String, Object> boardInsertInfo = new HashMap<String, Object>();
			boardInsertInfo.put("boardName", boardName);
			boardInsertInfo.put("boardVO", boardVO);
			mapper.insertBoardInfo(boardInsertInfo);

			// 로그 처리 후 롤백을 위해 예외를 다시 던져줌
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 공감(bGood), 비공감(bHate) 업데이트 및 현재 값 가져오기
	 * 
	 * @param boardName : 게시판 이름
	 * @param bId       : 게시물 번호
	 * @return : 정상 select 시 숫자, 비정상 select일 경우 null
	 */
	public Integer updateBGoodBHate(String boardName, int bId, String sort) {

		HashMap<String, Object> sortInfo = new HashMap<String, Object>();
		sortInfo.put("boardName", boardName);
		sortInfo.put("bId", bId);
		sortInfo.put("sort", sort); // 공감,비공감 구분자

		try {
			// bGood +1 업데이트
			mapper.updateBGoodBHate(sortInfo);

			// 롤백은 불필요하므로 예외는 바로 처리
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		// 최종 bGood 값 리턴
		return mapper.selectBGoodBHate(sortInfo);
	}

	/**
	 * 게시물 삭제
	 * 
	 * @param boardName : 게시판 이름
	 * @param bId       : 게시물 번호
	 */
	public void deleteBoardContent(String boardName, int bId) {

		HashMap<String, Object> boardContentDeleteInfo = new HashMap<String, Object>();
		boardContentDeleteInfo.put("boardName", boardName);
		boardContentDeleteInfo.put("bId", bId);

		// delete
		mapper.deleteBoardContent(boardContentDeleteInfo);

	}
}