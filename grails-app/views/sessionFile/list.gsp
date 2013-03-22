
<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <g:set var="imagesDir" value="${resource(dir:'images',file:'/')}" />
        <g:set var="baseURL" value="${resource(dir:'/',file:'')}" />
        <meta name="layout" content="main" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
        <link rel="stylesheet" href="${resource(dir:'jquery.treeview',file:'jquery.treeview.css')}".css" type="text/css" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'File')}" />
        <g:set var="jstreeTheme" value="${resource(dir:'plugins/js-tree-0.2/js/jstree_pre1.0_stable/themes/classic',file:'style.css')}" />
        <jsTree:resources />
   		<jqui:resources />
   		<uploader:head />

        <g:set var="downloadError" value="${message(code: 'sessionFile.downloadError.msg', default: 'Error downloading files')}" />
        <g:set var="downloadUrl" value="${createLink(mapping:'sessionFileList', controller:'sessionFile', action:'downloadFiles', params:['studyId': studyInstance.id])}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
  <body>
    <div class="body" id="tab4">
      <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
        
        <div id="component">
        
        <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab4']}" />
        <div id="tabs-details">

	
	    <g:form action="downloadFiles" controller="sessionFile" mapping="sessionFileList" params="[studyId: studyInstance.id]">
			<div class="list">
			  <table>
			<tr><th>Right click on folder to upload files and directories</th></tr>
			   <tbody>
			     <g:each in="${folders}" status="i" var="folder">
			      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			        <td>
			          <div id="SF_${folder.id}"></div> 
			        </td>
			      </tr>
			    </g:each>
			   </tbody>
			   
			  </table>
			</div>
	        <div id="upload">
        		<uploader:uploader id="upload" multiple="true" url="${baseURL}study/${studyInstance.id}/sessionFile/uploadFiles" params="${['directory':directory, 'sessionId':sessionId, 'fileName':fileName] }" >
	        		<uploader:onSubmit> 
	        			au_upload._options.params.fileName = fileName;
	        			running++;
	        		</uploader:onSubmit>
	        		
	        		<uploader:onComplete>
	        			running--;
				        if(running==0){
				          uploaderComplete();                        
				        }
					</uploader:onComplete>
				</uploader:uploader>
    		</div>
	    	<g:submitButton class="button" name="Download"></g:submitButton>
	    </g:form>
		</div>
        
        
        </div>
      </div>
            
	<script type="text/javascript">
	var treeAry =  new Array();
	var currentTree;
	var running = 0; 

	$(function() {
        var file_input = $('.qq-upload-button').children();
        $('.qq-upload-button').text($('.qq-upload-button').text().replace("Upload a file","Upload a single file or drag multiple files onto this button"))
        $('.qq-upload-button').append(file_input);
        $('.qq-upload-button').css("border", "1px solid #CCCCCC");
        //$('.qq-upload-button').css("color", "#333333");
        $('.qq-upload-button').css("display", "inline-block");
        $('.qq-upload-button').css("height", "auto");
        $('.qq-upload-button').css("margin", "0");
        $('.qq-upload-button').css("overflow", "hidden");
        $('.qq-upload-button').css("padding", "6px 9px");
        $('.qq-upload-button').css("width", "auto");
        //$('.qq-upload-button').css("background", "-moz-linear-gradient(center top , #FFFFFF 0%, #F6F6F6 47%, #EDEDED 100%) repeat scroll 0 0 transparent");
		});
	
	function uploaderComplete(){
		for(var i=0; i < treeAry.length; i++){
			var tree = jQuery.jstree._reference(treeAry[i]);
		    tree.refresh();
		}
	}
	
	//$('.qq-upload-button').text($('.qq-upload-button').text().replace("Upload a file","Upload a single file or drag multiple files onto this button"))
	$('#upload').hide();
	// TODO: watch out for single and double quotes in folder name !!!
	var $node = null;
    <g:each in="${folders}" status="i" var="folder">
    	
    	treeAry[${i}] = "#SF_"+${folder.id};
	    $node = $('#SF_${folder.id}');
       	$node.jstree({
           'core': {
               },
           'json_data' : {
               'ajax' : {
                   'url' : function(node) {
                	   var topId = ${folder.id};
                       var folderPath = node == -1 ? '' : $(node).data('folderPath');
                       return "listFolder?id=" + topId + '&folderPath=' + folderPath;
                    },
                   'success': function(response) {
                       if (response != null && response.error == null) { return response; }
                       alert('Error:' + response.error);
                       return null;
                   },
                   'error': function(request, textStatus, errorThrown) {
                       //alert('Cannot access folder or folder empty.')
                   }
               }
           },
           'checkbox': { 
               'two_state': false,
               'real_checkboxes':true,
               'real_checkboxes_names': function(node) {
            	   											var folderPath = $(node).data('folderPath');
            	   											if(folderPath == null)
                	   										{
            	   												folderPath = node[0].gZZzZZZZZetAttribute('folderPath')
                	   										}

                   											return['files', folderPath ];
               										   }
           },
           "themes": {
			   "url" : '${jstreeTheme}',
               "theme": "classic"
           },
           "types": {
               "valid_children" : "all",
               "type_attr" : "rel",
               "types" : {
                   "root" : {
                       "valid_children" : "all"
                   },
                   "file" : {
                       "icon" : {"image":"${imagesDir}/leaf.gif"},
                       "valid_children" : ["none"]
                   }
               }
           },
   		   "contextmenu" : {
			    "items" : createDefaultMenu
			}, 
           'plugins' : [ "themes", "json_data", "types", "checkbox", "ui", "contextmenu" ]
           });
			$node.bind("loaded.jstree", function (e, data) {

				//$node.jstree('open_all');
				for(var i=0; i < treeAry.length; i++){
					$(treeAry[i]).jstree('open_all');
				}
				
			});
			$node.bind("refresh.jstree", function (e, data) {

				//$node.jstree('open_all');
				for(var i=0; i < treeAry.length; i++){
					$(treeAry[i]).jstree('open_all');
				}
				
			});
    </g:each>

    function createDefaultMenu(obj)
    {
        var items = {
                uploadFilesItem: {
    				label: "Upload Files",
    				action: function (obj) {
        				
    					var relDirectoryStr = 'value is: ' + obj[0].getElementsByTagName('input').files.value
    					au_upload._options.params.sessionId = relDirectoryStr.split('/', 3)[2]
    					
    					currentTree = relDirectoryStr.split('/', 3)[1]
    					

    					
    					$('#upload').show('slow');
    					var folderPath = $(obj).data('folderPath');
	                    var regexp = /[^\/]+\/[^\/]+\/[^\/]+\/?(.*)/;
	                    var match = regexp.exec(folderPath);
	                    var relDirectory = match[1];
	                    
	                    au_upload._options.params.directory = relDirectory;
    					
    				},                                     
    				seperator_after: false,
    				seperator_before: false
                },
                deleteItem: { // The "delete" menu item
                	label: "Delete",
				    action: function (obj) { 
				        var folderPath = $(obj).data('folderPath');
				        if (confirm('Are you sure you want to delete ?')) {
				            document.location="doDeleteFolder?folderPath=" + folderPath.substring(2);
				        }
				    } 
                }
            };

            if ($(obj).attr('rel') != 'folder') {
                // Delete the "delete" menu item
                delete items.uploadFilesItem;
            }

            return items;

    }

    
    
	</script>
            
    </body>
</html>
