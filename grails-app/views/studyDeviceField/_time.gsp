<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="time">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'time', 'errors')}">
                                    <g:datePicker name="time" precision="day" value="${studyDeviceFields[i]?.time}" default="none" noSelection="['': '']" />
                                </td>
                                <g:hiddenField name="studyDeviceFields[i].deviceFieldId" value="${deviceFieldInstance.id}" />
                            </tr>