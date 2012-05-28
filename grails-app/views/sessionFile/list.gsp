
<%@ page import="au.org.intersect.bdcp.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <g:set var="imagesDir" value="${resource(dir:'images',file:'/')}" />
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

    <g:if test="${folders.size()!=0}">
        <g:form action="downloadFiles" controller="sessionFile" mapping="sessionFileList" params="[studyId: studyInstance.id]">
          <div class="list">
            <table>
          <tr><th>Right click on session or folder to upload files and directories</th></tr>
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
          <g:submitButton class="button" name="Download"></g:submitButton>
        </g:form>
        </g:if>
        <g:else>
          <div class="list">
            <table>
              <tr><th>There are no components</th></tr>
            </table>
          </div>
            
        </g:else>
        </div>
        
        
        </div>
      </div>
            
      <script type="text/javascript">
      // <![CDATA[
      // TODO: watch out for single and double quotes in folder name !!!
      var $node = null;
      <g:each in="${folders}" status="i" var="folder">
      $node = $('#SF_${folder.id}');
      $node.jstree({
          'core': { },
          'json_data' : {
              'ajax' : {
                  'url' : function(node) {
                      var topId = ${folder.id};
                      var folderPath = node == -1 ? '' : $(node).data('folderPath');
                      var sessionId = node == -1 ? '' : $(node).data('sessionId');
                      return "listFolder?id=" + topId + '&folderPath=' + folderPath +'&sessionId=' + sessionId;
                   },
                  'success': function(response) {
                      if (response != null && !(response.error)) { return response; }
                      alert('Error:' + response.error);
                      return null;
                  },
                  'error': function(request, textStatus, errorThrown) {
                      if ('success' == textStatus) return;
                      alert('Cannot access folder')
                  }
              }
          },
          'checkbox': { 
              'two_state': false,
              'real_checkboxes':true,
              'real_checkboxes_names': 
                        function(node) {
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
                      "icon" : {"image":"${imagesDir}/component.gif"},
                      "valid_children" : "all"
                  },
                  "file" : {
                      "icon" : {"image":"${imagesDir}/leaf.gif"},
                      "valid_children" : ["none"]
                  },
                  "session" : {
                      "icon" : {"image":"${imagesDir}/session.gif"},
                      "valid_children" : ["all"]
                  }
              }
          },
          "contextmenu" : {
               "items" : createDefaultMenu
          }, 
          'plugins' : [ "themes", "json_data", "types", "checkbox", "ui", "contextmenu" ]
        });

    </g:each>

    function createDefaultMenu(obj)
    {
        var items = {
           uploadFilesItem: {
               label: "Upload Files",
               action: function (obj) { 
                   var folderPath = $(obj).data('folderPath');
                   var regexp = /[^\/]+\/[^\/]+\/[^\/]+\/?(.*)/;
                   var match = regexp.exec(folderPath);
                   var relDirectory = match[1];
                   var sessionId = $(obj).data('sessionId');
                   alert('path=' + folderPath + ', sessionId=' + sessionId);
                   document.location="browseFiles?directory=" + relDirectory + '&sessionId=' + sessionId
               }                                     
           }
        };
        if ($(obj).attr('rel') == 'root' || $(obj).attr('rel') == 'file') {
           // Delete the "delete" menu item
           delete items.uploadFilesItem;
        }
        return items;
    }
    
   // ]]>
    </script>
    </body>
</html>
