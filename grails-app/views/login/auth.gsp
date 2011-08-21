<head>
<meta name='layout' content='main' />
<title>Login</title>
</head>

<body>
	<div id='login'>
		<div class='inner'>
			<h1>Biomechanics</h1>
			<div class='fheader'>Please enter your userid and password to login</div>
			<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
				<p>
					<label for='username'>User ID</label>
					<input type='text' class='text_' name='j_username' id='username' />
				</p>
				<p>
					<label for='password'>Password</label>
					<input type='password' class='text_' name='j_password' id='password' />
				</p>
				   <div id="buttons">
					<div id="button"><input id="Login" type='submit' value='Login' /></div>
					<div id="button"><input id="Clear" type="reset" value="Clear" /></div>
				   </div>
			</form>
			<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
			</g:if>
		</div>
	</div>
<script type='text/javascript'>
<!--
(function(){
	document.forms['loginForm'].elements['j_username'].focus();
})();
// -->
</script>
</body>
