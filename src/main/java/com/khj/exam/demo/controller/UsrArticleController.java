package com.khj.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khj.exam.demo.service.ArticleService;
import com.khj.exam.demo.service.BoardService;
import com.khj.exam.demo.utill.Ut;
import com.khj.exam.demo.vo.Article;
import com.khj.exam.demo.vo.Board;
import com.khj.exam.demo.vo.ResultData;
import com.khj.exam.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder.Default;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	// 액션 메서드 시작
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(int boardId, String title, String body, String replaceUri) {
			
		if ( Ut.empty(title) ) {
			return rq.jsHistoryBack("제목을 입력해주세요");
		}
		
		if ( Ut.empty(body) ) {
			return rq.jsHistoryBack("내용을 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);
		
		int id = writeArticleRd.getData1();
		
		if(Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}
		System.out.println("Ddd" + replaceUri);
		return rq.jsReplace(Ut.f("%s번 글이 생성되었습니다.", id), replaceUri);
	}
	
	@GetMapping("/usr/article/write")
	public void showWrite() {}
	
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue="1")int boardId,
			@RequestParam(defaultValue ="1")int page,
			@RequestParam(defaultValue = "title, body")String searchKeywordTypeCode,
			@RequestParam(defaultValue = "")String searchKeyword){
		
		Board board = boardService.getBoardById(boardId);
		if(board == null) {
			return rq.historyBackJsOnview(Ut.f("%d번 게시판은 존재하지 않습니다.", boardId));
		}
		
		int articlesCount = articleService.getArticleCount(boardId, searchKeywordTypeCode, searchKeyword);
		int itemsCountInAPage= 10;
		int pagesCount = (int)Math.ceil((double)articlesCount) / itemsCountInAPage;
		
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId, searchKeywordTypeCode, searchKeyword ,itemsCountInAPage, page);
	
		model.addAttribute("board", board);
		model.addAttribute("page",page);
		model.addAttribute("pagesCount",pagesCount);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);

		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData<Integer> doIncreaseHitCountRd(int id) {
		
		ResultData<Integer> increaseHitCountRd = articleService.increaseHitCount(id);
		
		if(increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}
		
		ResultData<Integer> rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));
		
		 rd.setData2("id",id);
		 
		 return rd;
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if ( article == null ) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if ( article == null ) {
			ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		if ( article.getMemberId() != rq.getLoginedMemberId() ) {
			return rq.jsHistoryBack("권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		
		return rq.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");
	}
	
	@RequestMapping("/usr/article/modify")
	public String ShowModify(Model model, int id, String title, String body) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if ( article == null ) {
			return rq.historyBackJsOnview(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if ( actorCanModifyRd.isFail() ) {
			return rq.historyBackJsOnview(actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		
		if ( rq.isLogined() == false ) {
			return rq.jsHistoryBack("로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if ( article == null ) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if ( actorCanModifyRd.isFail() ) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return rq.jsReplace(Ut.f("%d번 글이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}
	// 액션 메서드 끝

}
