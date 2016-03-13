<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-级别维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li>当前角色：${role.roleName }</li>
      </ol>
      <div>
        <form id ="myForm" class="form-horizontal" action="${ROOT}/admin/comm/role/permissionSave" method="post">
        <input type="hidden"  name="roleId" value="${role.id }"/>
        
        后台菜单<hr />
          	<ul>
                   <li>
                     <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[1-1] == 1}">checked="checked"</c:if>value="1" /> <a href="#" ><span>基础资料维护</span></a>
                      <ul style="margin-left: 20px;">
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[2-1] == 1}">checked="checked"</c:if>value="2" />城市维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[3-1] == 1}">checked="checked"</c:if> value="3" />区域维护</li>
                          <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[4-1] == 1}">checked="checked"</c:if> value="4" />省份维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[5-1] == 1}">checked="checked"</c:if> value="5" />面板维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[6-1] == 1}">checked="checked"</c:if> value="6" />产品系列维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[7-1] == 1}">checked="checked"</c:if> value="7" />零售产品系列维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[8-1] == 1}">checked="checked"</c:if>value="8"  />行业维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[9-1] == 1}">checked="checked"</c:if> value="9" />型号维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[10-1] == 1}">checked="checked"</c:if>value="10"  />样机维护</li>
                     </ul>
                 </li>
                 <li>
                      <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[11-1] == 1}">checked="checked"</c:if> value="11" /><a href="#" ><span>角色维护</span></a>
                      <ul style="margin-left: 20px;">
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[12-1] == 1}">checked="checked"</c:if> value="12" />级别维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[13-1] == 1}">checked="checked"</c:if>value="13"  />用户维护</li>
                     </ul>
                 </li>
                 <li>
                     <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[14-1] == 1}">checked="checked"</c:if>value="14"  /> <a href="#">代理商维护</a>
                  </li>
                 <li>
                  <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[15-1] == 1}">checked="checked"</c:if> value="15" /><a href="#">Sell out修改</a>
                  </li>
               <!--    <li>
                      <a href="#"><i class="icon icon03"></i><span>代打款账号维护</span></a>
                  </li> -->
               <%--    <li>
                   <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[16-1] == 1}">checked="checked"</c:if> value="16" /><a href="#">月度销售预估</a>
                  </li> --%>
                  
                  <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[73-1] == 1}">checked="checked"</c:if> value="73" /><a href="#">上传销售预估模板</a></li>
                  <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[75-1] == 1}">checked="checked"</c:if> value="75" /><a href="#">上传销售目标量模板</a></li>
                   <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[74-1] == 1}">checked="checked"</c:if> value="74" /><a href="#">导出销售预估</a></li>
                  <li>
                  		<input type="checkbox" name="permissionId"  <c:if test="${permitionIds[17-1] == 1}">checked="checked"</c:if> value="17" /><a href="#">数据修改理由维护</a>
                  </li>
                  <li>
               	   <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[18-1] == 1}">checked="checked"</c:if>value="18"  /><a href="#">PSI数据查询导出</a>
                  </li>
                  <li>
               	   <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[131-1] == 1}">checked="checked"</c:if>value="131"  /><a href="#">考核条件或目标</a>
                  </li>
                   <li>
                	   <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[19-1] == 1}">checked="checked"</c:if>value="19"  /><a href="#">审批模板定制</a>
                  </li>
                  <li class="nav-parent">
                      <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[20-1] == 1}">checked="checked"</c:if> value="20"  /><a href="#"><span>Iretail</span></a>
                      <ul style="margin-left: 20px;">
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[21-1] == 1}">checked="checked"</c:if>value="21"  />卖场维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[22-1] == 1}">checked="checked"</c:if> value="22" />物料维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[23-1] == 1}">checked="checked"</c:if>value="23" />宣传品类型维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[24-1] == 1}">checked="checked"</c:if> value="24" />店面级别维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[38-1] == 1}">checked="checked"</c:if> value="38" />PPN字段维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[37-1] == 1}">checked="checked"</c:if> value="37" />预算维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[67-1] == 1}">checked="checked"</c:if> value="67" />装修设置维护</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[70-1] == 1}">checked="checked"</c:if> value="70" />零售店维护</li>
                          <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[71-1] == 1}">checked="checked"</c:if> value="71" />数据查询与导出</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[25-1] == 1}">checked="checked"</c:if> value="25" />R01报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[26-1] == 1}">checked="checked"</c:if> value="26" />R02报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[27-1] == 1}">checked="checked"</c:if>value="27"  />R03报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[28-1] == 1}">checked="checked"</c:if> value="28" />R04报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[29-1] == 1}">checked="checked"</c:if>value="29"  />R05报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[30-1] == 1}">checked="checked"</c:if> value="30" />R06报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[31-1] == 1}">checked="checked"</c:if> value="31"  />R07报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[32-1] == 1}">checked="checked"</c:if> value="32" />R08报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[33-1] == 1}">checked="checked"</c:if> value="33" />R09报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[34-1] == 1}">checked="checked"</c:if> value="34" />R010报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[35-1] == 1}">checked="checked"</c:if> value="35" />R011报表</li>
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[36-1] == 1}">checked="checked"</c:if> value="36" />R012报表</li>
                     </ul>
                 </li>
                 
                 
                 <li>
                      <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[68-1] == 1}">checked="checked"</c:if> value="68" /><a href="#" ><span>B2I</span></a>
                      <ul style="margin-left: 20px;">
                         <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[69-1] == 1}">checked="checked"</c:if> value="69" />申请模板维护</li>
                     </ul>
                 </li>
              </ul>
               前台菜单<hr />
               <ul >
			        <li>
			           <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[39-1] == 1}">checked="checked"</c:if>value="39" /><a href="#" >PSI</a>
			          <ul style="margin-left: 20px;">
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[40-1] == 1}">checked="checked"</c:if>value="40" />sellin录入</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[41-1] == 1}">checked="checked"</c:if>value="41" />sellout录入</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[42-1] == 1}">checked="checked"</c:if>value="42" />sellout修改</li>
			             <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[43-1] == 1}">checked="checked"</c:if>value="43" />月度销售预估</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[44-1] == 1}">checked="checked"</c:if>value="44" />数据查询&导出</li>
			          </ul>
			        </li>
			         <li>
			          <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[45-1] == 1}">checked="checked"</c:if>value="45" /><a href="#" >iRetail</a>
			          <ul style="margin-left: 20px;">
			          	<li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[46-1] == 1}">checked="checked"</c:if>value="46" />R01零售店装修</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[47-1] == 1}">checked="checked"</c:if>value="47" />PO申请</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[48-1] == 1}">checked="checked"</c:if>value="48" />PO核销</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[49-1] == 1}">checked="checked"</c:if>value="49" />下载表格</li>
			          </ul>
			        </li>
			        <li>
			         <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[50-1] == 1}">checked="checked"</c:if>value=50 /> <a  href="#" >B2B</a>
			          <ul style="margin-left: 20px;">
			          	<li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[76-1] == 1}">checked="checked"</c:if>value="76" />添加客户</li>
			          	<li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[132-1] == 1}">checked="checked"</c:if>value="132" />添加二级代理商</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[51-1] == 1}">checked="checked"</c:if>value="51" />项目报备</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[52-1] == 1}">checked="checked"</c:if>value="52" />项目列表</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[53-1] == 1}">checked="checked"</c:if>value="53" />项目核销</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[54-1] == 1}">checked="checked"</c:if>value="54" />结案查询</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[55-1] == 1}">checked="checked"</c:if>value="55" />数据查询&导出</li>
			          </ul>
			        </li>
			         <li>
			          <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[56-1] == 1}">checked="checked"</c:if>value="56" /><a href="#" >B2I</a>
			          <ul style="margin-left: 20px;">
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[57-1] == 1}">checked="checked"</c:if>value="57" />添加客户</li>
			             <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[72-1] == 1}">checked="checked"</c:if>value="72" />添加二级代理商</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[58-1] == 1}">checked="checked"</c:if>value="58" />项目申请</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[59-1] == 1}">checked="checked"</c:if>value="59" />项目列表</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[60-1] == 1}">checked="checked"</c:if>value="60" />项目核销</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[61-1] == 1}">checked="checked"</c:if>value="61" />返利查询</li>
			           <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[62-1] == 1}">checked="checked"</c:if>value="62" />数据查询&导出</li>
			          </ul>
			        </li>
			        <li>
			          <input type="checkbox" name="permissionId"  <c:if test="${permitionIds[63-1] == 1}">checked="checked"</c:if>value="63" /><a href="#">B2C</a>
			          <ul style="margin-left: 20px;">
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[64-1] == 1}">checked="checked"</c:if>value="64" />添加项目</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[65-1] == 1}">checked="checked"</c:if>value="65" />未完结项目查询</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[66-1] == 1}">checked="checked"</c:if>value="66" />已完结项目查询</li>
			            <li><input type="checkbox" name="permissionId"  <c:if test="${permitionIds[133-1] == 1}">checked="checked"</c:if>value="133" />退回项目查询</li>
			          </ul>
			        </li>
      			</ul>
              
                <input type="submit" class="button button-blue" value="提交" />
        </form>
      </div>
    </div>
</div>
</div>
</body>
<script type="text/javascript">
$(function(){
	$("#submitBut").click(function(){
		alert("11");
		$("#myForm").submit();
	})
});
</script>
</html>