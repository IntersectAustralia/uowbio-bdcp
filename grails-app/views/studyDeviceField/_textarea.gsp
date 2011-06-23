<tr class="prop">
                                
                                <td valign="top" class="name">
                                    <label for="textArea">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'textArea', 'errors')}">
                                    <g:textArea name="studyDeviceFields[${i}].textArea" cols="40" rows="5" value="${studyDeviceFields[i]?.textArea}" />
                                </td>
                                
                            </tr>
                            