<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>List All Users</title>
    </head>
    <body>
    <div class="body">
    <span class="menuButton"><a id="home" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <h1>List All Users</h1>
    <g:render template="listUsers" model="['matches': matches]"/>
   <div class="buttons">
   					<span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="accountAdmin">Back</g:link></span>
                </div>
    </div>
    </body>
</html>