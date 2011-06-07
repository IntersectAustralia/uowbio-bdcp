<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date"><g:message code="studyDeviceField.date.label" default="Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'date', 'errors')}">
                                    <g:datePicker name="date" precision="day" value="${studyDeviceFieldInstance?.date}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>