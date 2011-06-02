
<%@ page import="au.org.intersect.bdcp.Component" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <meta name="layout" content="main" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <g:set var="entityName" value="${message(code: 'component.label', default: 'Component')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    
    </head>
    <body>
       <div class="body" id="tab3">

            <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
        <div id="component">
        <ul id="tabnav">
	
		<li class="tab1"><a href="${createLink(controller:'study', action:'show', params:['id': studyInstance.id]) }" id="${studyInstance.id}" name="details">Details</a></li>
		<li class="tab2"><a href="${createLink(mapping:'participantDetails', controller:'participant', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-participants" name="#tabs-participants"><span>Participants</span></a></li>
		<li class="tab3"><a href="${createLink(mapping: 'componentDetails', controller:'component', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-components" name="#tabs-components"><span>Components</span></a></li>
		<li class="tab4"><a href="${createLink(mapping: 'sessionFileList', controller:'sessionFile', action:'fileList', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Files</span></a></li>
	    <li class="tab5"><a href="${createLink(mapping: 'studyDeviceDetails', controller:'studyDevice', action:'list', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Devices</span></a></li>
    </ul>
        <br />
        <span class="menuButton"><g:link elementId="createComponent" mapping="componentDetails" controller="component" class="create" action="create" params="[studyId: studyInstance.id]">Add Component</g:link></span>
            <br />
            <br />
            <g:if test="${ componentInstanceTotal > 0}">
            <div class="projects">
                    <g:each in="${componentInstanceList}" status="i" var="componentInstance">
                        
                            ${fieldValue(bean: componentInstance, field: "name")} - ${fieldValue(bean: componentInstance, field: "description")}
                            
                             <g:link mapping="componentDetails" elementId="edit-component[${i}]" controller="component" action="edit" params="[studyId: params.studyId, id: componentInstance.id]">Edit</g:link>
                        	<ul><g:each in="${componentInstance.getSessionsList()}" status="n" var="sessionInstance">
                        	<li>${fieldValue(bean: sessionInstance, field: "name")} - ${fieldValue(bean: sessionInstance, field: "description")} <g:link mapping="sessionDetails" elementId="edit-session[${n}]" controller="session" action="edit" params="[studyId: params.studyId, id: sessionInstance.id,componentId: componentInstance.id]">Edit</g:link> </li>
                        	</g:each>
                        	 <li><span class="menuButton"><g:link elementId="createSession[${i}]" mapping="sessionDetails" controller="session" class="create" action="create" params="[studyId: studyInstance.id, componentId: componentInstance.id]">Add Session</g:link></span></li>
                    		</ul>
                    </g:each>
            </div>
            </g:if>
            </div>
            </div>
            </div>
    </body>
</html>
