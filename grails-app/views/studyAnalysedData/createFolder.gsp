
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
   	<jqui:resources />
        <title><g:message code="default.list.label" args="[entityName]" /> - Create Analysed data folder</title>
    </head>
    <body>
    <div class="body" id="tab7"> 
    <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
    <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
    </g:if>

    <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab7']}" />

    <div id="tabs-details">
            <g:if test="${folderPath.length()!=0}">
            <h1><g:message code="study.files.analysed.folder.create.title.normal" args="${[params.folderPath]}"/></h1>
            </g:if>
            <g:else>
            <h1><g:message code="study.files.analysed.folder.create.title.root" /></h1>
            </g:else>

            <g:if test="${formErrors.hasErrors()}">
              <div class="errors">
                  <g:each in="${formErrors.allErrors}" var="error">
                  <li><g:message code="${error}"/></li>
                  </g:each>
              </div>
            </g:if>

            <g:form mapping="studyAnalysedData" params="[studyId: studyInstance.id]" action="doCreateFolder" >
            <g:hiddenField name="folderPath" value="${folderPath}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="folder"><g:message code="study.files.analysed.folder.label" default="Folder" /></label>
                                </td>
                                <td valign="top" class="value ${formErrors.hasFieldErrors('folderName') ? 'errors' : ''}">
                                    <g:textField name="folderName" value="${folderName}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" id="save" mapping="studyAnalysedData" params="[studyId: studyInstance.id]" class="save right list" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link elementId="cancel" class="list" mapping="studyAnalysedData" controller="studyAnalysedData" action="list" id="${studyInstance.id}" params="[studyId: studyInstance.id]">Cancel</g:link></span>
                </div>
            </g:form>
	</div>
	</div> 
    </body>
</html>
