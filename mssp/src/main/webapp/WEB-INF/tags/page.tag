<%-- 
    Document   : httpget
    Created on : Jul 5, 2013, 5:23:18 PM
    Author     : changshu.li
  不允许有多余的空格
--%><%--
--%><%@tag import="com.mmd.mssp.util.StringUtil"%><%
%><%@tag import="org.apache.commons.lang.StringUtils"%><%
%><%@tag import="java.text.MessageFormat"%><%--
--%><%@tag description="put the tag description here" pageEncoding="UTF-8" isELIgnored="false"%><%--
--%><%@attribute name="from" type="java.lang.Integer" required="true" description="当前页起始行的索引 从零开始"%><%--
--%><%@attribute name="size" type="java.lang.Integer" required="true" description="每页的行数"%><%--
--%><%@attribute name="totle" type="java.lang.Integer" required="true"%><%--
--%><%@attribute name="query" type="java.lang.Boolean" required="false" description="默认是true"%><%--
--%><%@attribute name="url" type="java.lang.String" required="true" description="标识{0}页号，从 1 开始 如 /page/{0}.html"%><%--
--%><ul class="pagination pull-right"><%
  // 例如： <jw:page from="100" size="10" totle="221" url="${ROOT}/page/{0}.html"></jw:page>
	if (query == null || query) {
		String qu = request.getQueryString();
		if (!StringUtils.isBlank(qu)) {
			int idx = url.indexOf('?');
			if (idx >= 0) {
				String qur = url.substring(idx + 1);
				String[] dx = StringUtils.split(qur, "&");
				for (String str : dx) {
					String[] strs = StringUtil.split2keyVal(str, "=");
					if (strs[0].length() > 0) {
						qu = StringUtil.removeQueryKeyValue(qu, strs[0]);
					}
				}
			}
			url = StringUtil.joinQuery(url, qu);
		}
	}
	int pre = 1, cct = 3, cet = 1;
	if (size == null || size <= 0) {
		size = 1;
	}
	if (from <= 0) {
		from = 0;
	}
	from = from + 1;
	int pagetotle = (int) Math.ceil((double) totle / size);
	if (pagetotle <= 0) {
		pagetotle = 1;
	}
	int currentpage = (int) Math.ceil((double) from / size);
	if (currentpage == 1) {
		out.print("<li><a  class=''>《</a></li>");
	} else {
		out.print(MessageFormat.format("<li ><a  href=\"" + url + "\" class=\"\">《{1}</a></li>", currentpage - 1 + "", ""));
	}
	if (pagetotle <= pre + (cct * 2 + 1) + cet) {
		for (int i = 1; i <= pagetotle; i++) {
			if (i == currentpage) {
				out.print("<li class='disabled active'><a>" + i + "</a></li>");
			} else {
				out.print(MessageFormat.format("<li><a href=\"" + url + "\">{0}</a></li>", i + ""));
			}
		}
	} else {
		int i = 1;
		for (; i <= pre; i++) {
			if (i == currentpage) {
				out.print("<li class='disabled active'><a>" + i + "</a></li>");
			} else {
				out.print(MessageFormat.format("<li><a href=\"" + url + "\">{0}</a></li>", i + ""));
			}
		}
		if (pre + cct + 1 < currentpage) {
			out.print("<li><a> ... </a></li>");
			i = currentpage - cct;
		}
		for (; i < pagetotle && i <= currentpage + cct; i++) {
			if (i == currentpage) {
				out.print("<li class='disabled active'><a>" + i + "</a></li>");
			} else {
				out.print(MessageFormat.format("<li><a href=\"" + url + "\">{0}</a></li>", i + ""));
			}
		}
		if (currentpage + cct + cet + 1 < pagetotle) {
			out.print("<li><a> ... </a></li>");
			i = pagetotle - cet + 1;
		}

		for (; i <= pagetotle; i++) {
			if (i == currentpage) {
				out.print("<li class='disabled active'><a>" + i + "</a></li>");
			} else {
				out.print(MessageFormat.format("<li><a href=\"" + url + "\">{0}</a></li>", i + ""));
			}
		}
	}
	if (currentpage == pagetotle) {
		out.print("<li ><a class=''>》</a></li>");
	} else {
		out.print(MessageFormat.format("<li ><a  href=\"" + url + "\" class=\"\" >{1}》</a></li>", currentpage + 1 + "", ""));
	}
  %></ul>