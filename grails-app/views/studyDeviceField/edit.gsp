

<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDeviceField.label', default: 'StudyDeviceField')}" />
        <g:javascript library="jquery" plugin="jquery" />
        <jqui:resources />
        <script>
        function jq(myid) { 
               return '#' + myid.replace(/(:|\.)/g,'\\$1');
             }
        </script>
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
            
            
            <g:form id="saveForm" method="post"  mapping="studyDeviceFieldDetails" controller="studyDeviceField" params="['device.id':params.device.id,'study.id':params.studyId, studyId: params.studyId]">
                <g:if test="${au.org.intersect.bdcp.DeviceField.findAllByDevice(Device.findById(params.device.id))?.size() > 0}">
                <div class="dialog">
                    <table>
                       <g:each in="${deviceFields.sort {x,y -> x.dateCreated <=> y.dateCreated}}" status="i" var="deviceFieldInstance">
                            <g:hiddenField name="studyDeviceFields[${i}].id" value="${studyDeviceFields[i]?.id}" />
                            <g:hiddenField name="studyDeviceFields[${i}].version" value="${studyDeviceFields[i]?.version}" />
                            <g:render template="${deviceFieldInstance.fieldType.toString().toLowerCase()}"  model = "['i':i, 'studyDeviceFields': studyDeviceFields, 'deviceFieldInstance':deviceFieldInstance]"/>
                        </g:each>
                    </table>
                </div>
                </g:if>
                <div class="buttons">
                    <span class="button"><g:actionSubmit id="update" class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                   <span class="button"><g:link elementId="cancel" mapping="studyDeviceDetails" controller="studyDevice" action="list" params="[studyId: params.studyId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
