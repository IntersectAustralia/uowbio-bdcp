
<%@ page import="au.org.intersect.bdcp.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <g:set var="baseDir" value="${resource(dir:'/',file:'/')}" />
    <meta name="layout" content="main" />
    <g:javascript library="application" />
    <g:javascript library="jquery" plugin="jquery"/>
    <link rel="stylesheet" href="${resource(dir:'jquery.treeview',file:'jquery.treeview.css')}".css" type="text/css" />
    <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'File')}" />
    <jsTree:resources />
    <jqui:resources />
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
      <g:each in="${folders}" status="i" var="folder">
      var createMenu = function(obj)
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
                     document.location="browseFiles?directory=" + relDirectory + '&sessionId=' + sessionId
                 }                                     
             }
          };
          if ($(obj).attr('rel') == 'root' || $(obj).attr('rel') == 'file') {
             // Delete the "delete" menu item
             delete items.uploadFilesItem;
          }
          return items;
      };
      createFileTree('${baseDir}', '#SF_${folder.id}', ${folder.id}, createMenu);
      </g:each>
      </script>
    </body>
</html>
