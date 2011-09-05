
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
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
	<g:link mapping="studyAnalysedData" controller="studyAnalysedData" class="create button" action="create" params="[studyId: studyInstance.id]">+ Add Folder</g:link>
	<g:if test="${ dirFiles.size() > 0}">
	  <div class="list">
	    <table>
	      
	      <tbody>
	        <g:each in="${dirFiles}" status="i" var="file">
	          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	            
	            <td class="tablename">
	              <div class="columnLeft">${file.getName()}</div>
	            </td>
	          
	            <td class="tablebuttons">
	              <g:link mapping="studyAnalysedData" elementId="edit-participant[${i}]" class="button right list" action="upload" params="[studyId: studyInstance.id, folder:file.getName()]">Upload</g:link>
	             </td>
	             
	          </tr>
	        </g:each>
	      </tbody>
	      
	    </table>
	  </div>
	</g:if>
	</div>
	</div>
           
    </body>
</html>
