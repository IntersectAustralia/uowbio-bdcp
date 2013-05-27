package au.org.intersect.bdcp.constraints

import java.util.regex.Matcher
import java.util.regex.Pattern

class FilterSpecialCharsOfFilename
{
    static name = "filterSpecialCharsOfFilename"
    static failureCode = "invalid.characters"

    static persistent = false

	def filterSpecialChars = { fileName ->
		
		def regEx = "[/\\\\!\"#&%`()*+',-./:;<=>?@{}~￥]"
		//def regEx = "[/`~!@#$^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*]"
		def pattern = Pattern.compile(regEx)
		def matcher =   pattern.matcher(fileName)
		
		return matcher.replaceAll("_").trim()
	}
	
}
