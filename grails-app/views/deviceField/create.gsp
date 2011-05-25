

<%@ page import="au.org.intersect.bdcp.DeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceField.label', default: 'DeviceField')}" />
        <title>Add New ${deviceInstance.name} Metadata Template Field</title>
        <g:javascript library="jquery" plugin="jquery"/>
        <jqui:resources />
        <ckeditor:resources />
        <ckeditor:config var="toolbar_Default">
        [
	 	['Source'],
	 	['Cut','Copy','Paste','PasteText','PasteFromWord'],
	 	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat','-','Maximize', 'ShowBlocks'],
	 	'/',
	 	['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],['TextColor','BGColor'],
	 	['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],
	 	['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
	 	['Table','HorizontalRule','SpecialChar','PageBreak'],
	 	['Styles','Format','Font','FontSize']
	    ]
        </ckeditor:config>
        <ckeditor:config enterMode="CKEDITOR.ENTER_BR" toolbar="Default"/>
        <g:javascript>
        $(function() {
            function showOrHideStatic() {
        		if ($("input[name='fieldType']:checked").val() == 'STATIC_TEXT') {
        			$('#staticFieldRow').show();
        		} else {
        			$('#staticFieldRow').hide();
        		}
            }
        	$("input[name='fieldType']").change(function() {
        	    showOrHideStatic();
        	});
        	showOrHideStatic();
        })
        </g:javascript>
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
                           
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="fieldType"><g:message code="deviceField.fieldType.label" default="Field Type" /></label>
                                </td>
                                <td style="padding:0px 0px;">
                                <table style="border: 0px; padding:0px;">
                                <g:each in="${au.org.intersect.bdcp.enums.FieldType?.listValues().partition(3)}" var="row">
                                <tr>
                                   <g:each in="${row}" var="fieldTypeOption">
                                   <g:set var="checked" value="${fieldTypeOption.equals(deviceFieldInstance?.fieldType)?'checked=checked':''}"/>
                                   <td><span><input type="radio" value="${fieldTypeOption}" name="fieldType"
                                     ${checked}
                                     >&nbsp;<g:message code="deviceField.fieldType.${fieldTypeOption.name}" /></span></td>
                                   </g:each>
                                </tr>
                                </g:each>
		                        </table>
		                        </td>
                            </tr>
                            
                            <tr class="radiobutton" id="staticFieldRow">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="deviceField.staticContent.label" default="Static content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'staticContent', 'errors')}">
                                    <ckeditor:editor name="text">${deviceFieldInstance?.staticContent}</ckeditor:editor>
                                </td>
                            </tr>
                           
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
