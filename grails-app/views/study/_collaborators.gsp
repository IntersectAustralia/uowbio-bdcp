            
            <br />
            <span class="menuButton"><g:link elementId="Add Collaborator" controller="study" class="create" action="searchCollaborators" params="[studyId: studyInstance.id]">
        	Add Collaborator</g:link></span>
        	<br />
        	<br />
           <g:if test="${ collaboratorInstanceTotal > 0}">
           <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>${message(code: 'collaborator.identifier.label', default: 'Collaborator ID')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${collaboratorInstanceList}" status="i" var="collaboratorInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                              <div class="columnLeft">${fieldValue(bean: collaboratorInstance, field: "username")}</div>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
           </g:if>
           