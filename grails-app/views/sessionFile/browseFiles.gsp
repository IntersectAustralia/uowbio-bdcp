
<%@ page import="au.org.intersect.bdcp.SessionFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
        <script type="text/javascript">
        function callback(s)
        {
            if (s == "FINISHED")
            {
            	window.location.href = "${createLink(mapping:"sessionFileDetails", controller:"sessionFile", action:'fileList', params:['studyId':params.studyId, 'sessionId':params.sessionId])}"
            }
        }
        </script>
        <title>Browse Files</title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1>Browse For Files</h1>
            
            <h2 id="warning">Warning: files uploaded cannot be removed</h2>
            <br />
            <applet codebase="${request.contextPath}/applets"
                            ARCHIVE="acdata-uploader.jar, lib/commons-codec-1.4.jar, lib/commons-httpclient-3.1.jar, lib/commons-logging-1.1.1.jar, lib/log4j-1.2.15.jar, lib/plugin.jar"
                            code="au.org.intersect.acdata.uploader.main.UploadApplet"
                            width="40%" height="20%">
                            <param name="serverUrl" value="${request.siteUrl}/study/${params.studyId}/session/${params.sessionId}/sessionFile/upload" />
                            <param name="callback" value="true" />
                       Your browser does not have Java enabled.
                      </applet> 
         
    <div class="buttons">
    <span class="button"><g:link mapping="sessionFileDetails" controller="sessionFile" class="create" elementId="Cancel" action="fileList" params="['studyId': params.studyId,'sessionId': params.sessionId]">Cancel</g:link></span>
    </div>
    </div>
    </body>
</html>
