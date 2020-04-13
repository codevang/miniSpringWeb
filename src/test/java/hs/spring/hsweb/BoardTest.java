package hs.spring.hsweb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import hs.spring.hsweb.mapper.board.BasicBoardMapper;
import hs.spring.hsweb.mapper.vo.board.BasicBoardVO;

public class BoardTest extends Configure {

	@Test
	@Ignore
	public void insertBoardInfo() {

		BasicBoardMapper mapper = (BasicBoardMapper) ctx.getBean("basicBoardMapper");

		String boardName = "board_usual";

		Integer max = mapper.selectBoardBidMax(boardName);

		System.out.println(max);
	}

	@Test
	@Ignore
	public void selectBoardInfo() {

		BasicBoardMapper mapper = (BasicBoardMapper) ctx.getBean("basicBoardMapper");

		String boardName = "board_info";
		HashMap<String, Object> boardSelectInfo = new HashMap<String, Object>();
		boardSelectInfo.put("boardName", boardName);
		boardSelectInfo.put("bId", 3);

		BasicBoardVO bvo = mapper.selectBoardOne(boardSelectInfo);
		System.out.println(bvo.getbTitle());
		System.out.println(bvo.getbContent());
	}

	@Test
	@Ignore
	public void boardPaging() {

		int contentPerPage = 10;
		int pagingCount = 10;

		int currPage = 153;

		int totalCount = 10000;
		int totalPage = totalCount / contentPerPage;

		if (totalCount % contentPerPage > 0) {
			totalPage++;
		}

		int startPage = ((currPage - 1) / pagingCount) * pagingCount + 1;
		int endPage = startPage + 9;

		if (endPage > totalPage) {
			endPage = totalPage;
		}

		System.out.println("start : " + startPage);
		System.out.println("end : " + endPage);
	}

	@Test
	@Ignore
	public void boardList() {

		BasicBoardMapper mapper = (BasicBoardMapper) ctx.getBean("basicBoardMapper");

		HashMap<String, Object> boardListSelectInfo = new HashMap<String, Object>();
		boardListSelectInfo.put("boardName", "board_usual");
		boardListSelectInfo.put("startContentIndex", 3);
		boardListSelectInfo.put("CONTENTS_PER_PAGE", 5);

		List<BasicBoardVO> voList = mapper.selectBoardList(boardListSelectInfo);

		BasicBoardVO[] vo = voList.toArray(new BasicBoardVO[voList.size()]);

		for (BasicBoardVO voOne : vo) {
			System.out.println("bId : " + voOne.getbGroup());
			System.out.println("bTile : " + voOne.getbTitle());
			System.out.println("bUserId : " + voOne.getbUserId());
			System.out.println("bUsername : " + voOne.getbUsername());
			System.out.println("bDate : " + voOne.getbDate());

			System.out.println("=======================");
		}
	}

	@Test
	@Ignore
	public void date() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		System.out.println(sdf.format(date));
	}

	@Test
	@Ignore
	public void update() {

		BasicBoardMapper mapper = (BasicBoardMapper) ctx.getBean("basicBoardMapper");
		String boardName = "board_usual";
		Integer bGroup = 6;

		HashMap<String, Object> boardBOderUpdateInfo = new HashMap<String, Object>();
		boardBOderUpdateInfo.put("boardName", boardName);
		boardBOderUpdateInfo.put("bGroup", bGroup);
		mapper.updateBOrderNum(boardBOderUpdateInfo);
	}
	
}