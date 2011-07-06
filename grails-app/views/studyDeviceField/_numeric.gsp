 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numeric">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'numeric', 'errors')}">
                                    <g:textField name="studyDeviceFields[${i}].numeric" value="${fieldValue(bean: studyDeviceFields[i], field: 'numeric')}"/>
                                </td>
                            </tr>