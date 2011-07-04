

<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:javascript library="jquery" plugin="jquery"/>
        <jqui:resources />
        <g:render template="/shared/ckeditor" />
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${resultsDetailsFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${resultsDetailsFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${resultsDetailsFieldInstance?.id}" />
                <g:hiddenField name="version" value="${resultsDetailsFieldInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="fieldLabel"><g:message code="resultsDetailsField.fieldLabel.label" default="Field Label" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'fieldLabel', 'errors')}">
                                    <g:textField id="label" name="fieldLabel" value="${resultsDetailsFieldInstance?.fieldLabel}" />
                                </td>
                            </tr>
                           
                            <g:hiddenField name="fieldType" value="${resultsDetailsFieldInstance?.fieldType}" />
                            
                            <tr class="radiobutton" id="staticFieldRow">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="resultsDetailsField.staticContent.label" default="Static content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'staticContent', 'errors')}">
                                    <ckeditor:editor name="staticContent">${resultsDetailsFieldInstance?.staticContent}</ckeditor:editor>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:link controller="resultsDetailsField" elementId="cancel" action="list">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
