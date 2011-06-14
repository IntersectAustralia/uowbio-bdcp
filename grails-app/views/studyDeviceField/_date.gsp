<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'date', 'errors')}">
                                    <joda:datePicker name="date" precision="day" value="${studyDeviceFields[i]?.date}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>