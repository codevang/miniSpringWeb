package hs.spring.hsweb.mapper.board;

import hs.spring.hsweb.config.annotation.Mapper;

@Mapper
/* 게시판 전체 관리 테이블(board_root) 작업 */
public interface RootBoardMapper {

	// 게시판 한글 이름 select
	public String rootBoardSelectName(String boardTableName);
	
}
