<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="staticContent">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'staticContent', 'errors')}">
                                    <g:textArea name="staticContent" cols="40" rows="5" value="${studyDeviceFields[i]?.staticContent}" />
                                </td>
                                <g:hiddenField name="studyDeviceFields[i].deviceFieldId" value="${deviceFieldInstance.id}" />
                            </tr>