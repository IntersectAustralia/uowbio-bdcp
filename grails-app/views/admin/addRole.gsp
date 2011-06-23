
<%@ page import="au.org.intersect.bdcp.UserStore"%>
<%@ page import="au.org.intersect.bdcp.enums.UserRoles"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Select User Role</title>
</head>
<body>
<div class="body">

<h1>Select User Role</h1>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if> 
<g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
</g:if>
<g:form >

	<g:hiddenField name="username" value="${username}" />

	<div class="dialog">
		<g:select id="selectRole" noSelection="['':'']" from="${UserRoles.list()}" name="authority" value="${userInstance?.authority}"></g:select>
	</div>

	<div class="rowBottom">

	<div class="buttons">
		<span class="button">
			<g:actionSubmit name="save" id="save" class="save" controller="admin" action="create" value="${message(code: 'default.button.save.label', default: 'Select')}" />
		</span>
		<span class="menuButton">
			<g:link controller="admin" elementId="Back" class="create" action="searchUsers" params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Back</g:link>
		</span>
	</div>

</g:form></div>

</body>
</html>
