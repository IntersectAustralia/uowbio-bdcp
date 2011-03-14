<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="participantForm.name.label" default="Form Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantForms[i], field: 'formName', 'errors')}">
                                    <input type="text" id="forms[${i}].formName" name="forms[${i}].formName" value="${forms[i]?.formName}"/>
                                </td>
                            </tr>
                          
  <tr>
                            <td>
							<label for="form">Form</label>
							</td>
                            
 							
<td><div class="file_input_div">
<input type="text" id="fileName.${i}" name="fileName.${i}" class="file_input_textbox">
  <input type="button" value="Search files" class="file_input_button" />
  <input type="file" id="form.${i}" name="form.${i}" class="file_input_hidden" onchange="javascript: document.getElementById('fileName.${i}').value = this.value" />
   <br />
  </div></td>
                            
                            
                            </tr>
                            
                            <g:hiddenField name="forms[${i}].participant.id" value="${params.participantId }" />