<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>New UoW User Account</title>
</head>
<body>
<div class="body">

<h1>New UoW User Account</h1>
<g:form action="searchUsers" method="post">
	<div class="dialog">
	<table>
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><label for="firstName">First Name</label></td>
				<td valign="top" class="value"><g:textField name="firstName" value="${session?.firstName}" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="surname">Surname</label>
				</td>
				<td valign="top" class="value"><g:textField name="surname" value="${session?.surname}" />
				</td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="userid">User ID</label></td>
				<td valign="top" class="value"><g:textField name="userid" value="${session?.userid}" />
				</td>
			</tr>

		</tbody>
	</table>
	</div>

    <span class="button"><g:submitButton	name="create" id="search" class="save button" value="Search UoW" /></span>
	
</g:form>
<br/><hr/><br/>
	<h1 class="border-button"><g:link elementId="createExternal" controller+"admin" action="displayCreateExternalUser">Create External User Account</g:link></h1>

        <g:if test="${searching}">
            <g:if test="${matches.size()>0}">
                <h2>Results found, click <i>Select</i> on user to create UoW Account</h2>
	        <g:render template="list" model="['matches': matches]" />
            </g:if>
            <g:else>
                <h2>No results found</h2>
            </g:else>
        </g:if>

	<div class="buttons">
    	<span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="accountAdmin">Back</g:link></span>
    	<span class="button"><g:link elementId="cancel" controller="admin" class="list" action="accountAdmin">Cancel</g:link></span>
	</div>
  
</div>
</body>
</html>
