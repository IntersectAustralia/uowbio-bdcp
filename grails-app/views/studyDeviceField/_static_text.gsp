<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="staticContent">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'staticContent', 'errors')}">
                                    <g:textArea name="studyDeviceFields[${i}]?.staticContent" cols="40" rows="5" value="${studyDeviceFields[i]?.staticContent}" />
                                </td>
                            </tr>