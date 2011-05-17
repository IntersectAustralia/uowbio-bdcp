

<%@ page import="au.org.intersect.bdcp.DeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceField.label', default: 'DeviceField')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviceFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviceFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form mapping="deviceFieldDetails" method="post" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId]" >
                <g:hiddenField name="id" value="${deviceFieldInstance?.id}" />
                <g:hiddenField name="version" value="${deviceFieldInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fieldLabel"><g:message code="deviceField.fieldLabel.label" default="Field Label" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'fieldLabel', 'errors')}">
                                    <g:textArea name="fieldLabel" cols="40" rows="5" value="${deviceFieldInstance?.fieldLabel}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fieldType"><g:message code="deviceField.fieldType.label" default="Field Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'fieldType', 'errors')}">
                                    <g:select name="fieldType" from="${au.org.intersect.bdcp.enums.FieldType?.values()}" keys="${au.org.intersect.bdcp.enums.FieldType?.values()*.name()}" value="${deviceFieldInstance?.fieldType?.name()}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="device"><g:message code="deviceField.device.label" default="Device" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'device', 'errors')}">
                                    <g:select name="device.id" from="${au.org.intersect.bdcp.Device.list()}" optionKey="id" value="${deviceFieldInstance?.device?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
