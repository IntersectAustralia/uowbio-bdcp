<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
     <title>jsTree &raquo; Documentation &raquo; core</title>
     <g:javascript library="jquery" plugin="jquery"/>
     <script src="${createLinkTo(dir: 'jsTree.v.1.0rc2', file: 'jquery.jstree.js')}" type="text/javascript"></script>
     <script type="text/javascript" src="jquery.jstree.js"></script>
</head>
<input type="button" class="button" value="toggle_node" id="toggle_node" style="clear:both;" />
<div id="demo1" class="demo">
     <ul>
          <li id="phtml_1">

               <a href="#">Root node 1</a>
               <ul>
                    <li id="phtml_2">
                         <a href="#">Child node 1</a>
                    </li>
                    <li id="phtml_3">
                         <a href="#">Child node 2</a>

                    </li>
               </ul>
          </li>
          <li id="phtml_4">
               <a href="#">Root node 2</a>
          </li>
     </ul>
</div>
<script type="text/javascript" class="source">
$(function () {
     $("#toggle_node").click(function () { 
          $("#demo1").jstree("toggle_node","#phtml_1");
     });
     $("#demo1")
          .bind("open_node.jstree close_node.jstree", function (e) {
               $("#log1").html("Last operation: " + e.type);
          })
          .jstree({ "plugins" : [ "themes", "html_data" ] });
     });

</script>
<p class="log" id="log1" style="clear:both;">&#160;</p>
</html>