<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>sellin录入</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
      <form class="form-horizontal" id = "form"  action="${ROOT}/psi/sellin/input"  enctype="multipart/form-data"  method="post" >
          <div class="form-group">
            <label class="control-label">导入类型：</label>
            <div class="controls">
              <select class="select form-control" name="channelType">
                <option value="1"  ${channelType=='1'?'selected':'' }>飞生sell in导入</option>
                <option value="2"  ${channelType=='2'?'selected':'' }>越海sell in导入</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">选择文件：</label>
            <div class="controls">
              <input type="file" id="file" name="file" />
            </div>
          </div>
          <div class="form-group form-group-btn">
            <div class="controls">
              <input type="submit" id = "submit" class="button button-lightblue"  value="确定提交" />
            </div>
          </div>
        </form>
         <div class="table-responsive"   ${errorMsg==null?'style="display: none;"':'' }>
					<span style="color: red;">${errorMsg }</span>
          </div>
        <div class="table-responsive"   ${list==null?'style="display: none;"':'' }>
        	<c:if test="${channelType=='1' }">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>Delivery</th>
                                <th>Item</th>
                                <th>Material</th>
                                <th>Dlv.qty</th>
                                <th>SU</th>
                                <th>Createdon</th>
                                <th>Volume</th>
                                <th>VUn</th>
                                <th>Sold-to</th>
                                <th>Nameofsold-toparty</th>
                                <th>Ship-to</th>
                                <th>Nameoftheship-toparty</th>
                                <th>Ship-toL</th>
                                <th>DlvTy</th>
                                <th>ShPt</th>
                                <th>Area</th>
                                <th>City</th>
                                <th>Province</th>
                                <th>寸别</th>
                                <th>AH-IPS</th>
                            </tr>
                        </thead>
                        <c:forEach items="${list}" var="item">
                        <tbody>
                            <tr>
                                <td>${item.delivery}</td>
                                <td>${item.item}</td>
                                <td>${item.material}</td>
                                <td>${item.dlvQty}</td>
                                <td>${item.su}</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.createdon}" /></td>
                                <td>${item.volume}</td>
                                <td>${item.vun}</td>
                                <td>${item.soldTo}</td>
                                <td>${item.nameofsoldToparty}</td>
                                <td>${item.shipTo}</td>
                                <td>${item.nameoftheshipToparty}</td>
                                <td>${item.shipToL}</td>
                                <td>${item.dlvTy}</td>
                                <td>${item.shPt}</td>
                                <td>${item.area}</td>
                                <td>${item.city}</td>
                                <td>${item.province}</td>
                                <td>${item.size}</td>
                                <td>${item.ahips}</td>
                            </tr>
                        </tbody>
                        </c:forEach>
                    </table>
                    </c:if>
                    <c:if test="${channelType=='2' }">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>入库批号</th>
                                <th>仓别</th>
                                <th>出货仓库</th>
                                <th>客户</th>
                                <th>订货日期</th>
                                <th>产品</th>
                                <th>型号</th>
                                <th>数量</th>
                                <th>村别</th>
                                <th>AH-IPS</th>
                            </tr>
                        </thead>
                        <c:forEach items="${list}" var="item">
                        <tbody>
                            <tr>
                                <td>${item.seqNumber}</td>
                                <td>${item.inputNumber}</td>
                                <td>${item.repositoryType}</td>
                                <td>${item.city}</td>
                                <td>${item.nameoftheshipToparty}</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.createdon}" /></td>
                                <td>${item.product}</td>
                                <td>${item.material}</td>
                                <td>${item.dlvQty}</td>
                                <td>${item.size}</td>
                                <td>${item.ahips}</td>
                            </tr>
                        </tbody>
                        </c:forEach>
                    </table>
                    </c:if>
                </div>
    </div>
  </div>
</body>
</html>
<script>
	$(document).ready(function(){
		$("#form").submit(function(){
			var file = document.getElementById("file").value;
			if(file.length == 0){
				alert("请选择文件");
				return false;
			}else{
				var year = (new Date()).getFullYear();
				var month = (new Date()).getMonth()+1;
				if(month < 10)
					month = "0" + month;
				var dates = (new Date()).getDate();
				if(dates < 10)
					dates = "0"+dates;
				var d = year +"-"+month + "-"+dates +$("select[name=channelType]").val();
				if("${fei}" == d){
					alert("你已经提交过一次，请明天再提交");
					return false;
				}else if("${yue}" == d){
					alert("你已经提交过一次，请明天再提交");
					return false;
				}else{
					return true;
				}
			}
		});
	});
</script>