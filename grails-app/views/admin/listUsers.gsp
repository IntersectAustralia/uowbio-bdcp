<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:javascript library="prototype"/>
        <title>List All Users</title>
        <script type="text/javascript">
        function gup( name )
		{
		  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
		  var regexS = "[\\?&]"+name+"=([^&#]*)";
		  var regex = new RegExp( regexS );
		  var results = regex.exec( window.location.href );
		  if( results == null )
		    return "";
		  else
		    return results[1];
		}

        window.onload = function setcheckbox()
        {

			if (gup('hideUsers') != null )
			{
				if (gup('hideUsers') == "true")
				{
					document.myform.hideUsers.checked = true;
					submitform();
				}
				else if (gup('hideUsers') == "false")
				{
					document.myform.hideUsers.checked = false;
					submitform();
				}
						
			}
		}

		function submitform()
		{
			var form = new Element('form',
                    {method: 'post', action: 'listUsers'});
				form.insert(new Element('input',
                     {name: 'hideDeactivatedUsers', value: document.myform.hideUsers.checked, type: 'hidden'}));
			$(document.body).insert(form);
			form.submit();
		}

		
		</script>
    </head>
    <body>
    <div class="body">

    <h1>List All Users</h1>
    <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
     
     <g:form name="myform" controller="admin" action="listUsers">
     <table>
     <tbody>
     <tr class="prop">
                            <td valign="top" class="name"><g:message code="admin.enabled.label" default="Hide Deactivated Users" /></td>
                            
                            <td valign="top" class="value"><g:checkBox elementId="hide deactivated users" name='hideUsers' value="${hideUsers}" onclick="submitform();"/></td>
                            
                        </tr>
     </tbody>
     </table>
    <g:render template="listUsers" model="['matches': matches, 'hideUsers': hideUsers]"/>
   </g:form>
   <div class="buttons">
   					<span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="accountAdmin">Back</g:link></span>
                </div>
    </div>
    
    </body>
</html>