<h2>${studyInstance} Participants</h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <br />
            <span class="menuButton"><g:link mapping="participantDetails" class="create" action="create" params="[studyId: studyInstance.id]">
        	Add Participant</g:link></span>
        	<br />
        	<br />
           <g:if test="${ participantsInStudy[0] > 0}">
           <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="identifier" title="${message(code: 'participant.identifier.label', default: 'Identifier')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${participantInstanceList}" status="i" var="participantInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                              <div class="columnLeft">${fieldValue(bean: participantInstance, field: "identifier")}</div>
                              <div class="columnRight"><g:link elementId="forms" mapping="participantFormDetails" controller="participantForm" action="list" params="[studyId: studyInstance.id, participantId: participantInstance.id]">Forms</g:link></div>
                              <div class="columnRight"><g:link mapping="participantDetails" elementId="edit" controller="participant" action="edit" params="[studyId: studyInstance.id, id: participantInstance.id]">Edit</g:link></div>
                           	  
                           </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons" >
                <g:paginate total="${participantsInStudy[0]}" mapping="participantDetails" params="[studyId: studyInstance.id]"/>
            </div>
           </g:if>
           