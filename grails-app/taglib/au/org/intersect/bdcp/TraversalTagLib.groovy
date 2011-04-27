package au.org.intersect.bdcp

import groovy.xml.MarkupBuilder

class TraversalTagLib {

	def traversalTag = {attrs ->	
	def file = attrs.file
	def sessionInstance = attrs.session
		
	def type = file?.isDirectory() ? "folder" : "file"
	if (type == "file")
	{
		out << "<li><span class='${type}'>${file?.getName()}</span>"
	}
	else
	{
		out << "<li><span class='${type}'>${file?.getName()} <a href='${createLink(mapping:"sessionFileDetails", controller:"sessionFile", action:"browseFiles", params:['studyId': params.studyId,'sessionId': sessionInstance.id, 'directory':file.getName(), 'parent': file.getParentFile().getName()])}'><img src=\"${resource(dir:'images/icons',file:'upload.png')}\"  alt=\"Upload\" title=\"Upload\"/></a></span>"
	}
	File[] children = file.listFiles()
	if (children?.size() > 0)
	{
		out << "\n<ul>\n"
		for (File child: children)
		{
			out << traversalTag(file: child, session: sessionInstance)
		}
		out << "\n</ul></li>\n"
	}
	else
	{
		out <<"</li>\n"
	}
			

	
//    if (false){
//        out << recursiveTag(item:item)
//    }
}

}
