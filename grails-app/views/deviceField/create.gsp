

<%@ page import="au.org.intersect.bdcp.DeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceField.label', default: 'DeviceField')}" />
        <title>Add New ${deviceInstance.name} Metadata Template Field</title>
    </head>
    <body>
        <div class="body">
            <h1>Add New ${deviceInstance.name} Metadata Template Field</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviceFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviceFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
             <g:form mapping="deviceFieldDetails" action="save" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId]" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="fieldLabel"><g:message code="deviceField.fieldLabel.label" default="Field Label" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'fieldLabel', 'errors')}">
                                    <g:textField id="label" name="fieldLabel" value="${deviceFieldInstance?.fieldLabel}" />
                                </td>
                            </tr>
                           
                         <g:set var="i" value="${0}" />
                        <g:radioGroup name="fieldType" horizontal="true" labels="${au.org.intersect.bdcp.enums.FieldType?.list()}" values="${au.org.intersect.bdcp.enums.FieldType?.values()}" value="${au.org.intersect.bdcp.enums.FieldType?.TEXT}"> 
                        <tr class="radiobutton">
                                <td valign="top" class="name">
                                <g:if test="${i++ < 1}"
                                    <label for="fieldType"><g:message code="deviceField.fieldType.label" default="Field Type" /></label>
                                </g:if>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'fieldType', 'errors')}" style="width:auto">
                                 <span onclick="return confirm('${it.label}');"> ${it.radio} <g:message code="deviceField.fieldType.${it.label}" /></span>
                                </td>
                        </tr>
                        </g:radioGroup>
                            <g:hiddenField name="device.id" value="${params.deviceId}" />
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="save" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link mapping="deviceFieldDetails" elementId="cancel" action="list" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
