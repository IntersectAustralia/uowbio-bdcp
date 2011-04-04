<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Account Administration</title>
    </head>
    <body>
    <div class="body">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <h1>Account Administration</h1>
    <div class="rowTop"><g:link elementId="create" controller="admin" action="search">Create New User</g:link>
    <g:link elementId="list" controller="admin" action="listUsers">List All Users</g:link></div>
    <div class="rowBottom">
    <div class="buttons">
    <span class="menuButton"><g:link elementId="Back" controller="project" class="list" action="list">Back</g:link></span>
    </div>
    </div>
    </div>
    </body>
</html>