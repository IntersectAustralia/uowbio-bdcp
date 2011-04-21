<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
                    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <g:javascript library="jquery" plugin="jquery"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main" />
  <link rel="stylesheet" href="${resource(dir:'jquery.treeview',file:'jquery.treeview.css')}".css" type="text/css" />
  <script type="text/javascript" src="${resource(dir:'jquery.treeview',file:'jquery.treeview.js')}"></script>
  <script>

  $(document).ready(function(){
    $("#example").treeview();
  });
  
  
  </script>
  
</head>
<body>
<div class="body">
  
  <ul id="example" class="filetree">
  <g:each in="${componentInstanceList}" status="i" var="componentInstance">
  <li><span class="folder">${componentInstance.name}</span>
  <g:each in="${componentInstance.sessions}" status="k" var="sessionInstance">
          <ul>
                 <li><span class="folder">${sessionInstance.name} <img src="${resource(dir:'images/icons',file:'plus.gif')}" /></span></li>
                 <g:each in="${sessionFiles.getAt(sessionInstance.id.toString())}" status="l" var="fileInstance">
                 <li><span class="${fileInstance.type}">${fileInstance.name}</span></li>
                 </g:each>
         </ul>
  </g:each>
  </li>
  </g:each>
		</ul>
     </div>
</body>
</html>
