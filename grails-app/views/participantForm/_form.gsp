<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="participantForm.name.label" default="Form Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantForms[i], field: 'formName', 'errors')}">
                                    <input type="text" id="forms[${i}].formName" name="forms[${i}].formName"/>
                                </td>
                            </tr>
                          
  <!-- SNIP -->
  <tr class="prop">
    <td valign="top" class="name">
      <label for="form">Form</label>
    </td>
    <td valign="top">
      <input type="file" id="form.${i}" name="form.${i}"/>
    </td>
  </tr>
                        
                        
                            
                            <g:hiddenField name="forms[${i}].participant.id" value="${params.participantId }" />