<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dropDownOption">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'dropDownOption', 'errors')}">
                                    <g:textArea name="dropDownOption" cols="40" rows="5" value="${studyDeviceFieldInstance?.dropDownOption}" />
                                </td>
                            </tr>