<tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="radioButtonsOption">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td style="padding:0px 0px;">
                                <table style="border: 0px; padding:0px;">
                                <tr>
                                   <g:each in="${deviceFieldInstance.getFieldOptionsList()}" var="fieldOptionInstance">
                                   <g:set var="checked" value="${fieldOptionInstance.equals(studyDeviceFields[i]?.radioButtonsOption)?'checked=checked':''}"/>
                                   <td><span><input id="studyDeviceFields[${i}]?.radioButtonsOption" type="radio" value="${fieldOptionInstance}" name="studyDeviceFields[${i}]?.radioButtonsOption"
                                     ${checked}
                                     >&nbsp;${fieldOptionInstance}</span></td>
                                     </g:each>
                                </tr>
                                </table>
                                </td>
                                <g:hiddenField name="studyDeviceFields[${i}].deviceFieldId" value="${deviceFieldInstance.id}" />
                            </tr>