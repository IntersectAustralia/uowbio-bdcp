<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'date', 'errors')}">
                                    <g:datePicker name="date" precision="day" value="${studyDeviceFieldInstance?.date}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>