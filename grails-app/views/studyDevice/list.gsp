
<%@ page import="au.org.intersect.bdcp.StudyDevice" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDevice.label', default: 'StudyDevice')}" />
        <title><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></title>
    </head>
    <body>
        
        <div class="body" id="tab5"> 
           <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
    
	<ul id="tabnav">
	
		<li class="tab1"><a href="${createLink(controller:'study', action:'show', params:['id': studyInstance.id]) }" id="${studyInstance.id}" name="details">Details</a></li>
		<li class="tab2"><a href="${createLink(mapping:'participantDetails', controller:'participant', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-participants" name="#tabs-participants"><span>Participants</span></a></li>
		<li class="tab3"><a href="${createLink(mapping: 'componentDetails', controller:'component', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-components" name="#tabs-components"><span>Components</span></a></li>
		<li class="tab4"><a href="${createLink(mapping: 'sessionFileList', controller:'sessionFile', action:'fileList', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Files</span></a></li>
	    <li class="tab5"><a href="${createLink(mapping: 'studyDeviceDetails', controller:'studyDevice', action:'list', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Devices</span></a></li>
    </ul>
            <br />
            <span class="menuButton"><g:link elementId="Add Device" mapping="studyDeviceDetails" class="create" action="create" params="[studyId: studyInstance.id]">Add Device</g:link></span>
            <br />
            <br />
            <div class="projects">
                <g:each in="${deviceGroupsMapping}" status="i" var="deviceGroupMap">
                <h2>${deviceGroupMap?.deviceGroup.groupingName}</h2>
                <ul>
                <g:each in="${deviceGroupMap?.devices}" status="j" var="deviceInstance">
                <li>${deviceInstance?.name} - ${deviceInstance.vendor} - ${deviceInstance.modelName} <g:link elementId="edit_${j}" mapping="studyDeviceFieldDetails" class="button" action="edit" params="['device.id':deviceInstance.id,'study.id':params.studyId, studyId: params.studyId]">Edit</g:link></li>
                </g:each>
                </ul>
                </g:each>
                
            </div>
        </div>
    </body>
</html>
