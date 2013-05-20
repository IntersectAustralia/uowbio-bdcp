
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'fineuploader-3.5.0.css')}">
        <link rel="stylesheet" href="${resource(dir:'jquery.treeview',file:'jquery.treeview.css')}".css" type="text/css" />
        <g:set var="baseDir" value="${resource(dir:'/',file:'/')}" />
        <g:set var="baseURL" value="${resource(dir:'/',file:'')}" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
        <jsTree:resources />
        <jqui:resources />
        <g:set var="downloadError" value="${message(code: 'sessionFile.downloadError.msg', default: 'Error downloading files')}" />
        <g:set var="downloadUrl" value="${createLink(mapping:'sessionFileList', controller:'sessionFile', action:'downloadFiles', params:['studyId': studyInstance.id])}" />
        <title><g:message code="default.list.label" args="[entityName]" /> - Analysed data</title>
    </head>
    <body>
    <div class="body" id="tab7">
        <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
        <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
        </g:if>
        <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab7']}" />
        <div id="tabs-details">
        <g:form action="downloadFiles" controller="studyAnalysedData" mapping="studyAnalysedData" params="[studyId: studyInstance.id]">
          <div class="list">
            <table>
              <tr><th>Right click on folder to upload files, and create and delete folders. Drag and drop multiple files onto uploader button (Upload directories via drag and drop on Chrome).</th></tr>
             
              <tbody>
                  <tr class="odd">
                    <td>
                      <div id="SA_Folder"></div>
                    </td>
                  </tr>
              </tbody>
             
            </table>
          </div>
          <div id="uploader"></div>
    	  <g:javascript library="jquery.fineuploader-3.5.0" />
          <g:submitButton class="button" name="Download"></g:submitButton>
        </g:form>
        </div>
	</div>
        <script type="text/javascript">
        var running = 0; 
        var filePaths = new Array()
        var totalNumOfFiles = 0;
        var createMenu = function(obj)
        {
            var items = {
				createFolderItem: {
				    label: "Create folder",
				    action: function (obj) { 
				    	changeAllToChecked()
    					$(obj).removeClass("jstree-unchecked")
    					$(obj).addClass("jstree-checked")
				        var folderPath = $(obj).data('folderPath');
				        document.location="createFolder?folderPath=" + folderPath.substring(2);
				    }                                     
				},
				uploadFilesItem: {
               		label: "Upload Files",
					action: function (obj) { 
						changeAllToChecked()
    					$(obj).removeClass("jstree-unchecked")
    					$(obj).addClass("jstree-checked")
    					
						var folderPath = $(obj).data('folderPath');
						folderPath = folderPath.substring(2)
						
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
    				            endpoint: '${baseURL}studyAnalysedData/${studyInstance.id}/uploadFiles',
    				            params: {
        				            	'folderPath':folderPath,
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
	            	}  
				}, 
				deleteItem: {
				    label: "Delete",
				    action: function (obj) { 
				    	changeAllToChecked()
				    	$(obj).removeClass("jstree-unchecked")
    					$(obj).addClass("jstree-checked")
				    	$(obj).children("ul").children("li").each(function changeCheckState(){
		                	$(this).removeClass("jstree-unchecked")
		        			$(this).addClass("jstree-checked")
		        			$(this).children("ul").children("li").each(changeCheckState)
		                })
					    $("#vakata-contextmenu").hide()
				        var folderPath = $(obj).data('folderPath');
				        if (confirm('Are you sure you want to delete ' + folderPath.substring(2) + '?')) {
				            document.location="doDeleteFolder?folderPath=" + folderPath.substring(2);
				        }
				    }                                     
				}
            };
            if ($(obj).attr('rel') == 'analysed') {
               delete items.deleteItem;
            }
            if ($(obj).attr('rel') == 'file') {
               // Delete the "delete" menu item
               delete items.uploadFilesItem;
               delete items.createFolderItem;
            }
            return items;
        };
        createFileTree('${baseDir}', '#SA_Folder', null, createMenu);
        function changeAllToChecked(){
    		$("#SA_Folder").children("ul").children("li").each(function changeCheckState(){
    			$(this).removeClass("jstree-undetermined")
            	$(this).removeClass("jstree-checked")
    			$(this).addClass("jstree-unchecked")
    			$(this).children("ul").children("li").each(changeCheckState)
            })
    		
    	}
		function uploaderComplete(){
			$('.qq-upload-button').hide();
			var tree = jQuery.jstree._reference("#SA_Folder");
		    tree.refresh();
		}
        </script>
    </body>
</html>
