<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dropDownOption">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'dropDownOption', 'errors')}">
                                    <g:select name="studyDeviceFields[${i}].dropDownOption" noSelection="['':'']" from="${deviceFieldInstance.getFieldOptionsList()}" value="${studyDeviceFields[i]?.dropDownOption}" />
                                </td>
                            </tr>