

<%@ page import="au.org.intersect.bdcp.DeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceField.label', default: 'DeviceField')}" />
        <title>Add New ${deviceInstance.name} Metadata Template Field</title>
        <g:javascript library="jquery" plugin="jquery"/>
        <jqui:resources />
        <script type="text/javascript">
        function showOptions(obj){
            if (obj == "dropDown.label" || obj == "radioButtons.label")
            {
            	document.getElementById("d1").style.display=""
            }
            else
            {
            	document.getElementById("d1").style.display="none"
            }
        }

        $(document).ready(function(){
        if (${deviceFieldInstance?.fieldType.toString() == "DROP_DOWN" || deviceFieldInstance?.fieldType.toString() == "RADIO_BUTTONS"})
 		   {
				 	document.getElementById("d1").style.display=""
 		   }
		   else
 		   {
					document.getElementById("d1").style.display="none"
 		   }
       });

    </script>
=======
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
                           
                         <g:set var="i" value="${0}" />
                        <g:radioGroup name="fieldType" horizontal="true" labels="${au.org.intersect.bdcp.enums.FieldType?.list()}" values="${au.org.intersect.bdcp.enums.FieldType?.values()}" value="${deviceFieldInstance?.fieldType}"> 
                        <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <g:if test="${i++ < 1}"
                                    <label for="fieldType"><g:message code="deviceField.fieldType.label" default="Field Type" /></label>
                                    </g:if>
                                </td>
                                <td>
                                 <span onclick="showOptions('${it.label}')"> ${it.radio} <g:message code="deviceField.fieldType.${it.label}" /></span>
                                </td>
                        </tr>
                        </g:radioGroup>
                        <tr class="radiobutton" id="d1" style="display:none">
                                <td valign="top" class="name">
                                    <label for="fieldOptions"><g:message code="deviceField.fieldLabel.label" default="Field Options" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'fieldOptions', 'errors')}">
                                    <g:textArea id="fieldOptions" name="fieldOptions" value="${deviceFieldInstance?.fieldOptions}" />
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
