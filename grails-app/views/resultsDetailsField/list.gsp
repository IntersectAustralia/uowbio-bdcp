
<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<%@ page import="au.org.intersect.bdcp.enums.FieldType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
        <title>Results Details Template</title>
    </head>
    <body>
        <div class="body">
            <h1>Results Details Template</h1>
            <br />
            <g:link elementId="Add Field" class="create" class="button" controller="resultsDetailsField" action="create" >Add Field</g:link>
            <br />
            <br />
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${resultsDetailsFieldInstanceList?.size() > 0}">
            <div class="list">
                <table id="listTable">
                    <thead>
                        <tr>
                            <g:sortableColumn property="fieldLabel" title="${message(code: 'resultsDetailsField.fieldLabel.label', default: 'Field Label')}" />
                        
                            <g:sortableColumn property="fieldType" title="${message(code: 'resultsDetailsField.fieldType.label', default: 'Field Type')}" />
                        
                            <g:sortableColumn property="mandatory" title="${message(code: 'resultsDetailsField.mandatory.label', default: 'Mandatory')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${resultsDetailsFieldInstanceList}" status="i" var="resultsDetailsFieldInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: resultsDetailsFieldInstance, field: "fieldLabel")}</td>
                        
                            <td><g:message code="resultsDetailsField.fieldType.${resultsDetailsFieldInstance?.fieldType?.getName()}" />
                            <g:if test="${resultsDetailsFieldInstance?.fieldType == FieldType.STATIC_TEXT}"
                            ><g:link elementId="edit_${i}" controller="resultsDetailsField" action="edit" class="button" id="${resultsDetailsFieldInstance?.id}"
                             >Edit</g:link
                             ></g:if>
                            <g:if test="${[FieldType.DROP_DOWN, FieldType.RADIO_BUTTONS].contains(resultsDetailsFieldInstance?.fieldType)}"
                            ><g:link elementId="show[${i}]" controller="resultsDetailsField" action="show" class="button" id="${resultsDetailsFieldInstance?.id}"
                             >Show</g:link
                             ></g:if>
                             </td>
                            
                            <td>${fieldValue(bean: resultsDetailsFieldInstance, field: "mandatory")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            </g:if>
            <br />
            <g:link class="list" class="button" controller="admin" action="resultsAdmin">Return to Results Administration</g:link>
        </div>
    </body>
</html>
