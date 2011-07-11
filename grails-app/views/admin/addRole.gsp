<%@ page import="au.org.intersect.bdcp.UserStore"%>
<%@ page import="au.org.intersect.bdcp.enums.UserRole"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Select User Role</title>
</head>
<body>
<div class="body">
 
<h1>Select User Role</h1>

<g:form>
 
 <g:hasErrors bean="${user}">
      <div class="errors"><g:renderErrors bean="${user}" as="list" />
      </div>
</g:hasErrors>
<g:if test="${accountStatus == 'Failed'}">
      <p>Please assign a role.</p>
</g:if>
 
      <g:hiddenField name="username" value="${username}" />
 
      <div class="dialog">
            <g:select id="selectRole" noSelection="['':'']" from="${UserRole.list()}" keys="${UserRole.listValues()}" name="authority" value="${userInstance?.authority}"></g:select>
      </div>
 
      <div class="rowBottom">
 
      <div class="buttons">
            <span class="button">
                  <g:actionSubmit name="save" id="select" class="save" controller="admin" action="create" value="${message(code: 'default.button.select.label', default: 'Select')}" />
            </span>
            <span class="menuButton">
                  <g:link controller="admin" elementId="Back" class="create" action="searchUsers" params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Back</g:link>
            </span>
      </div>
 
</g:form></div>
</div>
 
</body>
</html>