
<%@ page import="au.org.intersect.bdcp.SessionFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
        <title>File Explorer</title>

	</head>
        <title>File Explorer</title>
    </head>
    <body>
        <div class="body">
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:textField name="directory" value="directory" readonly="readonly" />
	
            <g:if test="${fileResourceInstanceList?.size() > 0}">
            <div class="list">
             <table>
                    <thead>
                        <tr>
                            <th>Files</th>
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${fileResourceInstanceList}" status="i" var="fileInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            
                            <td><div class="columnLeft"><g:if test="${fileResourceInstanceList}" >${fileInstance}</g:if></div></td>
                        </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            </g:if>
            
            
        <div class="rowBottom">
    <div class="buttons">
    <span class="button"><g:link mapping="sessionFileDetails" controller="sessionFile" class="create" elementId="Cancel" action="uploadFiles" params="['studyId': params.studyId,'sessionId': params.sessionId]">Cancel</g:link></span>
    </div>
    </div>
    </div>
    </body>
</html>
