<html>
<head>
<title>File Explorer</title>
<g:javascript library="jquery" plugin="jquery"/>
<link rel="stylesheet" href="${createLinkTo(dir: 'jqueryFileTree', file: 'jqueryFileTree.css')}">
<script src="${createLinkTo(dir: 'jqueryFileTree', file: 'jqueryFileTree.js')}" type="text/javascript"></script>
<script type="text/javascript">

function YourFunctionToProcessThisFilePath(file){
alert('You selected : '+file);
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
<div id="file_list"> 
<%="Empty"%>
</div>
</body>
</html>