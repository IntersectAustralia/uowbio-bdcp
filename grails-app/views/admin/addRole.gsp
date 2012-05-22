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
 
<h1>User role and identifier (optional)</h1>

<g:form>

      <g:if test="${formErrors.hasErrors()}">
        <div class="errors">
            <g:each in="${formErrors.allErrors}" var="error">
            <li><g:message error="${error}"/></li>
            </g:each>
        </div>
      </g:if>

      <g:hiddenField name="isExternal" value="${isExternal}" />
      <g:hiddenField name="userid" value="${userid}" />
      <g:hiddenField name="username" value="${username}" />
      <g:hiddenField name="firstName" value="${firstName}" />
      <g:hiddenField name="surname" value="${surname}" />
      <g:hiddenField name="email" value="${email}" />
      <g:hiddenField name="password" value="${password}" />
 
      <div class="dialog">
         <table border="0">
          <tr><td><label for="selectRole">User role</label></td>
                <td><g:select id="selectRole" noSelection="['':'']" from="${UserRole.list()}" keys="${UserRole.listValues()}" name="authority" value="${userInstance?.authority}"></g:select></td></tr>
            <tr><td><label for="nlaIdentifier"><g:message code="admin.nlaIdentifier.label" default="NLA Persistence Identifier" /></label></td>
		<td><g:textField id="nlaIdentifier" name="nlaIdentifier" value="${nlaIdentifier}" /></td></tr>
            <tr><td><label for="title"><g:message code="admin.title.label" default="Title" /></label></td>
		<td><g:textField id="title" name="title" value="${title}" /></td></tr>
         </table>
      </div>
 
      <div class="rowBottom"></div>
 
      <div class="buttons">
            <span class="button">
                  <g:actionSubmit name="save" id="select" class="save right" controller="admin" action="create" value="${message(code: 'default.button.select.label', default: 'Select')}" />
            </span>
            <span class="menuButton">
                  <g:if test="${isExternal}">
                  <g:link controller="admin" elementId="Back" class="list" action="displayCreateExternalUser" 
                          params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Back</g:link>
                  </g:if>
                  <g:else>
                  <g:link controller="admin" elementId="Back" class="list" action="searchUsers" 
                          params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Back</g:link>
                  </g:else>
            </span>
      </div>
 
</g:form></div>

 
</body>
</html>
