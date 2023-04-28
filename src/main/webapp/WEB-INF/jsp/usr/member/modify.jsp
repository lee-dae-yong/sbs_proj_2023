<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="게시물 수정"/>
<%@include file="../common/head.jspf" %>

<script>
	let MemberModify__submitDone = false;
	function MemberModify__submit(form) {
		if ( MemberModify__submitDone ) {
			return;
		}    
		
		// 좌우공백 제거
		form.loginPw.value = form.loginPw.value.trim();
		
		if ( form.loginPw.value.length > 0 ) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
			
			if(form.loginPwConfirm.value.length == 0){
				alert("비밀번호 확인을 입력해주세요.");
				form.loginPwConfirm.focus();
				return;
			}
			
			if(form.loginPwConfirm.value != form.loginPwConfirm.value){
				alert("비밀번호 확인이 일치하지 않습니다.");
				form.loginPwConfirm.focus();
				return;
			}
		}
		
		form.name.value = form.name.value.trim();
		
		if ( form.name.value.length == 0 ) {
				alert("이름을 입력해주세요.");
				form.name.focus();
				return;
			}
		}
		
		form.nickname.value = form.nickname.value.trim();
		
		if ( form.nickname.value.length == 0 ) {
				alert("닉네임을 입력해주세요.");
				form.nickname.focus();
				return;
			}
		}
		
		form.email.value = form.email.value.trim();
		
		if ( form.email.value.length == 0 ) {
				alert("이메일을 입력해주세요.");
				form.email.focus();
				return;
			}
		}
		
		form.cellPhoneNo.value = form.cellPhoneNo.value.trim();
		
		if ( form.cellPhoneNo.value.length == 0 ) {
				alert("휴대전화번호를 입력해주세요.");
				form.cellPhoneNo.focus();
				return;
			}
		}
		
		MemberModify__submitDone = true;
		form.submit();		
	}
</script>

<section class="mt-5">
  <div class="container mx-auto px-3">
	<form class="table-box-type-1" method="POST" action="../member/doModify" onsubmit="MemberModify__submit">
	
      <table>
      <colgroup>
        <col width="200"/>
      </colgroup>
        <tbody>
          <tr>
            <th>로그인아이디</th>
            <td>${rq.loginedMember.loginId}</td>
          </tr>
          <tr>
            <th>새 비밀번호</th>
            <td>
            	<input type="password" class="input input-bordered" name="loginPw" placeholder="새 비밀번호를 입력해주세요."/>
            </td>
          </tr>
          <tr>
            <th>새 비밀번호 확인</th>
            <td>
            	<input type="password" class="input input-bordered" name="loginConfirm" placeholder="새 비밀번호를 확인해주세요."/>
            </td>
          </tr>
          <tr>
            <th>이름</th>
            <td>
            	<input type="text" class="input input-bordered" name="name" placeholder="이름을 입력해주세요." value="${rq.loginedMember.name}"/>
            </td>
          </tr>
          <tr>
            <th>닉네임</th>
            <td>
            	<input type="text" class="input input-bordered" name="nickname" placeholder="닉네임 입력해주세요." value="${rq.loginedMember.nickname}"/>
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>
            	<input type="text" class="input input-bordered" name="email" placeholder="이메일을 입력해주세요." value="${rq.loginedMember.email}"/>
            </td>
          </tr>
          <tr>
            <th>휴대전화번호</th>
            <td>
            	<input type="text" class="input input-bordered" name="cellphoneNo" placeholder="휴대전화번호를 입력해주세요." value="${rq.loginedMember.cellphoneNo}"/>
            </td>
          </tr>
          <tr>
            <th>회원정보수정</th>
            <td>
              <input type="submit" class="btn btn-primary" value="수정"/>
              <button type="button" class="btn btn-outline btn-primary" onclick="history.back();">뒤로가기</button>
            </td>
          </tr>
          
        </tbody>
      </table>   
	</form>
  </div>
</section>
<%@include file="../common/foot.jspf" %>