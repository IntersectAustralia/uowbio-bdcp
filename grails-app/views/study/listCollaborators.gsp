
<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <meta name="layout" content="main" />
    <g:javascript library="application" />
    <g:javascript library="jquery" plugin="jquery"/>
 		<jqui:resources />
    <g:set var="entityName" value="${message(code: 'collaborator.label', default: 'Collaborator')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>

		<script type="text/javascript">
		        $(function() {
		            // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		            $( "#dialog:ui-dialog" ).dialog( "destroy" );
		     
		            $( "#dialog-confirm" ).dialog({
		                  autoOpen: false,
		                  resizable: false,
		                  height:140,
		                  modal: true,
		                  buttons: {
		                        "Yes": function() {
		                              $( this ).dialog( "close" );
		                              window.location.href = $(this).data("url");
		                              
		                        },
		                        "No": function() {
		                              $( this ).dialog( "close" );
		                        }
		                  }
		            });

		            $('.myDelete').click(function() {
			  			  $("#dialog-confirm").data("url", $(this).attr("href"));
		                  $('#dialog-confirm').dialog('open');
		                  // prevent the default action, e.g., following a link
		                  return false;
			        });
			        
		      });
            
		</script>
  
  </head>
  <body>
    
    <div id="dialog-confirm" title="Cancel the deletion of Collaborator?">
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure?</p>
		</div>
		
      <div class="body" id="tab6">
        <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <div id="study">
          <ul id="tabnav">
  	
        		<li class="tab1"><a href="${createLink(controller:'study', action:'show', params:['id': studyInstance.id]) }" id="${studyInstance.id}" name="details">Details</a></li>
        		<li class="tab2"><a href="${createLink(mapping:'participantDetails', controller:'participant', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-participants" name="#tabs-participants"><span>Participants</span></a></li>
        		<li class="tab3"><a href="${createLink(mapping: 'componentDetails', controller:'component', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-components" name="#tabs-components"><span>Components</span></a></li>
        		<li class="tab4"><a href="${createLink(mapping: 'sessionFileList', controller:'sessionFile', action:'fileList', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Files</span></a></li>
        		<li class="tab5"><a href="${createLink(mapping: 'studyDeviceDetails', controller:'studyDevice', action:'list', params:['studyId': studyInstance.id])}" id="tabs-files" name="tabs-files"><span>Devices</span></a></li>
        		<li class="tab6"><a href="${createLink(mapping: 'studyCollaborators', controller:'study', action:'listCollaborators', params:['studyId': studyInstance.id])}" id="tabs-collaborators" name="tabs-collaborators"><span>Collaborators</span></a></li>
          </ul>
      		<g:if test="${username}">
        		<p>Collaborator <u>${username}</u> added to study <u>${studyInstance.studyTitle}</u></p>
      		</g:if>	
          <g:render template="collaborators" />
        </div>
    	</div>
  </body>
</html>