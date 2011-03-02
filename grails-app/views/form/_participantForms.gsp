<script type="text/javascript">
    var childCount = ${formInstance?.participantForms.size()} + 0;

    function addParticipantForm(){
      var clone = $("#participantForm_clone").clone()
      var htmlId = 'participantFormsList['+childCount+'].';
      var participantFormInput = clone.find("input[id$=participantForm]");

      clone.find("input[id$=id]")
             .attr('id',htmlId + 'id')
             .attr('name',htmlId + 'id');
      clone.find("input[id$=deleted]")
              .attr('id',htmlId + 'deleted')
              .attr('name',htmlId + 'deleted');
      clone.find("input[id$=new]")
              .attr('id',htmlId + 'new')
              .attr('name',htmlId + 'new')
              .attr('value', 'true');
      participantFormInput.attr('id',htmlId + 'participantForm')
              .attr('name',htmlId + 'question');
      clone.find("select[id$=type]")
              .attr('id',htmlId + 'type')
              .attr('name',htmlId + 'type');

      clone.attr('id', 'participantForm'+childCount);
      $("#childList").append(clone);
      clone.show();
      participantFormInput.focus();
      childCount++;
    }

    //bind click event on delete buttons using jquery live
    $('.del-participantForm').live('click', function() {
        //find the parent div
        var prnt = $(this).parents(".participantForm-div");
        //find the deleted hidden input
        var delInput = prnt.find("input[id$=deleted]");
        //check if this is still not persisted
        var newValue = prnt.find("input[id$=new]").attr('value');
        //if it is new then i can safely remove from dom
        if(newValue == 'true'){
            prnt.remove();
        }else{
            //set the deletedFlag to true
            delInput.attr('value','true');
            //hide the div
            prnt.hide();
        }
    });

</script>

<div id="childList">

    <div class=list>
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="name" title="${message(code: 'form.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="link" title="${message(code: 'form.link.label', default: 'Link')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${formInstanceList}" status="i" var="formInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <!-- Render the participantForm template (_participantForm.gsp) here -->
        					<g:render template='participantForm' model="['formInstance':formInstance]"/>
        					<!-- Render the participantForm template (_participantForm.gsp) here -->
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${formInstanceTotal}" />
            </div>
</div>
