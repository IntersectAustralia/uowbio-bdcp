
<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>User Details</title>
    </head>
    <body>
        <div class="body">
        <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <h1>User Details</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table id="userDetailsTable">
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="admin.firstname.label" default="Firstname" /></td>
                            
                            <td valign="top" class="value">${matchInstance.givenName}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="admin.surname.label" default="Surname" /></td>
                            
                           <td valign="top" class="value">${matchInstance.sn}</td>
                                
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="admin.userid.label" default="User ID" /></td>
                            
                            <td valign="top" class="value">${matchInstance.username.toArray()[1]}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
            <span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="listUsers">Back</g:link></span>
        	</div>
        </div>
    </body>
</html>
