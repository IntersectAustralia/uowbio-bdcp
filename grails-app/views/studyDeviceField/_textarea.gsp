<tr class="prop">
                                
                                <td valign="top" class="name">
                                    <label for="textArea">${studyDeviceField.deviceField.fieldLabel}</label>
                                </td>
                                
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceField, field: 'textArea', 'errors')}">
                                    <g:textArea name="studyDeviceFields[${i}].textArea" cols="40" rows="5" value="${studyDeviceField.textArea}" />
                                </td>
                                
                            </tr>
                            