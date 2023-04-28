package com.khj.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.khj.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	Article getForPrintArticle(@Param("id") int id);

	List<Article> getForPrintArticles(int boardId, int limitStart, int limitTake, String searchKeywordTypeCode,
			String searchKeyword);

	void writeArticle(@Param("memberId") int memberId, @Param("boardId") int boardId,
			@Param("title") String title, @Param("body") String body);

	void deleteArticle(@Param("id") int id);

	void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	int getLastInsertId();

	int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	int increaseHitCount(int id);

	int getArticleHitCount(int id);

	int increaseGoodReactionPoint(int relId);

	int increaseBadReactionPoint(int relId);

	int decreaseGoodReactionPoint(int relId);

	int decreaseBadReactionPoint(int relId);

}