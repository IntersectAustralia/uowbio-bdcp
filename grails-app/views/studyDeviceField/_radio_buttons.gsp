
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="radioButtonsOption">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td style="padding:0px 0px;">
                                <g:each in="${deviceFieldInstance.getFieldOptionsList().partition(3)}" var="optionsRow">
                                <table style="border: 0px; padding:0px;">
                                <tr>
                                   <g:each in="${optionsRow}" var="fieldOptionInstance">
                                   <g:set var="checked" value="${studyDeviceFields[i]?.radioButtonsOption?.trim().equals(fieldOptionInstance?.trim())?'checked=checked':''}"/>
                                   <td><input type="radio" value="${fieldOptionInstance}" name="studyDeviceFields[${i}].radioButtonsOption"
                                     ${checked}>&nbsp;${fieldOptionInstance}</td>
                                   </g:each>
                                </tr>                                
                                </table>
                                </g:each>
                                </td>
                            </tr>
