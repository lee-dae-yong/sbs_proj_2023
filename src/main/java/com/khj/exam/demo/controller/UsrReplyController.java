package com.khj.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khj.exam.demo.service.ReplyService;
import com.khj.exam.demo.utill.Ut;
import com.khj.exam.demo.vo.Reply;
import com.khj.exam.demo.vo.ResultData;
import com.khj.exam.demo.vo.Rq;

@Controller
public class UsrReplyController {

	@Autowired
	private ReplyService replyService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {
		if(Ut.empty(relTypeCode)) return rq.jsHistoryBack("relTypeCode를 입력해주세요");
		if(Ut.empty(relId)) return rq.jsHistoryBack("relId를 입력해주세요");
		if(Ut.empty(body)) return rq.jsHistoryBack("body를 입력해주세요");
		
		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);
		
		int id = writeReplyRd.getData1();
		
		if(Ut.empty(replaceUri)) {
			switch(relTypeCode) {
			case "article" :
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}
		}
		
		return rq.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {
		if(Ut.empty(id)) return rq.jsHistoryBack("id를 입력해주세요");
		
		Reply reply = replyService.getForPrintReplies(rq.getLoginedMemberId(),id);
		
		if(reply == null) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글이 존재하지 않습니다", id));
		}
		
		if(!reply.isExtra__actorCanDelete()) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글을 삭제할 권한이 없습니다.", id));
		}
		
		ResultData deleteReplyRd = replyService.deleteReply(id);
		
		if(Ut.empty(replaceUri)) {
			switch(reply.getRelTypeCode()) {
			case "article" :
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}
		
		return rq.jsReplace(deleteReplyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reply/modify")
	public String modify(Model model, int id) {
		if(Ut.empty(id)) return rq.jsHistoryBack("id를 입력해주세요");
		
		Reply reply = replyService.getForPrintReplies(rq.getLoginedMemberId(),id);
		
		if(reply == null) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글이 존재하지 않습니다", id));
		}
		
		if(!reply.isExtra__actorCanModify()) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글을 수정할 권한이 없습니다.", id));
		}
		
		model.addAttribute("reply", reply);

		return "usr/reply/modify";
	}
	
	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(int id, String body, String replaceUri) {
		if(Ut.empty(id)) return rq.jsHistoryBack("id를 입력해주세요");
		
		Reply reply = replyService.getForPrintReplies(rq.getLoginedMemberId(),id);
		
		if(reply == null) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글이 존재하지 않습니다", id));
		}
		
		if(!reply.isExtra__actorCanModify()) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글을 수정할 권한이 없습니다.", id));
		}
		
		if(Ut.empty(body)) {
			return rq.jsHistoryBack("body를 입력해주세요.");
		}
		
		ResultData deleteReplyRd = replyService.modifyReply(id,body);
		
		if(Ut.empty(replaceUri)) {
			switch(reply.getRelTypeCode()) {
			case "article" :
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}
		
		return rq.jsReplace(deleteReplyRd.getMsg(), replaceUri);
	}
}
