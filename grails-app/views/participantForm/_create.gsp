<div class="create">
            <h1>Add Forms</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:each in="${participantForms}" status="i" var="participantFormsInstance">
            <g:hasErrors bean="${participantFormsInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantFormsInstance}" as="list" />
            </div>
            </g:hasErrors>
            </g:each>
            <g:uploadForm action="upload" mapping="participantFormDetails" params="[studyId: params.studyId, participantId: params.participantId]" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                         
                            <g:render template="form" model = "['i':0, 'body':body()]"/>
                            <g:render template="form" model = "['i':1, 'body':body()]"/>
                            <g:render template="form" model = "['i':2, 'body':body()]"/>
                            <g:render template="form" model = "['i':3, 'body':body()]"/>
                            <g:render template="form" model = "['i':4, 'body':body()]"/>
                            <g:render template="form" model = "['i':5, 'body':body()]"/>
                            <g:render template="form" model = "['i':6, 'body':body()]"/>
                            <g:render template="form" model = "['i':7, 'body':body()]"/>
                            <g:render template="form" model = "['i':8, 'body':body()]"/>
                            <g:render template="form" model = "['i':9, 'body':body()]"/>
                            
                        </tbody>
                    </table>
                </div>
                
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="Upload" /></span>
                    <span class="button"><g:link elementId="return" mapping="participantDetails" controller="participant" action="list" params="[studyId: params.studyId]">Return to Participants</g:link></span>
                </div>
            </g:uploadForm>
        </div>