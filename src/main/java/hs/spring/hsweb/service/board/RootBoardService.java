package hs.spring.hsweb.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hs.spring.hsweb.mapper.board.RootBoardMapper;

@Service
/* 게시판 관리 작업 */
public class RootBoardService {

	@Autowired
	private RootBoardMapper mapper;

	/**
	 * 테이블 이름에 매핑된 게시판 한글 이름 반환
	 * 
	 * @param boardTableName : 게시판 이름(테이블 이름)
	 * @return
	 */
	public String getBoardName(String boardTableName) {

		return mapper.rootBoardSelectName(boardTableName);
	}
}
