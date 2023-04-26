package com.khj.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khj.exam.demo.repository.ReactionPointRepository;
import com.khj.exam.demo.vo.ResultData;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;
	
	public boolean actorCanMakeReactionPoint(int loginedMemberId,String relTypeCode, int relId) {
		if(loginedMemberId == 0) return false;
		
		return reactionPointRepository.actorCanMakeReactionPoint(loginedMemberId,relTypeCode,relId) == 0;
	}

	public ResultData addGoodReactionPoint(int loginedMemberId, String relTypeCode, int relId) {
		reactionPointRepository.addGoodReactionPoint(loginedMemberId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article": 
			articleService.increaseGoodReactionPoint(relId);
		}
		
		return ResultData.from("S-1", "좋아요 처리 되었습니다");
	}

	public ResultData addBadReactionPoint(int loginedMemberId, String relTypeCode, int relId) {
		reactionPointRepository.addBadReactionPoint(loginedMemberId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article": 
			articleService.increaseBadReactionPoint(relId);
		}
		
		return ResultData.from("S-1", "싫어요 처리 되었습니다");
	}

}
