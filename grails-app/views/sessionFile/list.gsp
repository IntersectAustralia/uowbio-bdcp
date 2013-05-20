
<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'fineuploader-3.5.0.css')}">
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
			<tr><th>Right click on folder to upload files and directories. Drag and drop multiple files onto uploader button (Upload directories via drag and drop on Chrome 21+).</th></tr>
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
	        <div id="uploader"></div>
	        <g:javascript library="jquery.fineuploader-3.5.0" />
	    	<g:submitButton class="button" name="Download"></g:submitButton>
	    </g:form>
		</div>
        </div>
      </div>
            
	<script type="text/javascript">
	var treeAry =  new Array();
	var currentTree;
	var running = 0; 
	
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
     	   											folderPath = node[0].getAttribute('folderPath')
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
    
    var filePaths = new Array()
    var totalNumOfFiles = 0;
    var isAnyException = true
    function createDefaultMenu(obj)
    {
        var items = {
                uploadFilesItem: {
    				label: "Upload Files",
    				action: function (obj) {
    					changeAllToChecked()
    					$(obj).removeClass("jstree-unchecked")
    					$(obj).addClass("jstree-checked")
    					
						var sessionId = "", directory = "";
    					var relDirectoryStr = 'value is: ' + obj[0].getElementsByTagName('input').files.value
    					sessionId = relDirectoryStr.split('/', 3)[2]
    					currentTree = relDirectoryStr.split('/', 3)[1]
						var folderPath = $(obj).data('folderPath');
						var regexp = /[^\/]+\/[^\/]+\/[^\/]+\/?(.*)/;
						var match = regexp.exec(folderPath);
						var relDirectory = match[1];
	                   	directory = relDirectory;

	                  	var top_upload = $("#uploader").offset().top;
	                   	$('.qq-upload-button').show('slow');
	                   	//$(window).animate({top:"2000px"},1000)
	                   	$(window).scrollTop(top_upload, "slow")

						var fileCount = 0;
    					var uploader = new qq.FineUploader({
    						element: document.getElementById('uploader'),
    						callbacks: {
    							onComplete: function(id, fName) {
    								totalNumOfFiles--
        							if(totalNumOfFiles == 0){
            							uploaderComplete()
            						}
    							},
    							onSubmit :  function(id, fName) {
    								totalNumOfFiles++
    							},
		    					onError: function(id, fName, reason, maybeXhr) {
									$('.qq-upload-button').hide()
									$(obj).addClass("jstree-unchecked")
									$(obj).removeClass("jstree-checked")
		    					}
    						},
    						request: {
    				            endpoint: '${baseURL}study/${studyInstance.id}/sessionFile/uploadFiles',
    				            params: {
        				            	'directory':directory,
        				            	'sessionId':sessionId,
        				            	'filePath':function() {
        				                    return filePaths.length > 0 ? filePaths[fileCount++] : "";
        				                 }
        				           	}
    				        },
    				        template: '<div class="qq-uploader span12">' +
    				        '<pre class="qq-upload-drop-area span12"><span>{dragZoneText}</span></pre>' +
    				        '<div class="qq-upload-button btn btn-success">{uploadButtonText}</div>' +
    				        '<span class="qq-drop-processing"><span>{dropProcessingText}</span><span class="qq-drop-processing-spinner"></span></span>' +
    				        '<ul class="qq-upload-list" style="margin: 10px 0;"></ul>' +
    				        '</div>',
    				        classes: {
    				        	success: 'alert alert-success',
    				        	fail: 'alert alert-error'
    				        },
    				        validation: {
    				        	sizeLimit: 1024 * 1000 * 100, // 100mB = 100 * 1000 * 1024 bytes
    				        	itemLimit: 100
    				        }
    					});
    				},                                     
    				seperator_after: false,
    				seperator_before: false
                }
            };

            if ($(obj).attr('rel') != 'folder') {
                // Delete the "delete" menu item
                delete items.uploadFilesItem;
            }

            return items;

    }
	function changeAllToChecked(){
		for(var i=0; i < treeAry.length; i++){
			$(treeAry[i]).children("ul").children("li").each(function changeCheckState(){
				$(this).removeClass("jstree-undetermined")
	        	$(this).removeClass("jstree-checked")
				$(this).addClass("jstree-unchecked")
				$(this).children("ul").children("li").each(changeCheckState)
	        })
		}
		
		
	}
	function uploaderComplete(){
		$('.qq-upload-button').hide();
		$(".jstree-classic.jstree-focused").css("min-height",$(".jstree-classic.jstree-focused").css("height"))
		for(var i=0; i < treeAry.length; i++){
			var tree = jQuery.jstree._reference(treeAry[i]);
		    tree.refresh();
		}
		
	}
	</script>
    </body>
</html>
