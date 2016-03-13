<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-系列维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">系列维护</a></li>
        <li>新增</li>
      </ol>
      <div>
        <form class="form-horizontal" action="" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label">面板：</label>
            <div class="controls">
              <input type="file" id="pannelFile" name="pannelFile"/>
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交" onclick="return ajaxUploadPannel();">
            </div>
          </div>
        </form>
        <form class="form-horizontal" action="" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label">系列：</label>
            <div class="controls">
              <input type="file" id="seriesFile" name="seriesFile"/>
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交" onclick="return ajaxUploadseries();">
            </div>
          </div>
        </form>
        <form class="form-horizontal" action="" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label">零售产品系列：</label>
            <div class="controls">
              <input type="file" id="productSeriesFile" name="productSeriesFile"/>
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交" onclick="return ajaxUploadProductSeries();">
            </div>
          </div>
        </form>
        <form class="form-horizontal" action="" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label">产品型号：</label>
            <div class="controls">
              <input type="file" id="productFile" name="productFile"/>
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交" onclick="return ajaxUploadProduct();">
            </div>
          </div>
        </form>
        <form class="form-horizontal" action="" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label">区域：</label>
            <div class="controls">
              <input type="file" id="areaFile" name="areaFile"/>
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交" onclick="return ajaxUploadArea();">
            </div>
          </div>
        </form>
        <form class="form-horizontal" action="" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label">城市：</label>
            <div class="controls">
              <input type="file" id="cityFile" name="cityFile"/>
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交" onclick="return ajaxUploadCity();">
            </div>
          </div>
        </form>
      </div>
    </div>
</div>
</div>
<script>
function ajaxUploadPannel()
{
	$.ajaxFileUpload
	(
		{
			url:"${ROOT}/admin/comm/upload/pannel",
			secureuri:false,
			fileElementId:'pannelFile',
			dataType: 'json',
			success: function (dom)
			{
				 if (dom.status='OK') {
				    	alert(dom);
				  }else{
				    	alert(dom.errors);
				  }
			}
		}
	)
	return false;
}
function ajaxUploadseries()
{
	$.ajaxFileUpload
	(
		{
			url:"${ROOT}/admin/comm/upload/series",
			secureuri:false,
			fileElementId:'seriesFile',
			dataType: 'json',
			success: function (dom)
			{
				 if (dom.status='OK') {
				    	alert(dom);
				  }else{
				    	alert(dom.errors);
				  }
			}
		}
	)
	return false;
}
function ajaxUploadProductSeries()
{
	$.ajaxFileUpload
	(
		{
			url:"${ROOT}/admin/comm/upload/productSeries",
			secureuri:false,
			fileElementId:'productSeriesFile',
			dataType: 'json',
			success: function (dom)
			{
				 if (dom.status='OK') {
				    	alert(dom);
				  }else{
				    	alert(dom.errors);
				  }
			}
		}
	)
	return false;
}
function ajaxUploadProduct()
{
	$.ajaxFileUpload
	(
		{
			url:"${ROOT}/admin/comm/upload/product",
			secureuri:false,
			fileElementId:'productFile',
			dataType: 'json',
			success: function (dom)
			{
				 if (dom.status='OK') {
				    	alert(dom);
				  }else{
				    	alert(dom.errors);
				  }
			}
		}
	)
	return false;
}
function ajaxUploadArea()
{
	$.ajaxFileUpload
	(
		{
			url:"${ROOT}/admin/comm/upload/area",
			secureuri:false,
			fileElementId:'areaFile',
			dataType: 'json',
			success: function (dom)
			{
				 if (dom.status='OK') {
				    	alert(dom);
				  }else{
				    	alert(dom.errors);
				  }
			}
		}
	)
	return false;
}
function ajaxUploadCity()
{
	$.ajaxFileUpload
	(
		{
			url:"${ROOT}/admin/comm/upload/city",
			secureuri:false,
			fileElementId:'cityFile',
			dataType: 'json',
			success: function (dom)
			{
				 if (dom.status='OK') {
				    	alert(dom);
				  }else{
				    	alert(dom.errors);
				  }
			}
		}
	)
	return false;
}
</script>
</body>
</html>