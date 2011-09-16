 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="text">${studyDeviceField.deviceField.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceField, field: 'text', 'errors')}">
                                    <g:textField name="studyDeviceFields[${i}].text" cols="40" rows="5" value="${studyDeviceField.text}" />
                                </td>
                            </tr>
