<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:javascript library="jquery" plugin="jquery" />
<jqui:resources />
<script type="text/javascript">
        $(function() {
            // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
            $( "#dialog:ui-dialog" ).dialog( "destroy" );
     
            $( "#dialog-confirm" ).dialog({
                  autoOpen: false,
                  resizable: false,
                  height:140,
                  modal: true,
                  buttons: {
                        "Yes": function() {
                              $( this ).dialog( "close" );
                              window.location.href = "${createLink(controller:"admin", action:'searchUsers', params:"[surname:session.surname, firstName: session.firstName, userid: session.userid]")}"
                        },
                        "No": function() {
                              $( this ).dialog( "close" );
                        }
                  }
            });
 
            $('#Cancel').click(function() {
                  $('#dialog-confirm').dialog('open');
                  // prevent the default action, e.g., following a link
                  return false;
            });
                 
      });
            
          </script>
 
<title>Confirm Account Creation</title>
</head>
<body>
 
<div id="dialog-confirm" title="Cancel the creation of a user account?">
<p><span class="ui-icon ui-icon-alert"
      style="float: left; margin: 0 7px 20px 0;"></span>Are you sure?</p>
</div>
<div class="body">

<h1>Confirm new account</h1>
 
<p>Confirm account creation for ${title} ${firstName} ${surname}, with userid: ${userid} with role: ${rolename}. (User will receive an
email notification</p>
<p>advising of new account.)</p>
<br />

<div class="rowTop">
     <g:form>
      <g:hiddenField name="isExternal" value="${isExternal}" />
      <g:hiddenField name="userid" value="${userid}" />
      <g:hiddenField name="username" value="${username}" />
      <g:hiddenField name="firstName" value="${firstName}" />
      <g:hiddenField name="surname" value="${surname}" />
      <g:hiddenField name="email" value="${email}" />
      <g:hiddenField name="password" value="${password}" />
      <g:hiddenField name="title" value="${title}" />
      <g:hiddenField name="authority" value="${authority}" />
      <g:hiddenField name="nlaIdentifier" value="${nlaIdentifier}" />
      <div class="buttons">
            <span class="button">
                  <g:actionSubmit name="save" id="confirm" class="save right" controller="admin" action="save" value="Confirm" />
            </span>
            <span class="menuButton">
                  <g:if test="${isExternal}">
                      <g:actionSubmit name="back" id="cancel" class="cancel left" controller="admin" action="displayCreateExternalUser" value="Cancel" />
                  </g:if>
                  <g:else>
                      <g:link controller="admin"
		          elementId="cancel" class="cancel left anchor" action="searchUsers"
		          params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Cancel</g:link>
                  </g:else>
            </span>
      </div>
 
     </g:form>
</div>
</div>
</div>
</body>
</html>
