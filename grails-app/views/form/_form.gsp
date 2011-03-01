<div class="largeDialog">
  <table>
      <tbody>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="participantForms"><g:message code="form.participantForms.label" default="Participant Forms" /></label>
      </td>
        <td valign="top" class="value ${hasErrors(bean: formInstance, field: 'participantForms', 'errors')}">

        <!-- Render the participantForms template (_participantForms.gsp) here -->
        <g:render template="participantForms" model="['formInstance':formInstance]" />
        <!-- Render the participantForms template (_participantForms.gsp) here -->

    </td>
    </tr>
    </tbody>
  </table>
</div>
