
<%@ page import="au.org.intersect.bdcp.Component" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <g:set var="entityName" value="${message(code: 'component.label', default: 'Component')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="component">
        <br />
        <span class="menuButton"><g:link mapping="componentDetails" controller="component" class="create" action="create" params="[studyId: studyInstance.id]">Add Component</g:link></span>
            <br />
            <br />
            <g:if test="${ componentInstanceTotal > 0}">
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <th>${message(code: 'component.name.label', default: 'Name')}</th>
                        
                            <th>${message(code: 'component.description.label', default: 'Description')}</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${componentInstanceList}" status="i" var="componentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: componentInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: componentInstance, field: "description")}</td>
                            
                            <td><g:link mapping="componentDetails" elementId="edit-component[${i}]" controller="component" action="edit" params="[studyId: params.studyId, id: componentInstance.id]">Edit</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${componentInstanceTotal}" />
            </div>
            </g:if>
            </div>
    </body>
</html>
