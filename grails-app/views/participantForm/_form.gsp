<tr class= "prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="participantForm.name.label" default="Form Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantForms[i], field: 'formName', 'errors')}">
                                    <input type="text" id="forms[${i}].formName" name="forms[${i}].formName" value="${forms[i]?.formName}"/>
                                </td>
                            </tr>
                          
  <tr class="prop">
                            <td valign="top" class="name">
							<label for="name">Form</label>
							</td>
                            
 							
<td valign="top" class="value ${hasErrors(bean: participantForms[i], field: 'fileName', 'errors')}">
<div class="file_input_div">

  <input type="text" id="forms[${i}].fileName" name="forms[${i}].fileName" class="file_input_textbox" value="${forms[i]?.fileName}" readonly = "readonly">
  <input type="button" value="Browse" class="file_input_button" />
  <input type="file" id="form.${i}" name="form.${i}" class="file_input_hidden" onchange="CopyMe(this, 'forms[${i}].fileName');" />
  
  </div></td>
  <g:hiddenField name="forms[${i}].participant.id" value="${params.participantId }" />
                            
                            </tr>
                            
                            