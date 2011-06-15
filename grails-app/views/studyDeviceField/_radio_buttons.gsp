<script>
function pick_${i}(option){
	document.getElementById("studyDeviceFields[${i}].radioButtonsOption").value=option
}
</script>
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
                                   <td><span onclick="pick_${i}('${fieldOptionInstance?.trim()}')"><input id="${i}" type="radio" value="${fieldOptionInstance}" name="radioButtonsOption"
                                     ${checked}>&nbsp;${fieldOptionInstance}</span></td>
                                   </g:each>
                                </tr>                                
                                </table>
                                </g:each>
                                </td>
                                <g:hiddenField id="studyDeviceFields[${i}].radioButtonsOption" name="studyDeviceFields[${i}].radioButtonsOption" value="${fieldValue(bean: studyDeviceFields[i], field: 'radioButtonsOption')}" />
                            </tr>
