<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
   		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">
    </head>
    <body class="yui-skin-sam" onunload="">
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div id="biomechsLogo"><a href="http://www.uow.edu.au/health/brl/index.html"><img src="${resource(dir:'images',file:'biomechs_logo.jpg')}" alt="Biomechanics" border="0" /></a></div>
        <div class="columnRight"><sec:username /> <sec:ifLoggedIn>(<g:link elementId="Logout" controller="logout">Logout</g:link>)</sec:ifLoggedIn></div>
        <g:layoutBody />
    </body>
    <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1">
    </head>
    </html>
</html>