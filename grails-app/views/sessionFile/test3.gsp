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
		<li><span class="folder">Folder 1 <img src="${resource(dir:'images/icons',file:'plus.gif')}" /></span>
			<ul>
				<li><span class="file">Item 1.1</span></li>
			</ul>
		</li>
		<li><span class="folder">Folder 2</span>
			<ul>
				<li><span class="folder">Subfolder 2.1</span>
					<ul>
						<li><span class="file">File 2.1.1</span></li>
						<li><span class="file">File 2.1.2</span></li>
					</ul>
				</li>
				<li><span class="file">File 2.2</span></li>
			</ul>
		</li>
		<li class="closed"><span class="folder">Folder 3</span>
			<ul>
				<li><span class="file">File 3.1</span></li>
			</ul>
		</li>
		<li><span class="file">File 4</span></li>
	</ul>
     </div>
</body>
</html>
