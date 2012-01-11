<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Create External User Account</title>
</head>
<body>
<div class="body">

<h1>Create External User Account</h1>
<g:form action="createExternalUser" method="post">
	<div class="dialog">
	<table>
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><label for="firstName">First Name</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}"><g:textField name="firstName" value="${session?.firstName}" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="surname">Surname</label>
				</td>
				<td valign="top"
					class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}"><g:textField name="surname" value="${session?.surname}" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="email">Email</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}"><g:textField name="email" value="${session?.email}" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="password">Password</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}"><g:passwordField name="password" value="${session?.password}" />
				</td>
			</tr>

		</tbody>
	</table>
	</div>

    <span class="button"><g:submitButton name="create" id="create" class="save button" value="Create" /></span>
	
</g:form>

	<div class="buttons">
    	<span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="search">Back</g:link></span>
    	<span class="button"><g:link elementId="cancel" controller="admin" class="list" action="accountAdmin">Cancel</g:link></span>
	</div>
  
</div>
</body>
</html>