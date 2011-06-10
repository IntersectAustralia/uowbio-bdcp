<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="staticContent">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'staticContent', 'errors')}">
                                <p>${fieldValue(bean: deviceFieldInstance, field: 'staticContent').decodeHTML()}</p>
                                </td>
                            </tr>