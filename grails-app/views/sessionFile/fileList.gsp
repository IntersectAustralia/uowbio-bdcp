
<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <meta name="layout" content="main" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
        <script src="${createLinkTo(dir: 'jsTree.v.1.0rc2', file: 'jquery.jstree.js')}" type="text/javascript"></script>
    
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
                    <div id="demo1" class="demo">
</div>
<script type="text/javascript" class="source">

$(function () {
	$("#demo1").jstree({ 
		"xml_data" : {
			"data" : "" + "${nodes}"
		},

		"ui" : {
			"select_limit" : 1,
			"select_multiple_modifier" : "alt",
			"selected_parent_close" : "select_parent",
		},

		'types' : {
		    'valid_children' : [ 'folder' ],
		    'types' : {
		        'folder' : {
		            'valid_children' : [ 'file'],
		            'max_depth' : 5
		        },

		        'file' : {
		            'valid_children' : [ 'none' ],
		            'icon' : { 'image' : "${resource(dir:'images',file:'file.png')}" },
		        }
		    }
		},
				
		"plugins" : [ "themes", "xml_data", "types", "ui", "contextmenu", "crrm"]
	});
	$("#demo1").jstree("open_all","#nodeID");
});

</script>
            </div>
            </g:if>
            </div>
            </div>
    </body>
</html>
