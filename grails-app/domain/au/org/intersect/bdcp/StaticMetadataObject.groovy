package au.org.intersect.bdcp

class StaticMetadataObject {
	
	private static final String FILEPREFIX = "static"
	
	/**
	 * Something to identify the static field to the user in user interface or in database.
	 */
	String description
	
	/**
	 * this is rif-cs registryObjects content, which will contain a single registryObject type and no related objects
	 */
	String xmlContent

	/**
	 * for grails timestamping	
	 */
	Date dateCreated
	Date lastUpdated
	
	static constraints =
	{
		description(blank:false, nullable:false, maxSize:1024)
		// 10485760 : Maximum size in Postgres, please keep it
		xmlContent(blank:false, nullable:false, maxSize:10485760)
	}
	
	public static void persistToFile(FileService fileService, Object ctx, StaticMetadataObject obj) {
		// TODO
		println "Persisting " + obj.fname()
	}

	public static void checkRows(FileService fileService, String baseCtxPath) {
		def staticCtx = fileService.createContext(baseCtxPath, "rifcs")
		fileService.getFileReference(staticCtx, ".").mkdirs()
		StaticMetadataObject.list().each { it ->
			File file = fileService.getFileReference(staticCtx, it.fname())
			if (!file.exists() || file.lastModified() < it.lastUdpated.getTime()) {
				persistToFile(fileService, staticCtx, it)
			}
		}
	}
	
	private String fname()
	{
		return StaticMetadataObject.FILEPREFIX + "~" + it.id + ".xml";
	}

}
