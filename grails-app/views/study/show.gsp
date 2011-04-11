
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <title><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></title>
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
           <div class="body" id="tab1"> 
            <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
    
	<ul id="tabnav">
	
		<li class="tab1"><a href="#tabs-details">Details</a></li>
		<li class="tab2"><a href="${createLink(mapping:'participantDetails', controller:'participant', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-participants" name="#tabs-participants"><span>Participants</span></a></li>
		<li class="tab3"><a href="${createLink(mapping: 'componentDetails', controller:'component', action:'list', params:['studyId': studyInstance.id]) }" id="tabs-components" name="#tabs-components"><span>Components</span></a></li>
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
                            <td valign="top" class="name"><g:message code="study.uowEthicsNumber.label" default="UOW Ethics Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "uowEthicsNumber")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.hasAdditionalEthicsRequirements.label" default="Additional Ethics Requirements" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "hasAdditionalEthicsRequirements")}</td>
                            
                        </tr>
                        <g:if test="${studyInstance?.hasAdditionalEthicsRequirements?.equals('Yes')}">
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="study.additionalEthicsRequirements.label" default="Additional Ethics Details" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyInstance, field: "additionalEthicsRequirements")}</td>
                            
                        </tr>
                        </g:if>
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
    </body>
</html>
