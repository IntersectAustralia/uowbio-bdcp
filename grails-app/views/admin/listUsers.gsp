<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>List All Users</title>
    </head>
    <body>
    <div class="body">

    <h1>List All Users</h1>
    <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
     
     <g:form action="sortListUsers" params="[showAllUsers: params.showAllUsers]" method="post">       
     <h1>${showAllUsers}</h1>
     <table>
     <tbody>
     <tr class="prop">
                            <td valign="top" class="name"><g:message code="admin.enabled.label" default="Hide Deactivated Users" /></td>
                            
                            <td valign="top" class="value"><g:checkBox elementId="hide deactivated users" name="hide deactivated users" value="${params.showAllUsers}" onchange="this.form.submit();"/></td>
                            
                        </tr>
     </tbody>
     </table>       
    <g:if test="${showAllUsers}">
    <g:render template="listUsers" model="['matches': matches]"/>
    </g:if>
    <g:else>
    <g:render template="listUsers" model="['matches': activatedUsers]"/>
    </g:else>
    </g:form>
   <div class="buttons">
   					<span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="accountAdmin">Back</g:link></span>
                </div>
    </div>
    
    </body>
</html>