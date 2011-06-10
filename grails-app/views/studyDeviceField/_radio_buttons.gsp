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
                                <table style="border: 0px; padding:0px;">
                                <tr>
                                   <g:each in="${deviceFieldInstance.getFieldOptionsList()}" var="fieldOptionInstance">
                                   <g:set var="checked" value="${studyDeviceFields[i]?.radioButtonsOption.trim() == fieldOptionInstance.trim()?'checked=checked':''}"/>
                                   <td><span onclick="pick_${i}('${fieldOptionInstance.trim()}')"><input id="${i}" type="radio" value="${fieldOptionInstance}" name="studyDeviceFields[${i}]?.radioButtonsOption"
                                     ${checked}>&nbsp;${fieldOptionInstance}</span></td>
                                     </g:each>
                                </tr>
                                </table>
                                </td>
                                <g:hiddenField id="studyDeviceFields[${i}].radioButtonsOption" name="studyDeviceFields[${i}].radioButtonsOption" value="" />
                            </tr>
