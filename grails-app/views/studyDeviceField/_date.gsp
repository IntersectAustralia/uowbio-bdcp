<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date">${studyDeviceField.deviceField.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceField, field: 'date', 'errors')}">
                                    <g:jqDatePicker name="studyDeviceFields[${i}].date" value="${studyDeviceField.date}" />
                                </td>
                            </tr>