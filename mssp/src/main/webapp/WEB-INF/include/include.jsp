<%-- 
    Document   : _env.jsp
    Created on : Jun 3, 2014, 10:52:47 AM
    Author     : changshu.li
--%><%@page pageEncoding="UTF-8"%><%--
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
--%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%--
--%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%--
--%><%@ taglib prefix="ms" tagdir="/WEB-INF/tags"%><%--
--%><c:set scope="request" var="ROOT" value="http://${header.Host}${pageContext.request.contextPath}"/>



<!-- coomm css -->
<link rel="stylesheet" href="${ROOT}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ROOT}/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" href="${ROOT}/css/bootstrap-select.min.css" />
<link rel="stylesheet" href="${ROOT}/css/main.css" />

<!-- coomm js -->
<script type="text/javascript" src="${ROOT}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ROOT}/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ROOT}/js/jw-common.js"></script>
<script type="text/javascript"  src="${ROOT}/js/bootstrap.min.js"></script>
<script type="text/javascript"  src="${ROOT}/js/main.js"></script>
<script type="text/javascript"  src="${ROOT}/js/ajaxfileupload.js"></script>
<script type="text/javascript"  src="${ROOT}/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="${ROOT}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ROOT}/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"  src="${ROOT}/js/bootstrap-select.min.js"></script>