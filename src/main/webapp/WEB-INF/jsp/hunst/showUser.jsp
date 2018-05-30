<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>我的弹框</title>

<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<script src="${ctx}/js/jquery-1.12.2.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Project name</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#"><span
							class="glyphicon glyphicon-home">Home</span></a></li>
					<li><a href="#shop"><span
							class="glyphicon glyphicon-shopping-cart">Shop</span></a></li>
					<li><a href="#support"><span
							class="glyphicon glyphicon-eur">Support</span></a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container" style="padding: 100px 50px 10px;">
		<button type="button" class="btn btn-default" title="Popover title"
			data-container="body" data-toggle="popover" data-placement="left"
			data-content="左侧的 Popover 中的一些内容">左侧的 Popover</button>
		<button type="button" class="btn btn-primary" title="Popover title"
			data-container="body" data-toggle="popover" data-placement="top"
			data-content="顶部的 Popover 中的一些内容">顶部的 Popover</button>
		<button type="button" class="btn btn-success" title="Popover title"
			data-container="body" data-toggle="popover" data-placement="bottom"
			data-content="底部的 Popover 中的一些内容">底部的 Popover</button>
		<button type="button" class="btn btn-warning" title="Popover title"
			data-container="body" data-toggle="popover" data-placement="right"
			data-content="右侧的 Popover 中的一些内容">右侧的 Popover</button>
		${user.userName}
	</div>
	<script>
		$(function() {
			$("[data-toggle='popover']").popover();
		});
	</script>
</body>
</html>