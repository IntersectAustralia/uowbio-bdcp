<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dropDownOption">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'dropDownOption', 'errors')}">
                                    <g:select name="dropDownOption" from="${deviceFieldInstance.getFieldOptionsList()}" value="${studyDeviceFields[i]?.dropDownOption}" />
                                </td>
                                <g:hiddenField name="studyDeviceFields[i].deviceFieldId" value="${deviceFieldInstance.id}" />
                            </tr>