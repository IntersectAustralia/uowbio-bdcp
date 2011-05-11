
<%@ page import="au.org.intersect.bdcp.DeviceGroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceGroup.label', default: 'Device Administration')}" />
        <title>Device Administration</title>
    </head>
    <body>
        <div class="body">
            <h1>Device Administration</h1>
            <br />
            <g:link class="button" action="create">Create Grouping</g:link>
            <br />
            <br />
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test ="${deviceGroupInstanceList.size() > 0}" >
            <h2>Existing Devices</h2>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>${message(code: 'deviceGroup.groupingName.label', default: 'Grouping Name')}</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${deviceGroupInstanceList}" status="i" var="deviceGroupInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${fieldValue(bean: deviceGroupInstance, field: "groupingName")}</td>
                            <td><g:link class="button" action="edit" id="${deviceGroupInstance.id}">Edit name</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${deviceGroupInstanceTotal}" />
            </div>
            </g:if>
            <div class="buttons"><span class="menuButton"><g:link
     elementId="Back" controller="admin" class="list" action="systemAdmin">Back</g:link></span>
</div>
        </div>
    </body>
</html>
