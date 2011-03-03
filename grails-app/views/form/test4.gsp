<%@ page import="au.org.intersect.bdcp.Form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'form.label', default: 'Form')}" />
        <g:javascript src="jquery-1.4.2.min.js" type="text/javascript" />
    	<style>
		body { font-size: 62.5%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style>
    	<script language="javascript">
        function addRow(tableID) {
 
            var table = document.getElementById(tableID);
 	
            var rowCount = table.rows.length;
            var row = table.insertRow(rowCount);
 
            var cell1 = row.insertCell(0);
            var element1 = document.createElement("input");
            element1.type = "text";
            cell1.appendChild(element1);
 
            var cell2 = row.insertCell(1);
            var element2 = document.createElement("button");
            element2.setAttribute("id","create-user2");
            element2.setAttribute("value", "Attach Form")
            element2.setAttribute("onClick", "$('#dialog-form2').dialog('open');")
            theText=document.createTextNode("Attach Form");
			element2.appendChild(theText)
            cell2.appendChild(element2);

            var cell3 = row.insertCell(2);
            var element3 = document.createElement("img");
			element3.setAttribute('src',"${resource(dir:'images/skin', file:'icon_delete.png')}")
			element3.setAttribute('class', 'delete');
			element3.setAttribute('style', "vertical-align:middle;");
			element3.setAttribute('hspace','4');
			element3.onclick=function(){$(this).parent().parent().remove();};
			cell3.appendChild(element3)
 
        }

        $(document).ready(function(){
        	$('#dataTable td img.delete').click(function(){
        		$(this).parent().parent().remove();
        	});
        });

        $(function() {

        	  var options = {
        	      autoOpen: false,
        	      width: 600,
        	      modal: true
        	    };

        	  $([1,2,3]).each(function() {
        	    var num = this;
        	    var dlg = $('#dialog-form' + num)
        	      .dialog(options);
        	    $('#create-user' + num).click(function() {
        	      dlg.dialog("open");
        	    });
        	  });

        	});
        	        		        
    </script>
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="dialog-form1" title="Create new user">
	<p class="validateTips">All form fields are required.</p>

	<form>
	<fieldset>
		<label for="name">Name</label>
		<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" />
		<label for="email">Email</label>
		<input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all" />
		<label for="password">Password</label>
		<input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
	</fieldset>
	</form>
	</div>
	<div id="dialog-form2" title="Create new user">
	<p class="validateTips">All form fields are required.</p>

	<form>
	<fieldset>
		<label for="name">Name</label>
		<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" />
		<label for="email">Email</label>
		<input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all" />
		<label for="password">Password</label>
		<input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
	</fieldset>
	</form>
	</div>

        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Forms</h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <INPUT type="button" value="Add Form" onclick="addRow('dataTable')" />
                <table id="dataTable">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="name" title="${message(code: 'form.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="link" title="${message(code: 'form.link.label', default: 'Link')}" />
                        	
                        	<td></td>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${formInstanceList}" status="i" var="formInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><input type="text" value = "${fieldValue(bean: formInstance, field: "name")}" /></td>
                        
                            <td><button id="create-user1">Attach Form</button></td>
                            
                
                            <td><img class="delete" src="${resource(dir:'images/skin', file:'icon_delete.png')}" style="vertical-align:middle;"/></td>
                        
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
