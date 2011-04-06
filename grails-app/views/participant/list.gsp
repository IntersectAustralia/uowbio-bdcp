
<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
           <div id="participant">
            <br />
            <span class="menuButton"><g:link mapping="participantDetails" controller="participant" class="create" action="create" params="[studyId: studyInstance.id]">
        	Add Participant</g:link></span>
        	<br />
        	<br />
            <g:if test="${ participantsInStudy[0] > 0}">
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <th>${message(code: 'participant.identifier.label', default: 'Identifier')}</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${participantInstanceList}" status="i" var="participantInstance">
                         <g:if test="${participantInstance?.study.id.toString() == params.studyId}">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                              <div class="columnLeft">${fieldValue(bean: participantInstance, field: "identifier")}</div>
                              <div class="columnRight"><g:link elementId="forms" mapping="participantFormDetails" controller="participantForm" action="list" params="[studyId: params.studyId, participantId: participantInstance.id]">Forms</g:link></div>
                              <div class="columnRight"><g:link mapping="participantDetails" elementId="edit-participant[${i}]" controller="participant" action="edit" params="[studyId: params.studyId, id: participantInstance.id]">Edit</g:link></div>
                           	  
                           </td>
                        </tr>
                        </g:if>
                    </g:each>
                    </tbody>
                </table>
            </div>
            
            <div class="paginateButtons" >
                <g:paginate total="${participantsInStudy[0]}" mapping="participantDetails" params="[studyId: params.studyId]"/>
            </div>
            </g:if>
            </div>
    </body>
</html>
