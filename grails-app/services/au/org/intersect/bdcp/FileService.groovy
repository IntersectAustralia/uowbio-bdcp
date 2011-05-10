package au.org.intersect.bdcp

import java.io.File

import org.apache.commons.io.FileUtils

class FileService {

    static transactional = true

    def grailsApplication
    
    def createContext(def webAppContextPath) {
        def tmpPath = new File(webAppContextPath,grailsApplication.config.tmp.location.toString())
        def rootPath = new File(webAppContextPath,grailsApplication.config.files.location.toString())
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
        // Create all folders
        json.findAll { p,q ->
            p.startsWith("folder")
        }.each
        { key, val ->
            def path = val[0]
            def tmpDir = new File(context.get("tmpPath"), destination)
            def directory = new File(tmpDir, path)
            if (!checkPathIsValid(context.get("tmpPath"), directory))
            {
                return false
            }
            if (!directory.exists())
            {
                directory.mkdirs()
            }
        }
        return true;
    }
    
    private boolean createAllFiles(def context, def json, def destination, def parameters)
    {
        if (parameters == null)
        {
            return false
        }
        // Create all files
        json.findAll { p,q ->
            p.startsWith("file")
        }.each
        { key, val ->
            def path = val[0]
            def file = parameters[key]
            def tmpDir = new File(context.get("tmpPath"), destination )
            def filepath = new File(tmpDir, path)
            if (!checkPathIsValid(context.get("tmpPath"), filepath))
            {
                return false
            }
            filepath.mkdirs()
            file.transferTo(filepath)
        }
        return true
    }
    
    
    private boolean moveDirectory(def context, def previous, def destination)
    {
        File oldDir = new File(context.get("tmpPath"), previous)
        if (!checkPathIsValid(context.get("tmpPath"), oldDir))
        {
            return false
        }
        File newDir = new File(context.get("rootPath"), destination)
        if (!checkPathIsValid(context.get("rootPath"), newDir))
        {
            return false;
        }
        
        try
        {
            FileUtils.copyDirectoryToDirectory(oldDir, newDir)
            FileUtils.deleteDirectory(oldDir)
        }
        catch (IOException ioException)
        {
            return false
        }
        return true
    }

    def boolean checkIfDirectoryExists(def context, String name, String path)
    {
        File directoryPath = new File(context.get("rootPath"), path)
        File directory = new File(directoryPath, name)
        return directory.exists()
    }
    
    def boolean createDirectory(def context, String name, String path) 
    {
            File directoryPath = new File(context.get("rootPath"), path)
            File directory = new File(directoryPath, name)
            
            if (checkPathIsValid(context.get("rootPath"), directory))
            {
                if (!directory.getParentFile().exists())
                {
                    return false
                }    
                return directory.mkdir()
            }
            return false
    }
    
    def boolean checkPathIsValid(File rootPath, File path)
    {
        if (rootPath.isDirectory())
        {
            File pathDir = new File(rootPath.getAbsolutePath(),path.getAbsolutePath())
            if (pathDir.getAbsolutePath().startsWith(rootPath.getAbsolutePath()))
            {
                return true
            }
        }
        return false
    }
    
}
