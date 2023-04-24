<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="게시물 작성"/>
<%@include file="../common/head.jspf" %>

<section class="mt-5">
  <div class="container mx-auto px-3">
	<form class="table-box-type-1" method="POST" action="doWrite">
	  <input type="hidden" name="id" value="${article.id}"/>
	
      <table>
      <colgroup>
        <col width="200"/>
      </colgroup>
        <tbody>
          <tr>
            <th>게시판</th>
            <td>
            	<select name="boardId" class="select select-bordered w-full max-w-xs">
            		<option value="" selected>게시판을 선택해주세요.</option>
            		<option value="1">공지사항</option>
            		<option value="2">자유게시판</option>
            	</select>
            </td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${rq.loginedMember.nickname}</td>
          </tr>
          <tr>
            <th>제목</th>
            <td>
              <input required="required" type="text" class="w-96 input input-bordered w-full max-w-xs" name="title" placeholder="제목" value="${article.title}"/>
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <textarea required="required" class="w-full textarea textarea-bordered" name="body" placeholder="내용" >${article.body}</textarea>
            </td>
          </tr>
          <tr>
            <th>작성</th>
            <td>
              <input type="submit" class="btn btn-primary" value="작성"/>
              <button type="button" class="btn btn-outline btn-primary" onclick="history.back();">뒤로가기</button>
            </td>
          </tr>
        </tbody>
      </table>   
	</form>
  </div>
</section>
<%@include file="../common/foot.jspf" %>