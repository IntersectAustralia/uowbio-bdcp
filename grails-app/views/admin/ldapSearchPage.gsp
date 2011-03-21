<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>New User Account</title>
    </head>
    <body>
    <div class="body">
    <h1>New User Account</h1>
    <div class="dialog">
                <table>
                    <tbody>
                    
                         <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="firstName">First Name</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}">
                                    <g:textField name="firstName" value="${session?.firstName}" />
                                </td>
                            </tr>
                        <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surname">Surname</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}">
                                    <g:textField name="surname" value="${session?.surname}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userid">User ID</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}">
                                    <g:textField name="userid" value="${session?.userid}" />
                                </td>
                            </tr>
                        
                    </tbody>
                 </table>
    </div>
    <div class="buttons">
                    <span class="button"><g:submitButton name="create" id="upload" class="save" value="Search" /></span>
                    </div>
    <div class="rowBottom">
   <div class="buttons">
                    <span class="button"><g:actionSubmit name="save" id="save" class="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <span class="button"><g:link elementId="cancel" controller="admin" action="create" >Cancel</g:link></span>
                </div>
    </div>
    </div>
    </body>
</html>