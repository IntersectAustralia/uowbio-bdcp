 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="text">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'text', 'errors')}">
                                    <g:textField name="studyDeviceFields[${i}].text" cols="40" rows="5" value="${studyDeviceFields[i]?.text}" />
                                </td>
                                <g:hiddenField name="studyDeviceFields[${i}].deviceFieldId" value="${deviceFieldInstance.id}" />
                            </tr>
