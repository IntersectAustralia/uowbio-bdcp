
<%@ page import="au.org.intersect.bdcp.SessionFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
        <title>Upload Files</title>
    </head>
    <body>
        <div class="body">
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1>Upload Files</h1>
            <div class="rowTop"><g:link elementId="create" mapping="sessionFileDetails" controller="sessionFile" action="createDirectory" params="['studyId': params.studyId]">Create Directory</g:link>
    <g:link elementId="create" mapping="sessionFileDetails" controller="sessionFile" action="browseFiles" params="['studyId': params.studyId]">Browse for Files</g:link></div>
        
        <div class="rowBottom">
    <div class="buttons">
    <span class="button"><g:link mapping="sessionFileDetails" controller="sessionFile" class="create" elementId="Cancel" action="fileList" params="['studyId': params.studyId]">Cancel</g:link></span>
    </div>
    </div>
    </div>
    </body>
</html>
