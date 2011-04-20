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
<script type="text/javascript">
$(function () {
     $("#demo1").jstree({ 
          "xml_data" : {
               "data" : "" + "${nodes}"
"<root>" + 
     "<item id='node_1'>" + 
          "<content><name>Root node 1</name></content>" + 
     "</item>" + 
     "<item>" + 
          "<content><name>Root node 2</name></content>" + 
     "</item>" + 
     "<item parent_id='node_1'>" + 
          "<content><name>Child node</name></content>" + 
     "</item>" + 
"</root>"
          },
          "plugins" : [ "themes", "xml_data" ]
     });
});


</script>

<p class="log" id="log1" style="clear:both;">&#160;</p>
</html>