
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="radioButtonsOption">${deviceFieldInstance.fieldLabel}</label>
                                </td>
                                <td style="padding:0px 0px;" class="value ${hasErrors(bean: studyDeviceFields[i], field: 'radioButtonsOption', 'errors')}">
                                <table style="padding:0px;" class="radioGroup">
                                <g:each in="${deviceFieldInstance.getFieldOptionsList().partition(3)}" var="optionsRow">
                                <tr>
                                   <g:each in="${optionsRow}" var="fieldOptionInstance">
                                   <g:set var="checked" value="${studyDeviceFields[i]?.radioButtonsOption?.trim().equals(fieldOptionInstance?.trim())?'checked=checked':''}"/>
                                   <td><input type="radio" value="${fieldOptionInstance}" name="studyDeviceFields[${i}].radioButtonsOption"
                                     ${checked}>&nbsp;${fieldOptionInstance}</td>
                                   </g:each>
                                </tr>                                
                                </g:each>
                                </table>
                                </td>
                            </tr>
