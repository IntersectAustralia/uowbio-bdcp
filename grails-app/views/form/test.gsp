<html>
<head>
<meta name="layout" content="main" />
<title>Delete table row on user click Demo</title>
<style>
/* Pretty printing styles. Used with prettify.js. */
.str { color: #080; }
.kwd { color: #008; }
.com { color: #800; }
.typ { color: #606; }
.lit { color: #066; }
.pun { color: #660; }
.pln { color: #000; }
.tag { color: #008; }
.atn { color: #606; }
.atv { color: #080; }
.dec { color: #606; }
pre.prettyprint, pre.code { padding: .5em;border:none; border-left: 3px solid #ccc; background-color:#fbfbfb }
@media print {
.str { color: #060; }
.kwd { color: #006; font-weight: bold; }
.com { color: #600; font-style: italic; }
.typ { color: #404; font-weight: bold; }
.lit { color: #044; }
.pun { color: #440; }
.pln { color: #000; }
.tag { color: #006; font-weight: bold; }
.atn { color: #404; }
.atv { color: #060; }
</style>
<g:javascript src="jquery-1.4.2.min.js" type="text/javascript" />
<script src='http://www.gstatic.com/codesite/ph/14407163836014391775/js/prettify/prettify.js'></script> <style>
</style>
<script>
$(document).ready(function(){
	$('#table1 td').click(function(){
		$(this).parent().remove();
	});
	$('#table2 td img.delete').click(function(){
		$(this).parent().parent().remove();
	});
});
</script>
</head>
<body>
<h1>Delete table row on user click Demo</h1>

<p>This is a demo for <a href="http://jquery-howto.blogspot.com/2009/05/remove-table-row-on-user-click.html" title="Delete table row on user click">Delete table row on user click</a> blog post.</p>

<h2>Click on any row to delete it</h2>

<table id="table1" border='1'>
  <tr>
    <td>This is a table with id="table1"</td>
	<td>Column 2</td>

  </tr>
  <tr>
    <td>row 2, cell 1</td>
	<td>Column 2</td>
  </tr>
  <tr>
    <td>row 3, cell 1</td>

	<td>Column 2</td>
  </tr>
  <tr>
    <td>row 4, cell 1</td>
	<td>Column 2</td>
  </tr>
  <tr>

    <td>row 5, cell 1</td>
	<td>Column 2</td>
  </tr>
</table>

<p><strong>Code:</strong></p>

<pre class="prettyprint lang-js">$(document).ready(function(){
	$('#table1 td').click(function(){
		$(this).parent().remove();
	});
});</pre>


<h2>Click on delete image to delete it</h2>

<table id="table2" border="1">
  <tr>
    <td>row 1, cell 1</td>
	<td><img class="delete" src="../images/delete.gif" /></td>
  </tr>
  <tr>
    <td>row 2, cell 1</td>

	<td><img class="delete" src="../images/delete.gif" /></td>
  </tr>
  <tr>
    <td>row 3, cell 1</td>
	<td><img class="delete" src="../images/delete.gif" /></td>
  </tr>
  <tr>
    <td>row 5, cell 1</td>

	<td><img class="delete" src="../images/delete.gif" /></td>
  </tr>
  <tr>
    <td>row 5, cell 1</td>
	<td><img class="delete" src="../images/delete.gif" /></td>
  </tr>
</table>

<p><strong>Code:</strong></p>

<pre class="prettyprint lang-js">$(document).ready(function(){
	$('#table2 td img.delete').click(function(){
		$(this).parent().parent().remove();
	});
});</pre>

<hr />

<a href="../">&laquo; Other demos</a>

</body>
</html>