<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="time">${studyDeviceField.deviceField.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceField, field: 'time', 'errors')}">
                                    <joda:timePicker name="studyDeviceFields[${i}].time" precision="minute" value="${studyDeviceField.time}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>