<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>月度销售预估</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <style>
  .maintable td,.maintable th { width:1%;}
  </style>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
    <c:if test="${user.commRole.id==4 }">
      <div class="filter">
      	<form id="form-filter" class="form-inline" action="${ROOT}/psi/estimate/search" method="post">
      		<div class="form-group">
	      		<label>当前代理：</label>
	      		<select class="form-control select" name="agentId">
	            <c:forEach items="${agentList}" var="item">
					<option value="${item.id}">${item.irName}</option>
	     		</c:forEach>
	    		</select>
      		</div>
      		<input type="button"  id="searchBut" value="查询" class="button button-lightblue" />
      	</form>
      </div>
      </c:if>
      <form id="form" action="${ROOT}/psi/estimate/save" method="post">
      <div  style="text-align:right;"<c:if test="${user.commRole.id==4 }"> style="margin-top:-55px;" </c:if> >
      	 	<c:if test="${isSubmit}"><input type="button" class="button button-lightblue" onclick="tips()" value="确定提交" /> </c:if>
      	 	<c:if test="${!isSubmit}"><input type="submit" class="button button-lightblue" value="确定提交" /> </c:if>
	     </div>
      <table class="table table-hover maintable" style="display:block;">
          <thead>
              <tr>
              	<th>代理商</th>
              	<th>寸别</th>
                <th>系列</th>
                <th>型号</th>
                <th>备注</th>
                <th>代理${month+1 }月需求</th>
                <th>代理${month+2 }月需求</th>
              </tr>
          </thead>
          <tbody style="display:block; height:800px; overflow-y:scroll;">
          <c:forEach items="${sellInTempList}" var="item">
              <tr>
              	  <td>${agent.irName }<input type="hidden" value="${agent.id }" name="agentId" /></td>
                  <td>${item.product.size1 }</td>
                  <td>${item.product.commSeries.name }</td>
                  <td>${item.product.name }<input type="hidden" value="${item.id }" name="tempId" /></td>
                  <td>${item.comment }</td>
                  <td><input type="text" class="text form-control" value="0" name="nextMonthEstimateVolume" /></td>
                  <td><input type="text" class="text form-control" value="0" name="nnextMonthEstimateVolume" /></td>
              </tr>
          </c:forEach>
          <c:forEach items="${psiEstimateByProductList}" var="item">
              <tr>
              	  <td>${agent.irName }<input type="hidden" value="${agent.id }" name="agentId" /></td>
                  <td>${item.temp.product.size1 }</td>
                  <td>${item.temp.product.commSeries.name }</td>
                  <td>${item.temp.product.name }</td>
                  <td>${item.temp.comment }</td>
                  <td>${item.nextMonthEstimateVolume }</td>
                  <td>${item.nnextMonthEstimateVolume }</td>
              </tr>
          </c:forEach>
          <c:forEach items="${psiManagerEstimateByProductList}" var="item">
              <tr>
              	  <td>${item.agent.irName }<input type="hidden" value="${item.agent.id }" name="agentId" /></td>
                  <td>${item.temp.product.size1 }</td>
                  <td>${item.temp.product.commSeries.name }</td>
                  <td>${item.temp.product.name }<input type="hidden" value="${item.temp.id }" name="tempId" /></td>
                  <td>${item.temp.comment }</td>
                  <td><input type="text" class="text form-control" value="${item.nextMonthEstimateVolume }" name="nextMonthEstimateVolume" /></td>
                  <td><input type="text" class="text form-control" value="${item.nnextMonthEstimateVolume }" name="nnextMonthEstimateVolume" /></td>
              </tr>
          </c:forEach>
          </tbody>
      </table>
      <a href="javascript:void(0)" id="backtop">返回顶部</a>
      </form>
    </div>
  </div>
</body>
<script type="text/javascript">
function backSorH(){
    h = $(window).height();
    t = $(document).scrollTop();
    if(t > h){
        $('#backtop').show();
    }else{
        $('#backtop').hide();
    }
}
$(document).ready(function(){
    //返回顶部
    backSorH();
    $('#backtop').click(function(){
        $(document).scrollTop(0);
    });
    //提交前判断是否没有填写
    $("#form").submit(function(){
    	var count=0;
    	var $nextMonthEstimateVolume=$("input[name=nextMonthEstimateVolume]");
    	var $nnextMonthEstimateVolume=$("input[name=nnextMonthEstimateVolume]");
    	for(var i=0;i<$nextMonthEstimateVolume.length;i++){
    		if($nextMonthEstimateVolume[i].value!=0 && $nnextMonthEstimateVolume[i].value!=0){
    			count++;
    		}
    	}
    	if(count==0){
    		alert("至少填写一个型号的预估量");
    		return false;
    	}
    });
    $("#searchBut").click(function(){
    	var agentId = $("select[name='agentId'] option:selected").val();
    	$.ajax({
			type : "POST",
			url : "${ROOT}/psi/estimate/search",
			data : "agentId=" + agentId,
			dataType : "json",
			success : function(data) {
				data=json$ref(data);
				var datalist = json$ref(data.psiEstimateByProductList);
				if(data.isSubmit){
		    		$(".pull-right").html('<input type="button" class="button button-lightblue" onclick="tips()" value="确定提交" />');
		    	}else{
		    		$(".pull-right").html('<input type="submit" class="button button-lightblue" value="确定提交" />');
		    	}
				$(".maintable tbody").empty();
				$.each(datalist,function(i,item){
	              var tr = '<tr><td>' + item.agent.irName +'<input type="hidden" value="' + item.agent.id + '" name="agentId" /></td>' + 
	              			'<td>' + item.temp.product.size1 + '</td>' + 
	              			'<td>' + item.temp.product.commSeries.name + '</td>' + 
	              			'<td>' + item.temp.product.name + '<input type="hidden" value="' + item.id + '" name="tempId" /></td>' + 
	              			'<td>' + item.temp.comment + '</td>' + 
	              			'<td><input type="text" class="text form-control" value="' + item.nextMonthEstimateVolume +'" name="nextMonthEstimateVolume" /></td>' + 
	              			'<td><input type="text" class="text form-control" value="' + item.nnextMonthEstimateVolume + '" name="nnextMonthEstimateVolume" /><input type="hidden" value="' + item.id + '" name="estimateId" /></td></tr>';
	             $(".maintable tbody").append(tr);
				});
			}
    	});
    });
});
$(window).scroll(function(e){
    backSorH();
});
function tips(){
	alert("本月已经提交过！");
}
</script>
</html>