<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>特殊支持申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
	  <div class="wrap">
    	 <%@include file="/WEB-INF/include/notice.jsp" %>
     	
     
     	 	<tr>
           			 <td><span class="star">*</span>申请编号：</td>
     	 			 <td><input  type="text"  class="form-control text" name="ppn" /></td>
     		 </tr>
     		 
     		 <tr>
           			 <td><span class="star">*</span>核销编号：</td>
     	 			 <td><input  type="text"  class="form-control text" name="cpn" /></td>
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>申请代理商：</td>
     	 			 <td><input  type="text"  class="form-control text" name="agnet" /></td>
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>所属大区：</td>
     	 			 <td><input  type="text"  class="form-control text" name="area" /></td>
     		 </tr>
     		 
     		 <tr>
           			 <td><span class="star">*</span>申请日期：</td>
     	 			 <td><input  type="text"  class="form-control text" name="applyDate" /></td>
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>申请主题：</td>
     	 			 <td><input  type="text"  class="form-control text" name="applyTheme" /></td>
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>申请费用：</td>
     	 			 <td><input  type="text"  class="form-control text" name="applyPrice" /></td>
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>有效期：</td>
     	 			 <td><input type="text" class="form-control text datetimepicker" style="width:220px;" name="validStartTime" /></td>
     	 			 <td>至</td>
     	 			<td><input type="text" class="form-control text datetimepicker" style="width:220px;" name="validEndTime" /></td>
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>供应商：</td>
     	 			 <td><input  type="text"  class="form-control text" name="provider" /></td>
     		 </tr>
     	
     	
     
</body>
</html>
