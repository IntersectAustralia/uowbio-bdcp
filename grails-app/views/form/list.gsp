
<%@ page import="au.org.intersect.bdcp.Form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'form.label', default: 'Form')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <br />
            <p>Please upload all participant related forms such as participant consent, screening, anthropometry, 
            flexibility, strength, injury history, informed consent, GP clearance, foot problems, subject inclusion 
            criteria, subject exclusion criteria, sample size (and method of calculation, data used), surveys and joint 
            laxity here.
            </p>
            <br />
            <br />
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="name" title="${message(code: 'form.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="location" title="${message(code: 'form.location.label', default: 'Location')}" />
                        
                        	<th><g:message code="form.participant.label" default="Participant" /></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${formInstanceList}" status="i" var="formInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                        
                                <td valign="top" class="value ${hasErrors(bean: formInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${formInstance?.name}" />
                                </td>
                        
							<td><sfu:fileUploadControl/></td>
							                        	
                            <td>${fieldValue(bean: formInstance, field: "participant")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${formInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
