<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dropDownOption">${studyDeviceField.deviceField.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceField, field: 'dropDownOption', 'errors')}">
                                    <g:select name="studyDeviceFields[${i}].dropDownOption" noSelection="['':'']" from="${studyDeviceField.deviceField.getFieldOptionsList()}" value="${studyDeviceField.dropDownOption}" />
                                </td>
                            </tr>