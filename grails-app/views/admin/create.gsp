<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Confirm Account Creation</title>
    </head>
    <body>
    <div class="body">
    <p> Confirm account creation for dpollum (User will recieve an email notification </p>
     <p> advising of new account.) </p>
	 <br />
	 <div class="rowTop">   
     <g:link controller="project" class="create" action="list">Confirm</g:link>
     <g:link controller="admin" class="create" action="searchUsers" params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Cancel</g:link>
     </div>
    <div class="rowBottom">
    <div class="buttons">
    <g:link controller="admin" class="create" action="searchUsers" params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Back</g:link>
    </div>
    </div>
    </div>
    </body>
</html>