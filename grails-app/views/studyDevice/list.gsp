
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
            
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            <br />
            <br />
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'studyDevice.id.label', default: 'Id')}" />
                        
                            <th><g:message code="studyDevice.device.label" default="Device" /></th>
                        
                            <th><g:message code="studyDevice.study.label" default="Study" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${studyDeviceInstanceList}" status="i" var="studyDeviceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${studyDeviceInstance.id}">${fieldValue(bean: studyDeviceInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: studyDeviceInstance, field: "device")}</td>
                        
                            <td>${fieldValue(bean: studyDeviceInstance, field: "study")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${studyDeviceInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
