<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
     <title>jsTree &raquo; Documentation &raquo; core</title>
     <g:javascript library="jquery" plugin="jquery"/>
     <script src="${createLinkTo(dir: 'jsTree.v.1.0rc2', file: 'jquery.jstree.js')}" type="text/javascript"></script>
    
</head>
<input type="button" class="button" value="toggle_node" id="toggle_node" style="clear:both;" />
<div id="demo1" class="demo">
</div>
<script type="text/javascript" class="source">

$(function () {
	$("#demo1").jstree({ 
		"xml_data" : {
			"data" : "" + "${nodes}"
		},
		'types' : {
		    'valid_children' : [ 'folder' ],
		    'types' : {
		        'folder' : {
		            'valid_children' : [ 'file'],
		            'max_depth' : 5
		        },

		        'file' : {
		            'valid_children' : [ 'none' ],
		            'icon' : { 'image' : "${resource(dir:'images',file:'file.png')}" },
		        }
		    }
		},
				
		"plugins" : [ "themes", "xml_data", "types" ]
	});
	$("#demo1").jstree("open_all","#nodeID");
});

</script>
<p class="log" id="log1" style="clear:both;">&#160;</p>
</html>