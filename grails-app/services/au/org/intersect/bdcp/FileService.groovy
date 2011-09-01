package au.org.intersect.bdcp

import java.io.File

import org.apache.commons.io.FileUtils

class FileService
{

    static transactional = true

    def grailsApplication

    def createContext(def webAppContextPath, String property)
    {
        def tmpPath = new File(webAppContextPath,grailsApplication.config.tmp.location.toString())
        def rootPath = new File(webAppContextPath,grailsApplication.config.files."${property}".location.toString())
        if (!tmpPath.exists())
        {
            tmpPath.mkdirs()
        }
        if (!rootPath.exists())
        {
            rootPath.mkdirs()
        }
        return ["tmpPath":tmpPath, "rootPath":rootPath]
    }

    def listFiles(def context, def path)
    {
        def top = new File(context.get("rootPath"), path)
        return [files: top.listFiles(), sessionRoot: top]
    }

    private boolean createAllFolders(def context, def json, def destination)
    {
        return json.every { topLevel -> 
			return topLevel.findAll({
	            p,q ->
	            p.startsWith("folder")
	        }).every {
	            key, val ->
	            def path = val
	            def tmpDir = new File(context.get("tmpPath"), destination)
	            def directory = new File(tmpDir, path)
	            return checkPathIsValid(context.get("tmpPath"), directory) && ((directory.exists() && directory.isDirectory()) || directory.mkdirs());
	        }
        }
    }

    private boolean createAllFiles(def context, def json, def destination, def parameters)
    {
        if (parameters == null)
        {
            return false
        }
        return json.every { topLevel -> 
			return topLevel.findAll({
	            p,q ->
	            p.startsWith("file")
	        }).every{
	            key, val ->
	            def path = val
	            def file = parameters[key]
	            def tmpDir = new File(context.get("tmpPath"), destination )
	            def filepath = new File(tmpDir, path)
	            if (!checkPathIsValid(context.get("tmpPath"), filepath))
	            {
	                return false;
	            }
	            filepath.mkdirs()
	            file.transferTo(filepath)
	            return true
	        }
        }
    }


    private boolean moveDirectoryFromTmp(def context, def previous, def destination)
    {
        File oldDir = new File(context.get("tmpPath"), previous)
        if (!checkPathIsValid(context.get("tmpPath"), oldDir))
        {
            return false
        }
        File newDir = new File(context.get("rootPath"), destination).getParentFile()
        if (!checkPathIsValid(context.get("rootPath"), newDir))
        {
            return false;
        }

        try
        {
            FileUtils.copyDirectoryToDirectory(oldDir, newDir)
            FileUtils.deleteDirectory(oldDir)
            return true
        }
        catch (IOException ioException)
        {
            return false
        }
    }

    def boolean checkIfDirectoryExists(def context, String name, String path)
    {
        File directoryPath = new File(context.get("rootPath"), path)
        File directory = new File(directoryPath, name)
        return directory.exists() || ((directory?.listFiles() == null || directory?.listFiles()?.isEmpty())? false: directoryPath?.listFiles().every {
            it.getName().compareToIgnoreCase(name) == 0
        })
    }

    def boolean createDirectory(def context, String name, String path)
    {
        File directoryPath = new File(context.get("rootPath"), path)
        File directory = new File(directoryPath, name)

        return checkPathIsValid(context.get("rootPath"), directory) && directory.getParentFile().exists() && directory.mkdir()
    }

    def boolean checkPathIsValid(File rootPath, File path)
    {
        return rootPath.isDirectory() && path.getAbsolutePath().startsWith(rootPath.getAbsolutePath())
    }
	
	def File getFileReference(def context, String path)
	{
        return new File(context.get("rootPath"), path)
	}
}
