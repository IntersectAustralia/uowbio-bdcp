
<%@ page import="au.org.intersect.bdcp.SessionFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
        <title>Browse Files</title>
    </head>
    <body>
        <div class="body">
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1>Browse For Files</h1>
            
            <applet codebase="${request.contextPath}/applets"
                            ARCHIVE="acdata-uploader.jar, lib/commons-codec-1.4.jar, lib/commons-httpclient-3.1.jar, lib/commons-logging-1.1.1.jar, lib/log4j-1.2.15.jar, lib/plugin.jar"
                            code="au.org.intersect.acdata.uploader.main.UploadApplet"
                            width="50%" height="50%">

                       Your browser does not have Java enabled.
                      </applet> 
            
        <div class="rowBottom">
    <div class="buttons">
    <span class="button"><g:link mapping="sessionFileDetails" controller="sessionFile" class="create" elementId="Cancel" action="uploadFiles" params="['studyId': params.studyId,'sessionId': params.sessionId]">Cancel</g:link></span>
    </div>
    </div>
    </div>
    </body>
</html>
