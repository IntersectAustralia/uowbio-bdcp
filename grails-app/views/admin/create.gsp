<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Confirm Account Creation</title>
    </head>
    <body>
    <div class="body">
     <p> Confirm account creation for ${username} (User will receive an email notification </p>
     <p> advising of new account.) </p>
	 <br />
	 <div class="rowTop">   
     <g:link controller="admin" class="create" elementId="Confirm" params="[username: params.username]" action="save" method="post">Confirm</g:link>
     <g:link controller="admin" class="create" elementId="Cancel" action="searchUsers" params="[surname:session.surname, firstName: session.firstName, userid: session.userid]" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" >Cancel</g:link>
     </div>
    <div class="rowBottom">
    <div class="buttons">
    <g:link controller="admin" elementId="Back" class="create" action="searchUsers" params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Back</g:link>
    </div>
    </div>
    </div>
    </body>
</html>