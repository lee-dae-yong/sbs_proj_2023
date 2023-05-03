<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="비밀번호 확인"/>
<%@include file="../common/head.jspf" %>

<script>
	function MemberCheckPassword__submit(form) {
	let MemberCheckPassword__submitDone = false;
		if ( MemberCheckPassword__submitDone ) {
			return;
		}    
		
		// 좌우공백 제거
		form.loginPw.value = form.loginPw.value.trim();
			
			if(form.loginPw.value.length == 0){
				alert("비밀번호 확인을 입력해주세요.");
				form.loginPw.focus();
				return;
		}
			
		form.loginPw.value = sha256(form.loginPwInput.value);
		form.loginPwInput.value = '';
			
		MemberCheckPassword__submitDone = true;
	
		form.submit();		
	}
</script>

<section class="mt-5">
  <div class="container mx-auto">
	  <form class="table-box-type-1" method="POST" action="../member/doCheckPassword" onsubmit="MemberCheckPassword__submit(this); return false;">
	  <input type="hidden" name="replaceUri" value="${param.replaceUri}"/>
		  <table>
			  <colgroup>
			  	<col width="200"/>
			  </colgroup>
			  <tbody>
			  	<tr>
			  		<th>로그인아이디</th>
			  		<td>
			  		${rq.loginedMember.loginId }
			  		</td>
			  	</tr>
			  	<tr>
			  		<th>로그인비밀번호</th>
			  		<td>
			  			<input type="password" class="w-96 input input-bordered w-full max-w-xs" name="loginPw" placeholder="로그인비밀번호"/>
			  		</td>
			  	</tr>
			  	<tr>
			  		<th>비밀번호 확인</th>
			  		<td>
			  			<input type="submit" class="btn btn-primary" value="비밀번호 확인"/>
			  			<button type="button" class="btn btn-outline btn-primary" onclick="history.back();">뒤로가기</button>
			  		</td>
			  	</tr>
			  </tbody>
		  </table>
	  </form>
  </div>
</section>

<%@include file="../common/foot.jspf" %>

