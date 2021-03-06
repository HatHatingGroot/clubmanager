<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cc"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판-글 상세 보기</title>

<!-- INCLUDE head.jsp -->
<%@ include file="/WEB-INF/views/includes/head.jsp"%>

</head>
<body>
	<div class="bg-image-main"></div>

	<!-- container-fluid start -->
	<div class="container-fluid">

		<!-- INCLUDE nav_top.jsp -->
		<%@ include file="/WEB-INF/views/includes/nav_top.jsp"%>
		<!-- Container Main start-->
		<div class="container-main">
			<!-- Free Board View start-->
			<div class="row">
				<div class="text-center text-white text-nowrap enter-row-4">
					<h1>
						<dfn>자유게시판 - 글 상세 보기</dfn>
					</h1>
				</div>






				<!-- Free Board Content Table start -->
				<div class="col-xs-12 col-sm-12 col-md-12 enter-row-4">
					<div class="panel panel-default">
						<!-- Default panel contents -->
						<div class="panel-heading input-lg">
							<div class="col-xs-6 col-sm-6 col-md-6">
								${boardVO.boardWriterName } 
								<small>
									<fmt:formatDate value="${boardVO.boardDate }" pattern="yyyy-MM-dd E  HH:mm" />
								</small>
							</div>
							<div class="col-xs-2 col-sm-2 col-md-2">
								<span class="glyphicon glyphicon-comment">${boardVO.replyCnt }</span>
							</div>
							<div class="col-xs-2 col-sm-2 col-md-2">
								<span class="glyphicon glyphicon-thumbs-up" id="likeBtn">${boardVO.boardLike }</span>
							</div>
							<div class="col-xs-2 col-sm-2 col-md-2">
								<span class="glyphicon glyphicon-eye-open">${boardVO.boardHit }</span>
							</div>
						</div>
						<div class="panel-body">
							<div class="col-xs-12 col-sm-12 col-md-12 large-font">
								${boardVO.boardTitle }</div>
							<div class="col-xs-12 col-sm-12 col-md-12">
							<ul class="list-inline" id="attachListLink">
								<c:forEach items="${boardVO.attachList }" var="attach">
									<li>
									<button data-fileName="${attach.fileName }" data-filePath="${attach.filePath }" class="download">
									${attach.fileName }
									</button>
									</li>
								</c:forEach>
							</ul>
							</div>
							<div id="imgDisplay">
								<c:forEach items="${boardVO.attachList }" var="attach">
									<c:if test="${attach.isImg == 1 }">
										<c:url value="/freeboard/viewImg" var="url">
											<c:param name="filePath" value="${attach.filePath }" />
											<c:param name="fileName" value="${attach.fileName }" />
										</c:url>
										<img src='${url}'>
									</c:if>
								</c:forEach>
							</div>

							<div>
								<p>${boardVO.boardContent }</p>
							</div>


							<div>
								<button type="button" class="btn btn-default pull-left"
									onclick="history.back()">목록</button>
								${principal.member.userId}
								${boardVO.boardWriter }
								<c:if test="${principal.member.userId == boardVO.boardWriter }">
									<span class="pull-right">
										<button type="button" class="btn btn-default pull-left"
											onclick="location.href='/freeboard/modify?boardNo=${boardVO.boardNo }'">수정</button>
										<button type="button" class="btn btn-default" id="deleteBtn">삭제</button>
									</span>
								</c:if>

							</div>
							<hr>
							<div class="comment-box ">
								<textarea class="col-xs-12 col-sm-11 col-md-11"
									placeholder="댓글을 남겨주세요" id="addReply"></textarea>
								<button type="button"
									class="btn btn-default input-lg col-xs-12 col-sm-1 col-md-1"
									id="addReplyBtn">등록</button>
							</div>
						</div>


						<!-- Table -->
						<div class="table-responsive container-fluid scroll-reply"
							id="replyListDP"></div>
					</div>

				</div>
				<!-- Free Board Content Table end -->
			</div>
			<!-- Free Board View end-->
		</div>
		<!-- Container Main end-->
	</div>
	<!-- container-fluid end -->
	<form id="downloadFrm" method="post" action="/freeboard/download">
		<input type="hidden" name="fileName" id="fileName">
		<input type="hidden" name="filePath" id="filePath">
		<input type="hidden" name="boardNo" value="${boardVO.boardNo }">
	    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</form>
	
	<script>
var token = '${_csrf.token }';
var header = '${_csrf.headerName }';
console.log("${boardVO.attachList[0].fileName}");
var isLike = '';

$(".download").on("click",function(e){
	console.log($(e.target).data());
	$("#fileName").val($(e.target).data('filename'));
	$("#filePath").val($(e.target).data('filepath'));
	console.log($("#downloadFrm"));
	$("#downloadFrm").submit();
})


var chDateFormat = function(inputDate){
	var date = new Date(inputDate);
	var dateStr = '';
	console.log((new Date()-date));
	if((new Date()-date) >= (1000*60*60*24)){
		dateStr += (date.getYear()+1900) + "-";
		dateStr += (date.getMonth()+1)>=10? (date.getMonth()+1)+"-" :"0"+(date.getMonth()+1)+"-" ;
		dateStr += date.getDate()>=10? date.getDate()+"  ":"0"+date.getDate()+"  ";
	}else{
		dateStr += date.getHours()>=10? date.getHours() + ":":"0"+date.getHours() + ":";
		dateStr += date.getMinutes()>=10 ? date.getMinutes():"0"+date.getMinutes();
	}
	
	
	return dateStr;
};

$("#deleteBtn").on("click",function(e){
	if(confirm('정말 삭제하시겠습니까?')){
		$.ajax({
			type:"delete",
			url:"/freeboard/board/"+"${boardVO.boardNo}",
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(result){
				console.log(result);
				if(result == 'success'){
					alert("게시물이 삭제되었습니다");
				}else{
					alert("게시물 삭제에 실패했습니다");
				}
				location.replace("/freeboard/list?clubCode=${principal.member.clubCode }");
			}
		});
	}
})

	$("#addReplyBtn").on("click",function(e){
		var replyVO = new Object();
		replyVO.replyWriter = "${principal.member.userId}";
		replyVO.replyWriterName =  "${principal.member.userName}";
		replyVO.replyContent = $("#addReply").val();
		replyVO.boardNo = '${boardVO.boardNo }';
		replyVO.isLike = 0;
		console.log(replyVO);
		
		$.ajax({
			type:"post",
			url:"/freeboard/reply",
			data: JSON.stringify(replyVO),
			contentType : "application/json",
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(result){
				console.log(result);
				location.reload();
			}
		});
	})
	
var showReplyList = function(){
		var boardNo = '${boardVO.boardNo }';
		$.ajax({
			type:"get",
			url:"/freeboard/reply/list/"+boardNo,
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(result){
				if(result != null){
					str = "<table class='table table-condensed text-center'>"
						+"<colgroup>"
						+"<col width=10% />"
						+"<col width=75% />"
						+"<col width=15% />"
						+"</colgroup>";
				for(var reply of result){
					if(reply.isLike == 0){
					str+= "<tr>"
							+"<td>"+reply.replyWriterName+"</td>"
							+"<td>"+reply.replyContent+"</td>"
							+"<td>"+chDateFormat(reply.replyDate)+"</td>"
							+"</tr>";
					}else{
						isLike = true;
					}
					}
					str+= "</table>";	
					$("#replyListDP").html(str);
				}
			}
		});
	};
	
	$("#likeBtn").on("click", function(e){
		if(isLike){
			alert("좋아요를 취소합니다");
			var replyVO = new Object();
			replyVO.replyWriter = "${principal.member.userId}";
			replyVO.boardNo = '${boardVO.boardNo }';
			replyVO.isLike = 1;
			console.log(replyVO);
			
			$.ajax({
				type:"delete",
				url:"/freeboard/reply",
				data: JSON.stringify(replyVO),
				contentType : "application/json",
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				success : function(result){
					console.log(result);
					location.reload();
				}
			});
		}else{
			alert("이 글을 좋아합니다");
			var replyVO = new Object();
			replyVO.replyWriter = "${principal.member.userId}";
			replyVO.replyWriterName =  "${principal.member.userName}";
			replyVO.replyContent = '';
			replyVO.boardNo = '${boardVO.boardNo }';
			replyVO.isLike = 1;
			console.log(replyVO);
			
			$.ajax({
				type:"post",
				url:"/freeboard/reply",
				data: JSON.stringify(replyVO),
				contentType : "application/json",
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				success : function(result){
					console.log(result);
					location.reload();
				}
			});
		}
	})
	
		window.onload=function(){
			showReplyList();
			$("#imgDisplay>img").css({"width":"100%","height":"auto","margin-bottom":"5px"});
			
			
		}
	
</script>


	<!-- INCLUDE footer.jsp -->
	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>


</body>
</html>