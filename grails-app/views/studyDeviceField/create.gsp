

<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDeviceField.label', default: 'StudyDeviceField')}" />
        <g:javascript library="jquery" plugin="jquery" />
        <jqui:resources />
        <script type="text/javascript" src="${resource(dir:'jquery-ui-datepicker',file:'jquery.ui.datepicker-en-AU.js')}"></script>
        <title>${Device?.findById(params.device.id)} Details Template Form</title>
    </head>
    <body>
        <div class="body">
            <h1>${Device?.findById(params.device.id)} Details Template Form</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <g:each in="${studyDeviceFields}" status="i" var="studyDeviceFieldInstance">
            <g:hasErrors bean="${studyDeviceFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${studyDeviceFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
            </g:each>
            
            <br />
            
            <g:form id="saveForm" action="save" mapping="studyDeviceFieldDetails" controller="studyDeviceField" params="['device.id':params.device.id,'study.id':params.studyId, studyId: params.studyId]">
            <g:if test="${au.org.intersect.bdcp.DeviceField.findAllByDevice(Device.findById(params.device.id))?.size() > 0}">
            <div class="dialog">
            <table>
            <g:each in="${au.org.intersect.bdcp.DeviceField.findAllByDevice(Device.findById(params.device.id))}" status="i" var="deviceFieldInstance">
            <g:render template="${deviceFieldInstance.fieldType.toString().toLowerCase()}"  model = "['i':i, 'studyDeviceFields': studyDeviceFields, 'deviceFieldInstance':deviceFieldInstance, 'studyDeviceFieldInstance':studyDeviceFieldInstance]"/>
            </g:each>
            </table>
            </div>
            </g:if>
                <div class="buttons">
                    <span class="button"><g:submitButton name="save" class="save right list" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link elementId="cancel" class="list" mapping="studyDeviceDetails" controller="studyDevice" action="create" params="[studyId: params.studyId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
