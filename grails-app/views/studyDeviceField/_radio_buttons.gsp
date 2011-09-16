
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="radioButtonsOption">${studyDeviceField.deviceField.fieldLabel}</label>
                                </td>
                                <td style="padding:0px 0px;" class="value ${hasErrors(bean: studyDeviceField, field: 'radioButtonsOption', 'errors')}">
                                <table style="padding:0px;" class="radioGroup">
                                <g:each in="${studyDeviceField.deviceField.getFieldOptionsList().partition(3)}" var="optionsRow">
                                <tr>
                                   <g:each in="${optionsRow}" var="fieldOptionInstance">
                                   <g:set var="checked" value="${fieldOptionInstance.trim().equals(studyDeviceField.radioButtonsOption?.trim())?'checked=checked':''}"/>
                                   <td><input type="radio" value="${fieldOptionInstance}" name="studyDeviceFields[${i}].radioButtonsOption"
                                     ${checked}>&nbsp;${fieldOptionInstance}</td>
                                   </g:each>
                                </tr>                                
                                </g:each>
                                </table>
                                </td>
                            </tr>
