 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numeric">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'numeric', 'errors')}">
                                    <g:textField name="numeric" value="${fieldValue(bean: studyDeviceFields[i], field: 'numeric')}" />
                                </td>
                                <g:hiddenField name="studyDeviceFields[i].deviceFieldId" value="${deviceFieldInstance.id}" />
                            </tr>