package au.org.intersect.bdcp

import groovy.xml.MarkupBuilder

class TraversalTagLib {

	def traversalTag = {attrs ->
    def currentItem = attrs.currentItem
	def allItems = attrs.allItems
	def currentDir = attrs.currentDir
	def componentInstance = attrs.componentInstance
	
	if (currentItem == null || currentDir == null)
	{
		
	}
	else if (currentItem?.parent == currentDir)
	{
		out << "<li><span class='${currentItem?.type}'>${currentItem?.name}</span></li>"
	}
	else if (currentItem?.children != null)
	{
		currentItem.children.each 
		{
			allItems.findAll 
			{
				  path: it
			}.each 
		     {
				out << "<li><span class='${currentItem?.type}'>${currentItem?.name}</span></li>"
			 }
		}
		
	}
	
//    if (false){
//        out << recursiveTag(item:item)
//    }
}

}
