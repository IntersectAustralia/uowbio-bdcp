
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
                    
                        <g:each in="${fileResourceInstanceList}" status="i" var="fileResourceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            
                            <td><g:if test="${participantFormInstanceList}" ><g:link action="show" id="${participantInstance.id}">${fieldValue(bean: participantFormInstance, field: "formName")}</g:link></g:if></td>
                        
							<td><a href="${createLinkTo( dir:'uploads/forms' , file: fileResourceInstance.decodeURL(), absolute:true )}" target="_new">${fileResourceInstance.decodeURL()}</a></td>
                        
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
            <g:form method="post"  enctype="multipart/form-data">
	                <div class="dialog">
	                    <table>
	                        <tbody>
	                        <tr class="prop">
	                                <td valign="top" class="name">
	                                    <label for="name">Form Name:</label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean:fileResourceInstance,field:'name','errors')}">
	                                    <input type="text" id="name" name="name" />
	                                </td>
	                            </tr> 
	                            <tr class="prop">
	                                <td valign="top" class="name">
	                                    <label for="fileUpload">Upload:</label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean:fileResourceInstance,field:'upload','errors')}">
	                                    <input type="file" id="fileUpload" name="fileUpload" />
	                                </td>
	                            </tr> 
	                        </tbody>
	                    </table>
	                </div>
	                <div class="buttons">
	                    <span class="button"><g:actionSubmit class="upload" value="Upload" action="upload" /></span>
	                </div>
	            </g:form>
	            <g:form>
	            <div class="buttons">
	                    <span class="button"><g:link elementId="return" mapping="participantDetails" controller="participant" action="list" params="[studyId: params.studyId]">Return to Participants</g:link></span>
	                </div>
	            </g:form>
        </div>
    </body>
</html>
