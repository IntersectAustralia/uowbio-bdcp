

<%@ page import="au.org.intersect.bdcp.ParticipantForm" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participantForm.label', default: 'ParticipantForm')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:each in="${participantForms}" status="i" var="participantFormsInstance">
            <g:hasErrors bean="${participantFormsInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantFormsInstance}" as="list" />
            </div>
            </g:hasErrors>
            </g:each>
            <g:uploadForm action="upload" mapping="participantFormDetails" params="[studyId: params.studyId, participantId: params.participantId]" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="participantForm.name.label" default="Form Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantForms[0], field: 'formName', 'errors')}">
                                    <input type="text" id="forms[0].formName" name="forms[0].formName"/>
                                </td>
                            </tr>
                        
  <!-- SNIP -->
  <tr class="prop">
    <td valign="top" class="name">
      <label for="form">Form</label>
    </td>
    <td valign="top">
      <input type="file" id="form.0" name="form.0"/>
    </td>
  </tr>
                        
                        
                            
                            <g:hiddenField name="forms[0].participant.id" value="${params.participantId }" />
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="participantForm.name.label" default="Form Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantForms[1], field: 'formName', 'errors')}">
                                    <input type="text" id="forms[1].formName" name="forms[1].formName"/>
                                </td>
                            </tr>
                        
  <!-- SNIP -->
  <tr class="prop">
    <td valign="top" class="name">
      <label for="form">Form</label>
    </td>
    <td valign="top">
      <input type="file" id="form.1" name="form.1"/>
    </td>
  </tr>
                        
                        
                            
                            <g:hiddenField name="forms[1].participant.id" value="${params.participantId }" />
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="participantForm.name.label" default="Form Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantForms[2], field: 'formName', 'errors')}">
                                    <input type="text" id="forms[2].formName" name="forms[2].formName"/>
                                </td>
                            </tr>
                        
  <!-- SNIP -->
  <tr class="prop">
    <td valign="top" class="name">
      <label for="form">Form</label>
    </td>
    <td valign="top">
      <input type="file" id="form.2" name="form.2"/>
    </td>
  </tr>
                        
                        
                            
                            <g:hiddenField name="forms[2].participant.id" value="${params.participantId }" />
                        </tbody>
                    </table>
                </div>
                
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:uploadForm>
        </div>
    </body>
</html>
