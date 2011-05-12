
<%@ page import="au.org.intersect.bdcp.Device" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'device.label', default: 'Device')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'device.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'device.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'device.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="manufacturer" title="${message(code: 'device.manufacturer.label', default: 'Manufacturer')}" />
                        
                            <g:sortableColumn property="locationOfManufacturer" title="${message(code: 'device.locationOfManufacturer.label', default: 'Location Of Manufacturer')}" />
                        
                            <g:sortableColumn property="model" title="${message(code: 'device.model.label', default: 'Model')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${deviceInstanceList}" status="i" var="deviceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${deviceInstance.id}">${fieldValue(bean: deviceInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: deviceInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: deviceInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: deviceInstance, field: "manufacturer")}</td>
                        
                            <td>${fieldValue(bean: deviceInstance, field: "locationOfManufacturer")}</td>
                        
                            <td>${fieldValue(bean: deviceInstance, field: "model")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${deviceInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
