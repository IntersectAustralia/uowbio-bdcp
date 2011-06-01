
<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <meta name="layout" content="main" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    
    </head>
    <body>
       <div class="body" id="tab2">
            <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
        <div id="participant">
        <ul id="tabnav">
	
		<li class="tab1"><a href="${createLink(controller:'study', action:'show', params:['id': studyInstance.id]) }" id="${studyInstance.id}" name="details">Details</a></li>
		<li class="tab2"><a href="${createLink(mapping:'participantDetails', controller:'participant', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-participants" name="#tabs-participants"><span>Participants</span></a></li>
		<li class="tab3"><a href="${createLink(mapping: 'componentDetails', controller:'component', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-components" name="#tabs-components"><span>Components</span></a></li>
		<li class="tab4"><a href="${createLink(mapping: 'sessionFileList', controller:'sessionFile', action:'fileList', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Files</span></a></li>
	   <li class="tab5"><a href="${createLink(mapping: 'studyDeviceDetails', controller:'studyDevice', action:'list', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Devices</span></a></li>
    </ul>
	
        <g:render template="participants" />
        </div>
	</div>
    </body>
</html>