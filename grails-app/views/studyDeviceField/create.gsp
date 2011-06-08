

<%@ page import="au.org.intersect.bdcp.StudyDeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDeviceField.label', default: 'StudyDeviceField')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${studyDeviceFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${studyDeviceFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
            
            <br />
            <div class="dialog">
            <table>
            <g:each in="${au.org.intersect.bdcp.DeviceField.list()}" status="i" var="deviceFieldInstance">
            <g:render template="${deviceFieldInstance.fieldType.toString().toLowerCase()}"  model = "['i':i, 'studyDeviceFields': studyDeviceFields, 'deviceFieldInstance':deviceFieldInstance, 'studyDeviceFieldInstance':studyDeviceFieldInstance]"/>
            </g:each>
            </table>
            </div>
            <br />
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="text"><g:message code="studyDeviceField.text.label" default="Text" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'text', 'errors')}">
                                    <g:textArea name="text" cols="40" rows="5" value="${studyDeviceFieldInstance?.text}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="textArea"><g:message code="studyDeviceField.textArea.label" default="Text Area" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'textArea', 'errors')}">
                                    <g:textArea name="textArea" cols="40" rows="5" value="${studyDeviceFieldInstance?.textArea}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numeric"><g:message code="studyDeviceField.numeric.label" default="Numeric" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'numeric', 'errors')}">
                                    <g:textField name="numeric" value="${fieldValue(bean: studyDeviceFieldInstance, field: 'numeric')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date"><g:message code="studyDeviceField.date.label" default="Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'date', 'errors')}">
                                    <g:datePicker name="date" precision="day" value="${studyDeviceFieldInstance?.date}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="time"><g:message code="studyDeviceField.time.label" default="Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'time', 'errors')}">
                                    <g:datePicker name="time" precision="day" value="${studyDeviceFieldInstance?.time}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="radioButtonsOption"><g:message code="studyDeviceField.radioButtonsOption.label" default="Radio Buttons Option" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'radioButtonsOption', 'errors')}">
                                    <g:textArea name="radioButtonsOption" cols="40" rows="5" value="${studyDeviceFieldInstance?.radioButtonsOption}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dropDownOption"><g:message code="studyDeviceField.dropDownOption.label" default="Drop Down Option" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'dropDownOption', 'errors')}">
                                    <g:textArea name="dropDownOption" cols="40" rows="5" value="${studyDeviceFieldInstance?.dropDownOption}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="studyDeviceField.staticContent.label" default="Static Content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'staticContent', 'errors')}">
                                    <g:textArea name="staticContent" cols="40" rows="5" value="${studyDeviceFieldInstance?.staticContent}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="deviceField"><g:message code="studyDeviceField.deviceField.label" default="Device Field" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'deviceField', 'errors')}">
                                    <g:select name="deviceField.id" from="${au.org.intersect.bdcp.DeviceField.list()}" optionKey="id" value="${studyDeviceFieldInstance?.deviceField?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="studyDevice"><g:message code="studyDeviceField.studyDevice.label" default="Study Device" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'studyDevice', 'errors')}">
                                    <g:select name="studyDevice.id" from="${au.org.intersect.bdcp.StudyDevice.list()}" optionKey="id" value="${studyDeviceFieldInstance?.studyDevice?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
