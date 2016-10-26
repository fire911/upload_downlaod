<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>SmartUpload批量上传文件</h1>
	<form action="smartUploadServlet" method="post" enctype="multipart/form-data">
		文件1：<input type="file" name="file1"/><br/>
		文件2：<input type="file" name="file2"/><br/>
		文件3：<input type="file" name="file3"/><br/>
		<input type="submit" value="上传"><br/>
		${result}
	</form><br/>
	<a href="smartDownloadServlet?filename=1.jpg">1.jpg</a>
	<form action="batchDownloadServlet" method="post">
		<input type="checkbox" name="filename" value="1.jpg">file1
		<input type="checkbox" name="filename" value="20160304.txt">file2
		<input type="checkbox" name="filename" value="记录.txt">file3
		<input type="submit" value="下载">
	</form>
</body>
</html>