package com.khj.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khj.exam.demo.repository.BoardRepository;
import com.khj.exam.demo.vo.Board;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	public Board getBoardById(int boardId) {
		return boardRepository.selectBoardById(boardId);
	}

}
