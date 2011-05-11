
<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <meta name="layout" content="main" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
        <link rel="stylesheet" href="${resource(dir:'jquery.treeview',file:'jquery.treeview.css')}".css" type="text/css" />
  <script type="text/javascript" src="${resource(dir:'jquery.treeview',file:'jquery.treeview.js')}"></script>
    
        <jqui:resources />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'File')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
         <style>
         
         /* begin css tabs */

ul#tabnav { /* general settings */
text-align: left; /* set to left, right or center */
margin: 1em 0 1em 0; /* set margins as desired */
font: bold 11px verdana, arial, sans-serif; /* set font as desired */
border-bottom: 1px solid #6c6; /* set border COLOR as desired */
list-style-type: none;
padding: 3px 10px 3px 10px; /* THIRD number must change with respect to padding-top (X) below */
}

ul#tabnav li { /* do not change */
display: inline;
}

#tab1 li.tab1, #tab2 li.tab2, #tab3 li.tab3, #tab4 li.tab4 { /* settings for selected tab */
border-bottom: 1px solid #fff; /* set border color to page background color */
background-color: #fff; /* set background color to match above border color */
}

#tab1 li.tab1 a, #tab2 li.tab2 a, #tab3 li.tab3 a, #tab4 li.tab4 a { /* settings for selected tab link */
background-color: #fff; /* set selected tab background color as desired */
color: #000; /* set selected tab link color as desired */
position: relative;
top: 1px;
padding-top: 4px; /* must change with respect to padding (X) above and below */
}

ul#tabnav li a { /* settings for all tab links */
padding: 3px 4px; /* set padding (tab size) as desired; FIRST number must change with respect to padding-top (X) above */
border: 1px solid #6c6; /* set border COLOR as desired; usually matches border color specified in #tabnav */
background-color: #cfc; /* set unselected tab background color as desired */
color: #666; /* set unselected tab link color as desired */
margin-right: 0px; /* set additional spacing between tabs as desired */
text-decoration: none;
border-bottom: none;
}

ul#tabnav a:hover { /* settings for hover effect */
background: #fff; /* set desired hover color */
}

/* end css tabs */
         </style>
    <script>

  $(document).ready(function(){
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
		<li class="tab4"><a href="${createLink(mapping: 'componentDetails', controller:'component', action:'fileList', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Files</span></a></li>
	</ul>
        <br />
            <g:if test="${ componentInstanceTotal > 0}">
            <div class="projects">
                    <ul id="example" class="filetree">
  <g:each in="${componentInstanceList}" status="i" var="componentInstance">
  <li><span class="folder">${componentInstance.name}</span>
  <g:each in="${componentInstance.sessions}" status="k" var="sessionInstance">
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
                 <g:traversalTag file="${fileInstance}" sessionRoot="${sessionRoot}" session="${sessionInstance}" status="${i}-${1}"/>
                 </g:each>
                 </ul>
                 </li>
         </ul>
  </g:each>
  </li>
  </g:each>
		</ul>




            </div>
            </g:if>
            </div>
            </div>
    </body>
</html>
