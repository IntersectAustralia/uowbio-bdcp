
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <title><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></title>
        
        <script type="text/javascript">

         //$(document).ready(function () {
         //   $("#tabs").bind('tabsselect', function(event, ui) {
         //       window.location.href=ui.tab;
         //   });
         //   $('#tabs').tabs();
            
       //});
        
       $(document).ready(function(){
           var param = "${params.tab}"
              $('#tabs').tabs("select", param) 
    $('#tabs').tabs();
    });
       
				

        
       </script>
    </head>
    <body>
           <div class="body"> 
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link controller="project" class="list" action="list">Project List</g:link></span>
            <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
    
	<div id="tabs"> 
	<ul>
	
		<li><a href="#tabs-details">Details</a></li>
		<li><a href="${createLink(mapping:'participantDetails', controller:'participant', action:'list', params:['studyId': studyInstance.id]) }" name="#tabs-participants"><span>Participants</span></a></li>
		<li><a href="${createLink(mapping: 'componentDetails', controller:'component', action:'list', params:['studyId': studyInstance.id]) }" name="#tabs-components"><span>Components</span></a></li>
	</ul>

	<div id="tabs-details">
	   <div class="dialog">
                <table id="studyTable">
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.studyTitle.label" default="Study Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "studyTitle")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.ethicsNumber.label" default="Ethics Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "ethicsNumber")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.industryPartners.label" default="Industry Partners" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "industryPartners")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.collaborators.label" default="Collaborators" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "collaborators")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.startDate.label" default="Start Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="MM/yyyy" date="${studyInstance?.startDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.endDate.label" default="End Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="MM/yyyy" date="${studyInstance?.endDate}" /></td>
                            
                        </tr>
                        
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.numberOfParticipants.label" default="Number of Participants" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "numberOfParticipants")}</td>
                            
                        </tr>
                    
                    	<tr class="prop">
                            <td valign="top" class="name"><g:message code="study.inclusionExclusionCriteria.label" default="Inclusion Exclusion Criteria" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "inclusionExclusionCriteria")}</td>
                            
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${studyInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" id="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                </g:form>
            </div>
	</div>
	</div> 
        </div>
    </body>
</html>
