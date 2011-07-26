
<%@ page import="au.org.intersect.bdcp.Project" %>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title>Welcome Researcher</title>
    </head>
    
    <body>
    
        <div class="body">
            <h1>Welcome Researcher</h1>
            
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            
            <g:if test="${ projectInstanceList?.size() > 0}">
	        
	        
	            <div class="projects">
	            	<g:each in="${projectInstanceList}" status="i" var="projectInstance">
	            	
	            		<g:link action="show" id="${projectInstance.id}" class="project_title"> ${fieldValue(bean: projectInstance, field: "projectTitle")}</g:link>
	            		
	            		<ul>
	            		    <g:each in="${projectInstance.studies}" status="n" var="studyInstance">
                        		<li><g:link mapping="studyDetails" controller="study" action="show" id="${studyInstance.id}" class="project_study" params="[projectId: projectInstance.id]">${fieldValue(bean: studyInstance, field: "studyTitle")}</g:link></li>
	            		    </g:each>
                    		<li><g:link id="addStudy" mapping="studyDetails" class="create" controller="study" action="create" params="[projectId: projectInstance.id]">+ Add Study</g:link></li>
	            		</ul>
	            		
	            		<br />
	            		
	            	</g:each>
	            </div>
            </g:if>
            <g:link class="create" controller="project" action="create">+ Add Project</g:link>
        </div>
    </body>
</html>
