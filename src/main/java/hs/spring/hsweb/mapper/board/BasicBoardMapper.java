package hs.spring.hsweb.mapper.board;

import java.util.HashMap;
import java.util.List;

import hs.spring.hsweb.config.annotation.Mapper;
import hs.spring.hsweb.mapper.vo.board.BasicBoardVO;

@Mapper
/* 기본형 게시판 작업 */
public interface BasicBoardMapper {

	// max(bId)
	public Integer selectBoardBidMax(String boardName);

	// count(bId)
	public Long selectBoardCount(String boardName);

	// 게시물 입력
	public void insertBoardInfo(HashMap<String, Object> boardInsertInfo);

	// 게시물 1개 select
	public BasicBoardVO selectBoardOne(HashMap<String, Object> boardSelectInfo);

	// 조회수 업데이트
	public void updateBHit(HashMap<String, Object> boardSelectInfo);

	// 게시물 리스트 select
	public List<BasicBoardVO> selectBoardList(HashMap<String, Object> boardListSelectInfo);

	// 조회순 게시물 리스트 select
	public List<BasicBoardVO> selectBoardListOderbyBHit(String boardName);

	// 공감순 게시물 리스트
	public List<BasicBoardVO> selectBoardListOderbyBGood(String boardName);

	// 게시물 bOrder 정보 update
	public void updateBOrderNum(HashMap<String, Object> boardBOderUpdateInfo);

	// 공감, 비공감 (bGood/bHate) update
	public void updateBGoodBHate(HashMap<String, Object> sortInfo);

	// 공감, 비공감 (bGood/bHate) select
	public Integer selectBGoodBHate(HashMap<String, Object> sortInfo);

	// 게시물 삭제
	public void deleteBoardContent(HashMap<String, Object> boardContentDeleteInfo);
}
