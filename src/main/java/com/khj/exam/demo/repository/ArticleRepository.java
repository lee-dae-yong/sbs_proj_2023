package com.khj.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.khj.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	Article getForPrintArticle(@Param("id") int id);
	
	List<Article> getForPrintArticles(@Param("boardId")int boardId, String searchKeywordTypeCode, String searchKeyword, int limitStart, int limitTake);
	
	void writeArticle(@Param("memberId") int memberId, @Param("boardId")int boardId, @Param("title") String title, @Param("body") String body);
	
	void deleteArticle(@Param("id") int id);

	void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	int getLastInsertId();

	int getArticlesCount(@Param("boardId")int boardId, String searchKeywordTypeCode, String searchKeyword);

	int increseHitCount(int id);

	int getArticleHitCount(int id);
}