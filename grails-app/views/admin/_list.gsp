            
           <g:if test="${ matches.size() > 0}">
           <div class="list">
                <table id="searchTable">
                    <thead>
                        <tr>
                        
                            <th>User ID</th>
                        	<th>Given Name</th>
                        	<th>Surname</th>
                        	<th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${matches}" status="i" var="matchInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${matchInstance.username}</td>
                             <td> ${matchInstance.givenName }</td>
                             <td> ${matchInstance.sn }</td>
                             <td><g:link elementId="select[${i}]" controller="admin" action="addRole" params="[userid: matchInstance.username, firstName: matchInstance.givenName, surname: matchInstance.sn]">Select</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons" >
                <g:paginate total="${matches}"/>
            </div>
           </g:if>
           
