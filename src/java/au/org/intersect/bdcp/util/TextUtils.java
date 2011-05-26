package au.org.intersect.bdcp.util;

import java.util.regex.Pattern;

public class TextUtils {
	
	private static Pattern htmlMarkup = Pattern.compile("<[^>]+>");
	private static Pattern whiteSpace = Pattern.compile("(\\s|&nbsp;)+");
	
	/**
	 * Removes html markup (tags) and checks if not empty
	 * 
	 * @param html string to search and remove in
	 * @return true if after removing markup and &nbsp; the string is not empty
	 */
	public static boolean isNotEmpty(String html) {
		if (html == null)
		{
			return false;
		}
		String noHtml = htmlMarkup.matcher(html).replaceAll("");
		return whiteSpace.matcher(noHtml).replaceAll("").length() > 0;
	}

}
