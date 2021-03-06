<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구단 관리</title>

<!-- INCLUDE head.jsp -->
<%@ include file="/WEB-INF/views/includes/head.jsp"%>

</head>
<body>
	<div class="bg-image-main"></div>

	<!-- container-fluid start -->
	<!-- 	<div class="container-fluid bg-image-main"> -->
	<div class="container-fluid">

		<!-- INCLUDE nav_top.jsp -->
		<%@ include file="/WEB-INF/views/includes/nav_top.jsp"%>
		<!-- Container Main start-->
		<div class="container-main">
			<!-- Club List start-->
			<div class="row">
				<div class="text-center text-white text-nowrap">
					<h1>
						<dfn>구단 관리</dfn>
					</h1>
				</div>

				<!-- Club list Table start -->
				<div class="col-xs-12 col-sm-12 col-md-12">
					<!-- Search condition start -->
					<div class="col-sm-12 col-md-12 enter-row-1">
						<form class="form-inline pull-right">
							<div class="form-group">
								<input type="text" class="form-control" id="keyword"
									placeholder="검색어를 입력하세요">
							</div>
						</form>
					</div>
					<!-- Search condition start -->
					<div class="table-responsive container-fluid" id="clubList">
					</div>
				</div>
				<!-- Club list Table end -->

				<!-- Pagination start -->
				<div class="text-center">
					<nav>
						<ul class="pagination" id="paginator">
						</ul>
					</nav>
				</div>
				<!-- Pagination end -->

			</div>
			<!-- Club List end-->
		</div>
		<!-- Container Main end-->
	</div>
	<!-- container-fluid end -->

	<!-- INCLUDE footer.jsp -->
	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
<script>
var token = '${_csrf.token }';
var header = '${_csrf.headerName }';

var cri = new Object();

var chDateFormat = function(inputDate){
	var date = new Date(inputDate);
	dateStr = (date.getYear()+1900) + "-";
	dateStr += (date.getMonth()+1)>=10? (date.getMonth()+1)+"-" :"0"+(date.getMonth()+1)+"-" ;
	dateStr += date.getDate()>=10? date.getDate()+"  ":"0"+date.getDate()+"  ";
	
	return dateStr;
};
	var showClubList = function(cri){
		$.ajax({
			type:"get",
			url:"/admin/club/list",
			data : cri,
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success:function(result){
				console.log("showClubList...........");
				console.log(result);
				var str = "<table class='table table-condensed table-hover text-center text-white'>"
						+	"<tr><td>No</td><td>구단명</td><td>구단주 ID</td><td>등록일</td><td>관리</td></tr>";
				
				var i = 1;
				for(var clubVO of result){
					str += "<tr>"
						+	"<td>" + (i++) + "</td>"
						+	"<td>" + clubVO.clubName + "</td>"
						+	"<td>" + clubVO.ownerId + "</td>"
						+	"<td>" + chDateFormat(clubVO.clubDate) + "</td>"
					    +   "<td><button type='button' class='btn btn-default' onclick='delClub(\""+clubVO.clubCode+"\");' >삭제</button></td>"
						+ "</tr>";
				}
				str += "</table>";
				
				
				$("#clubList").html(str);
				getPaginator(cri);
			}
		});
	}
	
	
	var getPaginator = function(cri){
		$.ajax({
			type : "get",
			url : "/admin/paginator/club",
			data : cri,
			dataType : "json",
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(result){
				console.log("getPaginator...........result");				
				console.log(result);	
				var str = "";
						
				if(result.prev){
					str += "<li>";
				}else{
					str += 	"<li class='disabled'>";
				}		
				str += "<a href='#' onclick='chPage("+(result.startPage-1)+");' aria-label='Previous'><span	aria-hidden='true'>&laquo;</span></a></li>";
				
				for(var i = result.startPage ; i<= result.endPage; i++){
					if(i==cri.pageNum){
						str += "<li class='active'>";
					}else{
						str += "<li>";
					}
					str += "<a href='#' onclick='chPage("+i+");'>" + i + "</a></li>";
				}
				if(result.next){
					str += "<li>";
				}else{
					str += 	"<li class='disabled'>";
				}		
				str += "<a href='#' onclick='chPage("+(result.endPage+1)+");' aria-label='Previous'><span	aria-hidden='true'>&raquo;</span></a></li>";
			
				$("#paginator").html(str);
			
			}
		});
		
	}
	
	var chPage = function(pageNum){
		console.log("chPage........to pageNum : " + pageNum);
		
		cri.pageNum = pageNum;
		showClubList(cri);
	}
	$("#keyword").on("keyup",function(e){
		console.log($(e.target).val());
		cri.keyword = $(e.target).val();
		
		showClubList(cri);
	})
	
	var delClub = function(clubCode){
		if(confirm("정말 삭제하시겠습니까?")){
		$.ajax({
			type : "delete",
			url :  "/admin/club/"+clubCode,
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(result){
				console.log("delClub...........");
				console.log(result);
				if(result =="success") location.reload();
			}
		});
		}
	}
	
	
	
	window.onload = function(){
		console.log("${principal.member}");
		cri.pageNum = "${cri.pageNum}";
		cri.amount = "${cri.amount}";
		cri.keyword = "${cri.keyword}";
		showClubList(cri);
	}
</script>
</body>
</html>