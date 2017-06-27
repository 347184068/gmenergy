<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
      html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
      .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
        	-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
      .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
      .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
      .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
      .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
      .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
      label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
	<style>
		body,html{
			background: -webkit-linear-gradient(#333,#3e3e3e,#333); /* Safari 5.1 - 6.0 */
			background: -o-linear-gradient(#333,#3e3e3e,#333); /* Opera 11.1 - 12.0 */
			background: -moz-linear-gradient(#333,#3e3e3e,#333); /* Firefox 3.6 - 15 */
			background: linear-gradient(#333,#3e3e3e,#333); /* 标准的语法 */
		}
		.login-box{
			margin:0 auto;
			width: 750px;
			height: auto;
			overflow: hidden;
			background: #f9f9f9;
			border-radius:5px;
			box-shadow:1px 1px 15px #000;
		}
		.login-title{
			padding: 30px 0;
			background: #2c2c2c;
			color:#f99331;
			border-bottom:3px solid #f99331;
			font-size:32px;
			text-align: left;
			text-indent: 25px;
		}
		.login-context{
			height: auto;
			overflow: hidden;
		}
		.login-context>.login-left{
			width:358px;
			float: left;
		}
		.login-left>.form-signin{
			border:0;
			box-shadow:none;
			background: transparent;
		}
		.login-context>.login-right{
			float:right;
			width:392px;
		}
		.login-context>.login-right>img{;
			width: 100%;
		}
		.form-signin{
			margin:0;
			padding-top:60px;
			padding-bottom:0;
		}
		.form-signin>.weui-btn{
			padding: 6px 12px;
			display:block;
			width:280px;
			margin:0 auto;
			background:#f99331;
			color:#fff;
			box-shadow:none;
		}
		.footer{
			padding: 10px 0;
			line-height:25px;
		}
	</style>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<div class="login-box">
		<div class="login-title">
			${fns:getConfig('productName')}
		</div>
		<div class="login-context">
			<div class="login-left">
				<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
					<label class="input-label" for="username">登录名</label>
					<input type="text" id="username" name="username" class="input-block-level required" value="${username}">
					<label class="input-label" for="password">密码</label>
					<input type="password" id="password" name="password" class="input-block-level required">
					<c:if test="${isValidateCodeLogin}"><div class="validateCode">
						<label class="input-label mid" for="validateCode">验证码</label>
						<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
					</div></c:if><%--
					<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
					<label for="rememberMe" title="下次不需要再登录" style="margin-bottom:20px;float:right;"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>
						<input class="btn weui-btn" type="submit" value="登 录"/>&nbsp;&nbsp;
					<div id="themeSwitch" class="dropdown" style="display: none">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a>
						<ul class="dropdown-menu">
						  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
						</ul>
						<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
					</div>
				</form>
			</div>
			<div class="login-right">
				<img src="${ctxStatic}/images/login_bg.png">
			</div>
		</div>
		<!--  <div class="footer">
			Copyright &copy; 2012-${fns:getConfig('copyrightYear')} <br /><a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a> - Powered By <a href="http://jeesite.com" target="_blank">JeeSite</a> ${fns:getConfig('version')} 
		</div>-->
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>