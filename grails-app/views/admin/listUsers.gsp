<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:javascript library="prototype" />
        <title>List All Users</title>
        
    </head>
    <body>
    <div class="body">

    <h1>List All Users</h1>
    <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
     
     <h1>${params.showAllUsers}</h1>
     <table>
     <tbody>
     <tr class="prop">
                            <td valign="top" class="name"><g:message code="admin.enabled.label" default="Hide Deactivated Users" /></td>
                            
                            <td valign="top" class="value"><g:checkBox name='showAllUsers' value="${showAllUsers}"
onclick="location.href='${g:createLink action:'listUsers', params:['showAllUsers': showAllUsers] }'"/></td>
                            
                        </tr>
     </tbody>
     </table>
    <g:render template="listUsers" model="['matches': matches]"/>
   <div class="buttons">
   					<span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="accountAdmin">Back</g:link></span>
                </div>
    </div>
    
    </body>
</html>