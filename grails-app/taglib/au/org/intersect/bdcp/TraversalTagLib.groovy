package au.org.intersect.bdcp

import groovy.xml.MarkupBuilder

class TraversalTagLib {

	def traversalTag = {attrs ->	
	def file = attrs.file
	def sessionInstance = attrs.session
	def status = attrs.status
	def statusValues = status.split("-")
	def incrementedValue = statusValues[1].toInteger() + 1
	def outputStatus = "${statusValues[0]}-${incrementedValue}"
		
	def type = file?.isDirectory() ? "folder" : "file"
	if (type == "file")
	{
		out << "<li><span class='${type}'>${file?.getName()}</span>"
	}
	else
	{
		out << "<li><span class='${type}'>${file?.getName()} <a href='${createLink(mapping:"sessionFileDetails", controller:"sessionFile", action:"browseFiles", params:['studyId': params.studyId,'sessionId': sessionInstance.id, 'directory':file.getName(), 'parent': file.getParentFile().getName()])}' id=\"upload[${status}]\"><img src=\"${resource(dir:'images/icons',file:'upload.png')}\"  alt=\"Upload Files\" title=\"Upload Files\"/></a>  <a href='${createLink(mapping:"sessionFileDetails", controller:"sessionFile", action:"createDirectory", params:['studyId': params.studyId,'sessionId': sessionInstance.id, 'directory':file.getName(), 'parent': file.getParentFile().getName()])}' id=\"createDirectory[${status}]\"><img src=\"${resource(dir:'images/icons',file:'plus.gif')}\"  alt=\"Add Directory\" title=\"Add Directory\"/></a></span>"
	}
	File[] children = file.listFiles()
	if (children?.size() > 0)
	{
		out << "\n<ul>\n"
		for (File child: children)
		{
			out << traversalTag(file: child, session: sessionInstance, status: outputStatus)
		}
		out << "\n</ul></li>\n"
	}
	else
	{
		out <<"</li>\n"
	}
			

}

}
