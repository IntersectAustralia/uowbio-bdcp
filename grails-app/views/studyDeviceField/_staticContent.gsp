<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="studyDeviceField.staticContent.label" default="Static Content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyDeviceFieldInstance, field: 'staticContent', 'errors')}">
                                    <g:textArea name="staticContent" cols="40" rows="5" value="${studyDeviceFieldInstance?.staticContent}" />
                                </td>
                            </tr>