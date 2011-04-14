<%@ page import="au.org.intersect.bdcp.SessionFile" %>
<html>
<head>
<title>File Explorer</title>
<meta name="layout" content="main" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<g:javascript library="jquery" plugin="jquery"/>
<link rel="stylesheet" href="${createLinkTo(dir: 'jqueryFileTree', file: 'jqueryFileTree.css')}">
<script src="${createLinkTo(dir: 'jqueryFileTree', file: 'jqueryFileTree.js')}" type="text/javascript"></script>
<script type="text/javascript">

function YourFunctionToProcessThisFilePath(file){
//alert('You selected : '+file);
}

$(document).ready(function() {
	$(document).ready(function() {
		$('#file_list').fileTree({
		root:'${path}',
		script:'generateFileList',
		expandSpeed: 1000,
		collapseSpeed: 1000,
		multiFolder: false,
		expandedFolders: ${expandedDirs}
		}, function(file) {
		YourFunctionToProcessThisFilePath(file); 
		});
		});
	});
</script>
</head>
<h1>File Explorer</h1>
<body>
<div class="body">
<g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
 <h2>Selected Files</h2>           
<div id="file_list"> 
<%="Empty"%>
</div>
<div class="rowBottom">
    <div class="buttons">
    <span class="button"><g:link mapping="sessionFileDetails" controller="uploadTempFiles" class="create" elementId="Cancel" action="uploadFiles" params="['studyId': params.studyId,'sessionId': params.sessionId]">Upload</g:link></span>
    <span class="button"><g:link mapping="sessionFileDetails" controller="sessionFile" class="create" elementId="Cancel" action="uploadFiles" params="['studyId': params.studyId,'sessionId': params.sessionId]">Cancel</g:link></span>
    
    </div>
    </div>
    </div>
</body>
</html>