<div id="participantForm${i}" class="participantForm-div" <g:if test="${hidden}">style="display:none;"</g:if>>
<g:if test="${participantFormsList?.size() < 1}"> 
<table>
<thead>
<tr>

	<g:sortableColumn property="name" title="${message(code: 'form.name.label', default: 'Form Name')}" />
                        
   <g:sortableColumn property="link" title="${message(code: 'form.link.label', default: 'Form')}" />
   
</tr>
</thead>
</g:if>
</tbody>
<g:hiddenField name='participantFormsList[${i}].id' value='${participantForm?.id}'/>
<g:hiddenField name='participantFormsList[${i}].deleted' value='false'/>
<g:hiddenField name='participantFormsList[${i}].new' value="${participantForm?.id == null?'true':'false'}"/>
<td><g:textField name='participantFormsList[${i}].participantForm' value='${participantForm?.name}' /></td>   
<td><g:link action="show" id="${formInstance.id}">Attach Form</g:link></td>
</tbody>
</table>
<br />
<span class="del-participantForm">
<img src="${resource(dir:'images/skin', file:'icon_delete.png')}"
	style="vertical-align:middle;"/>
</span>
<span class="add-participantForm">
<img src="${resource(dir:'images/skin', file:'database_add.png')}" style="vertical-align:middle;" onclick="addParticipantForm();" />
</span>
</div>