 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="text">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'text', 'errors')}">
                                    <g:textField name="text" cols="40" rows="5" value="${studyDeviceFieldInstance?.text}" />
                                </td>
                            </tr>
