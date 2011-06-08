<tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="fieldType">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td style="padding:0px 0px;">
                                <table style="border: 0px; padding:0px;">
                                <tr>
                                   <g:each in="${deviceFieldInstance.getFieldOptionsList()}" var="fieldOptionInstance">
                                   <td><span><input type="radio" value="${studyDeviceFields[i]?.radioButtonsOption}" name="fieldOption"
                                     ${checked}
                                     >&nbsp;${fieldOptionInstance}</span></td>
                                   </g:each>
                                </tr>
                                </table>
                                </td>
                                <g:hiddenField name="studyDeviceFields[i].deviceFieldId" value="${deviceFieldInstance.id}" />
                            </tr>