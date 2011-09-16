 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numeric">${studyDeviceField.deviceField.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceField, field: 'numeric', 'errors')}">
                                    <g:textField name="studyDeviceFields[${i}].numeric" value="${fieldValue(bean: studyDeviceField, field: 'numeric')}"/>
                                </td>
                            </tr>