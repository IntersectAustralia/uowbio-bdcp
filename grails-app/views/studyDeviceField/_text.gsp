<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="text"><g:message code="studyDeviceField.text.label" default="Text" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'text', 'errors')}">
                                    <g:textArea name="text" cols="40" rows="5" value="${studyDeviceFieldInstance?.text}" />
                                </td>
                            </tr>
