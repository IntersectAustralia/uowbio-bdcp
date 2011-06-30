<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'date', 'errors')}">
                                    <g:jqDatePicker name="studyDeviceFields[${i}].date" value="${studyDeviceFields[i]?.date}" />
                                </td>
                            </tr>