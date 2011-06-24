
<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <meta name="layout" content="main" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
        <link rel="stylesheet" href="${resource(dir:'jquery.treeview',file:'jquery.treeview.css')}".css" type="text/css" />
        <style type="text/css">
        <!--
        input.fileSelect, input.directorySelect {
	    width : 1em;
	    height :1em;
	    padding: 0px;
	    margin: 0px;
	}
        -->
        </style>

  <script type="text/javascript" src="${resource(dir:'jquery.treeview',file:'jquery.treeview.js')}"></script>
    
        <jqui:resources />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'File')}" />
        <g:set var="downloadError" value="${message(code: 'sessionFile.downloadError.msg', default: 'Error downloading files')}" />
        <g:set var="downloadUrl" value="${createLink(mapping:'sessionFileList', controller:'sessionFile', action:'downloadFiles', params:['studyId': studyInstance.id])}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    <script>

  $(document).ready(function(){
	 
    var $downloadButton = $("#downloadButton");
    var $fileTree = $(".filetree");
    var numf = 0;
    $downloadButton.button({disabled:true}); 
    $downloadButton.click(function(){
        var url = '${downloadUrl}';
        var $selected = $fileTree.find("input:checkbox:checked");
        var $form = $('<form action="'+url+'" method="POST"></form>');
        $form.appendTo('body');
        $selected.clone().appendTo($form);
        $form.submit().remove();
    });
    
    function refreshButton() {
        numf = $fileTree.find("input:checkbox:checked").size();
        $downloadButton.button("option","disabled",numf == 0);
    }
        
	function updateDirectory(reference) {
		  var $input = $("input[reference='"+reference+"']");
      	  var parentRef = $input.attr("parentDir");
		  var $e = $input.closest("li");
		  var $children = $e.find("input[parentDir='"+reference+"']");
		  var $selectedChildren = $e.find("input[parentDir='"+reference+"']:checked");
		  var checked = $input.prop("checked");
		  var newChecked = $children.size() == $selectedChildren.size();
		  if (checked != newChecked) {
			  $input.prop("checked", newChecked);
			  if (parentRef != null && parentRef.length>0) {
				  updateDirectory(parentRef);
			  }
		  }
	}
    $("input.fileSelect").change(function(){
        	var parentRef = $(this).attr("parentDir");
	        updateDirectory(parentRef);
	        refreshButton();
        });
    $("input.directorySelect").change(function(){
    		var reference = $(this).prop("reference");
    		var isChecked = this.checked
        	$(this).closest("li").find("input").each(function(i,elem){
            	var $e = $(elem);
            	$e.prop("checked", isChecked)
            	});
        	var parentRef = $(this).attr("parentDir");
	        updateDirectory(parentRef);
	        refreshButton();
        });
    $("#example").treeview();
  });
  
  
  </script>
    
    </head>
    <body>
       <div class="body" id="tab4">
            <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
        
        <div id="component">
        <ul id="tabnav">
	
		<li class="tab1"><a href="${createLink(controller:'study', action:'show', params:['id': studyInstance.id]) }" id="${studyInstance.id}" name="details">Details</a></li>
		<li class="tab2"><a href="${createLink(mapping:'participantDetails', controller:'participant', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-participants" name="#tabs-participants"><span>Participants</span></a></li>
		<li class="tab3"><a href="${createLink(mapping: 'componentDetails', controller:'component', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-components" name="#tabs-components"><span>Components</span></a></li>
		<li class="tab4"><a href="${createLink(mapping: 'sessionFileList', controller:'sessionFile', action:'fileList', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Files</span></a></li>
	    <li class="tab5"><a href="${createLink(mapping: 'studyDeviceDetails', controller:'studyDevice', action:'list', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Devices</span></a></li>
    </ul>
        <br />
            <g:if test="${ componentInstanceTotal > 0}">
            <div class="projects">
                    <ul id="example" class="filetree">
  <p>To download file/s, please check box/es next to files or directories and click "Download”</p>
  <g:each in="${componentInstanceList}" status="i" var="componentInstance">
  <li><span class="folder">${componentInstance.name}</span>
  
  <g:each in="${componentInstance.getSessionsList()}" status="k" var="sessionInstance">
          <ul>
                 
                 <li class="closed"><span class="folder">${sessionInstance.name} 
                 <g:link elementId="upload[${i}-${0}]" mapping="sessionFileDetails" controller="sessionFile" action="browseFiles" 
                 params="['studyId': params.studyId,'sessionId': sessionInstance.id, 'directory':'']" ><img src="${resource(dir:'images/icons',file:'upload.png')}"  alt="Upload Files" title="Upload Files"
                 /></g:link>
                 <g:link elementId="createDirectory[${i}-${0}]" mapping="sessionFileDetails" controller="sessionFile" action="createDirectory" 
                 params="['studyId': params.studyId,'sessionId': sessionInstance.id, 'directory':'']"><img src="${resource(dir:'images/icons',file:'plus.gif')}"  alt="Add Directory" title="Add Directory"
                 /></g:link></span>
                 <ul>
                 <g:def var="sessionRoot" value="${sessionFiles.getAt(sessionInstance.id.toString()).getAt('sessionRoot')}" />
                 <g:each in="${sessionFiles.getAt(sessionInstance.id.toString()).getAt('files')}" status="l" var="fileInstance">
                 <g:traversalTag file="${fileInstance}" sessionRoot="${sessionRoot}" session="${sessionInstance}" status="${i}-${1}" parent="${sessionInstance.id}" position="${l}"/>
                 </g:each>
                 </ul>
                 </li>
         </ul>
  </g:each>
  </li>
  </g:each>
		</ul>


			<button id="downloadButton">Download</button>

            </div>
            </g:if>
            </div>
            </div>
    </body>
</html>
