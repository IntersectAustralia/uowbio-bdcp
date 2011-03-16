<h1>${studyInstance} Participants</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
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
                         <g:if test="${participantInstance?.study.id.toString() == params.studyId}">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                              <div class="columnLeft">${fieldValue(bean: participantInstance, field: "identifier")}</div>
                              <div class="columnRight"><g:link elementId="forms" mapping="participantFormDetails" controller="participantForm" action="list" params="[studyId: params.studyId, participantId: participantInstance.id]">Forms</g:link></div>
                              <div class="columnRight"><g:link mapping="participantDetails" elementId="edit" controller="participant" action="edit" params="[studyId: params.studyId, id: participantInstance.id]">Edit</g:link></div>
                           	  
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