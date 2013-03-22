
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <g:set var="baseDir" value="${resource(dir:'/',file:'/')}" />
        <g:set var="baseURL" value="${resource(dir:'/',file:'')}" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
        <jsTree:resources />
        <jqui:resources />
        <uploader:head />
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
              <tr><th>Right click on folder to upload files, and create and delete folders</th></tr>
             
              <tbody>
                  <tr class="odd">
                    <td>
                      <div id="SA_Folder"></div>
                    </td>
                  </tr>
              </tbody>
             
            </table>
          </div>
          	<div id="upload">
        		<uploader:uploader id="upload" multiple="true" url="${baseURL}studyAnalysedData/${studyInstance.id}/uploadFiles" params="${['folderPath':folderPath] }" >
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
        <script type="text/javascript">

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
		        
        var running = 0; 
        $('#upload').hide();
        var createMenu = function(obj)
        {
            var items = {
				createFolderItem: {
				    label: "Create folder",
				    action: function (obj) { 
				        var folderPath = $(obj).data('folderPath');
				        document.location="createFolder?folderPath=" + folderPath.substring(2);
				    }                                     
				},
				uploadFilesItem: {
               		label: "Upload Files",
              		
					action: function (obj) { 
						$('#upload').show('slow');
						var folderPath = $(obj).data('folderPath');
						au_upload._options.params.folderPath = folderPath.substring(2);
                   	}  
				}, 
				deleteItem: {
				    label: "Delete",
				    action: function (obj) { 
				        var folderPath = $(obj).data('folderPath');//alert($(obj).data('folderPath'));
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

		function uploaderComplete(){

			var tree = jQuery.jstree._reference("#SA_Folder");
		    tree.refresh();
		}
        </script>
    </body>
</html>
