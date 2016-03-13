<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-预算维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
   		<div class="col-lg-12">
   		
					<%-- <form class="form-inline" id="form" action="${ROOT}/admin/iretail/budget/import.html" enctype="multipart/form-data" method="post">
							<div class="form-group">
								<label>选择文件：</label>
								<input type="file" id="file" name="file" style="display:inline-block;" />
								<input type="submit" id="submit" class="button button-lightblue" value="确定提交" />
							</div>
					</form> --%>
					

				<div class="form-group">
			      	<label>年份：</label>
					<select class="select" name="year">
			      				<option value="1" >=====请选择=====</option>
			      				<c:forEach items="${years }"  var="item">
			      					<option value="${item}">${item}</option>
			      				</c:forEach>
			      	</select>&nbsp;
					<label>季度：</label>
					<select class="select" name="month">
			      				<option value="0"  >=====请选择=====</option>
			      				<!-- <option value="1" >1</option>
			      				<option value="2" >2</option>
			      				<option value="3" >3</option>
			      				<option value="4" >4</option> -->
			      				<c:forEach items="${months }"  var="item">
			      					<option value="${item}">${item}</option>
			      				</c:forEach>
			      	</select>
			      	<input type="button" id="search" onclick="search()" value="查询" class="button button-blue" />
			      	
			      	<input type="button" value="导入预算" class="button button-blue" data-toggle="modal" data-target="#myModal"/>
		 	   </div>
		    
        	
        			<div class="table-responsive" >
						<span style="color: red;">${errorMsg }</span>
        			</div>
        	
        	<div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>年份</th>
                                <th>月份</th>
                                <th>查看与操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list }" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.year}</td>
                                <td>${item.quarter}</td>
                                <td>
                                	<a href="${ROOT}/admin/iretail/budget/view-${item.year}-${item.quarter}" class="operate operate1"><i></i>查看当月</a>
                                	 <a href="${ROOT}/admin/iretail/budget/viewTotal-${item.year}-${item.quarter}" class="operate operate1"><i></i>查看累计</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="page">
						<p class="pull-left">当前显示<span>${pagelist.from+1}-${pagelist.from+50}</span>,共<span>${pagelist.totalElements}</span>条记录</p>
						<ms:page from="${pagelist.from}" size="${pagelist.size}" totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
					</div> 
   		</div>
   </div>
</div>
</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">导入预算</h4>
      </div>
      <div class="modal-body">
      
     				<form class="form-inline" id="form" action="${ROOT}/admin/iretail/budget/import.html" enctype="multipart/form-data" method="post">
						<label>年份：</label>
					<select class="select" id="years" name="years">
			      				<option value="" >=====请选择=====</option>
			      				<c:forEach items="${years }"  var="item">
			      					<option value="${item}">${item}</option>
			      				</c:forEach>
			      	</select>&nbsp;
					<br /><br />
					<label>季度：</label>
					<select class="select" id="months" name="months">
			      				<option value=""  >=====请选择=====</option>
			      				<!-- <option value="1" >1</option>
			      				<option value="2" >2</option>
			      				<option value="3" >3</option>
			      				<option value="4" >4</option> -->
			      				<c:forEach items="${months }"  var="item">
			      					<option value="${item}">${item}</option>
			      				</c:forEach>
			      	</select>
			      	<br />	<br />
			      	<label>选择文件：</label>
					<input type="file" id="file" name="file" style="display:inline-block;" />
					</form>
      
        			
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button"  id="import" onclick="importBudget()" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
function search(){
	var year = $("select[name='year']").val();
	var month = $("select[name='month']").val();
	window.location="${ROOT}/admin/iretail/budget/search-"+year+"-"+month;
}

function importBudget(){
		$("#form").submit();
}

$(document).ready(function(){
	$("#form").submit(function(){
		var years = $("select[name='years']").val();
		var months = $("select[name='months']").val();
		var file = document.getElementById("file").value;
		if(years == ""){
			alert("请选择年份");
			return false;
		}
		if(months == ""){
			alert("请选择月份");
			return false;
		}
		 if(file.length == 0){
			alert("请选择预算文件");
			return false;
		} 
		var file_ext = file.substring(file.lastIndexOf(".")+1);
		if(file_ext != "xls" && file_ext != "xlsx"){
			alert("请上传xls或者xlsx文件");
			return false;
		}
	})
})
		
</script>
</body>
</html>