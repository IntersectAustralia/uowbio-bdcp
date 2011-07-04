
<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table id="fieldDetailsTable">
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="resultsDetailsField.fieldLabel.label" default="Field Label" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean:resultsDetailsFieldInstance, field: "fieldLabel")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="resultsDetailsField.fieldType.label" default="Field Type" /></td>
                            
                            <td valign="top" class="value"><g:message code="resultsDetailsField.fieldType.${resultsDetailsFieldInstance?.fieldType?.getName()}" /></td>
                            
                        </tr>
                        
                        <g:each in="${resultsDetailsFieldInstance?.fieldOptions.split('\n')}" status="i" var="resultsDetailsFieldOption">
                        <tr class="radiobutton">
                            <g:if test="${i < 1}">
                            <td valign="top" class="name"><g:message code="resultsDetailsField.fieldOptions.label" default="Field Options" /></td>
                            </g:if>
                            <g:else>
                            <td valign="top" class="name"></td>
                            </g:else>
                            <td valign="top" class="value">${resultsDetailsFieldOption}</td>
                        </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${resultsDetailsFieldInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:link controller="resultsDetailsField" elementId="cancel" action="list">Cancel</g:link></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
