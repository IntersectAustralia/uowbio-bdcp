
<%@ page import="au.org.intersect.bdcp.Component" %>
<html>

            <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
        <div id="component">
        
        <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab3']}" />
        
        <br />
        <span class="menuButton"><g:link elementId="createComponent" mapping="componentDetails" controller="component" class="create" action="create" params="[studyId: studyInstance.id]">Add Component</g:link></span>
            <br />
            <br />
            <g:if test="${ componentInstanceTotal > 0}">
            
            
            <div class="projects">
            	<g:each in="${componentInstanceList}" status="i" var="componentInstance">
                        
                	<span class="component_title">
                		${fieldValue(bean: componentInstance, field: "name")} 
                		- 
                		${fieldValue(bean: componentInstance, field: "description")}
                		
            		<g:link mapping="componentDetails" elementId="edit-component[${i}]" class="button" controller="component" action="edit" params="[studyId: params.studyId, id: componentInstance.id]"><span>Edit</span></g:link>
                		
              </span>
                     
              <ul>
              	<g:each in="${componentInstance.getSessionsList()}" status="n" var="sessionInstance">
                  <li>
                  	${fieldValue(bean: sessionInstance, field: "name")} 
                  	- 
                  	${fieldValue(bean: sessionInstance, field: "description")} 
                  	<g:link mapping="sessionDetails" elementId="edit-session[${n}]" class="button" controller="session" action="edit" params="[studyId: params.studyId, id: sessionInstance.id,componentId: componentInstance.id]"><span>Edit</span></g:link> 
                  </li>                        
              	</g:each>
              	
              	<li>
            			<g:link elementId="createSession[${i}]" mapping="sessionDetails" controller="session" class="create button" action="create" params="[studyId: studyInstance.id, componentId: componentInstance.id]"><span>Add Session</span></g:link>
              	</li>
              </ul>
            </div>
          </g:each>
        </div>
      </g:if>
      </div>
    </div>
          </div>
  </body>
</html>
