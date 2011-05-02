
<%@ page import="au.org.intersect.bdcp.UserStore"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>User Details</title>
</head>
<body>
<div class="body">

<h1>User Details</h1>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if> 
<g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
            </g:if>
<g:form method="post" action="update"
	params="[hideUsers: params.hideUsers]">
	<g:hiddenField name="id" value="${userInstance?.id}" />
	<g:hiddenField name="version" value="${userInstance?.version}" />
	<g:hiddenField name="username" value="${userInstance.username}" />
	<div class="dialog">
	<table id="userDetailsTable">
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><g:message
					code="admin.firstname.label" default="Firstname" /></td>

				<td valign="top" class="value">
				${matchInstance.givenName}
				</td>

			</tr>

			<tr class="prop">
				<td valign="top" class="name"><g:message
					code="admin.surname.label" default="Surname" /></td>

				<td valign="top" class="value">
				${matchInstance.sn}
				</td>

			</tr>

			<tr class="prop">
				<td valign="top" class="name"><g:message
					code="admin.userid.label" default="User ID" /></td>

				<td valign="top" class="value">
				${matchInstance.username.toArray()[1]}
				</td>

			</tr>

			<tr class="prop">
				<td valign="top" class="name"><g:message
					code="admin.enabled.label" default="Deactivate User Account" /></td>

				<td valign="top" class="value" ${hasErrors(bean:userInstance,field:'deactivated','errors')}">
                    <g:checkBox
					elementId="deactivated" name="deactivated"
					value="${userInstance?.deactivated}" /></td>

			</tr>

		</tbody>
	</table>
	</div>


	<div class="buttons"><span class="button"><g:actionSubmit
		name="save" id="save" class="save" controller="admin"
		params="[hideUsers: params.hideUsers]" action="update"
		value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
	<span class="menuButton"><g:link elementId="Back"
		controller="admin" class="list" action="listUsers"
		params="[hideUsers: params.hideUsers]">Back</g:link></span></div>
</g:form></div>
</body>
</html>
