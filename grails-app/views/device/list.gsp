
<%@ page import="au.org.intersect.bdcp.Device" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'device.label', default: 'Device')}" />
        <title>${deviceGroupInstance?.groupingName}</title>
    </head>
    <body>
        <div class="body">
        <h1>${deviceGroupInstance?.groupingName}</h1>
        <br />
            <g:link mapping="deviceDetails" controller="device" class="button" action="create" params="[deviceGroupId: params.deviceGroupId]">Add new device</g:link>
            <br />
            <br />
            <g:if test="${deviceInstanceList.size() > 0}">
            <h2>Devices</h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>${message(code: 'device.name.label', default: 'Name')}</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${deviceInstanceList}" status="i" var="deviceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${deviceInstance.id}">${fieldValue(bean: deviceInstance, field: "name")}</g:link></td>
                            <td>Edit</td>                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${deviceInstanceTotal}" />
            </div>
            </g:if>
        </div>
    </body>
</html>
