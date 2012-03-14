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
					class="value ${formErrors['firstName'] ? 'errors' : ''}"><g:textField name="firstName" value="${firstName}" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="surname">Surname</label>
				</td>
				<td valign="top"
					class="value ${formErrors['surname'] ? 'errors' : ''}"><g:textField name="surname" value="${surname}" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="email">Email</label></td>
				<td valign="top"
					class="value ${formErrors['email'] ? 'errors' : ''}"><g:textField name="email" value="${email}" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="password">Password</label></td>
				<td valign="top"
					class="value ${formErrors['password'] ? 'errors' : ''}"><g:passwordField name="password" value="" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="password_2">Confirm password</label></td>
				<td valign="top"
					class="value ${formErrors['password_2'] ? 'errors' : ''}"><g:passwordField name="password_2" value="" />
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
