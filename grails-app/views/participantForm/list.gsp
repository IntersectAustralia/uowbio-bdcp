
<%@ page import="au.org.intersect.bdcp.ParticipantForm" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participantForm.label', default: 'ParticipantForm')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Participant ${participantInstance}</h1>
            <h2>Forms</h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="formName" title="${message(code: 'participantForm.formName.label', default: 'Form Name')}" />
                        
                            <g:sortableColumn property="form" title="${message(code: 'participantForm.link.label', default: 'Form')}" />
                        
                            <th><g:message code="participantForm.participant.label" default="Participant" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${participantFormInstanceList}" status="i" var="participantFormInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            
                            <td><g:if test="${participantFormInstanceList}" ><g:link action="show" id="${participantFormInstance.id}">${fieldValue(bean: participantFormInstance, field: "formName")}</g:link></g:if></td>
                        
							<td>${participantFormInstance?.form}</td>
                        
                            <td><g:if test="${participantFormInstanceList}">${fieldValue(bean: participantFormInstance, field: "participant")}</g:if></td>
                        
                        </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${participantFormInstanceTotal}" />
            </div>
            <h2> Add Forms</h2>
            <g:hasErrors bean="${participantFormInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantFormInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:uploadForm action="upload" mapping="participantFormDetails" params="[studyId: params.studyId, participantId: params.participantId]" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="participantForm.name.label" default="Form Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantFormInstance, field: 'formName', 'errors')}">
                                    <g:textField name="formName" value="${participantFormInstance?.formName}" />
                                </td>
                            </tr>
                        
  <!-- SNIP -->
  <tr class="prop">
    <td valign="top" class="name">
      <label for="form">Form</label>
    </td>
    <td valign="top">
      <input type="file" id="fileUpload" name="fileUpload"/>
    </td>
  </tr>
                        
                        
                            
                        <g:hiddenField name="participant.id" value="${params.participantId }" />
                        </tbody>
                    </table>
                </div>
                
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="upload" value="Upload" /></span>
                    <span class="button"><g:link elementId="return" mapping="participantDetails" controller="participant" action="list" params="[studyId: params.studyId]">Return to Participants</g:link></span>
                </div>
            </g:uploadForm>
        </div>
    </body>
</html>
