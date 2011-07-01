

<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${resultsDetailsFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${resultsDetailsFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fieldLabel"><g:message code="resultsDetailsField.fieldLabel.label" default="Field Label" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'fieldLabel', 'errors')}">
                                    <g:textArea name="fieldLabel" cols="40" rows="5" value="${resultsDetailsFieldInstance?.fieldLabel}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fieldType"><g:message code="resultsDetailsField.fieldType.label" default="Field Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'fieldType', 'errors')}">
                                    <g:select name="fieldType" from="${au.org.intersect.bdcp.enums.FieldType?.values()}" keys="${au.org.intersect.bdcp.enums.FieldType?.values()*.name()}" value="${resultsDetailsFieldInstance?.fieldType?.name()}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="resultsDetailsField.staticContent.label" default="Static Content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'staticContent', 'errors')}">
                                    <g:textArea name="staticContent" cols="40" rows="5" value="${resultsDetailsFieldInstance?.staticContent}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fieldOptions"><g:message code="resultsDetailsField.fieldOptions.label" default="Field Options" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'fieldOptions', 'errors')}">
                                    <g:textField name="fieldOptions" value="${resultsDetailsFieldInstance?.fieldOptions}" />
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
