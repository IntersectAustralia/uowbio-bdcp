<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="textArea"><g:message code="studyDeviceField.textArea.label" default="Text Area" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'textArea', 'errors')}">
                                    <g:textArea name="textArea" cols="40" rows="5" value="${studyDeviceFieldInstance?.textArea}" />
                                </td>
                            </tr>