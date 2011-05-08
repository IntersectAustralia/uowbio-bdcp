package au.org.intersect.bdcp

import java.io.File

import org.apache.commons.io.FileUtils

class FileService {

    static transactional = true

    def grailsApplication
    
    
    
    def parameters = [:]
    
    def createContext(webAppContextPath) {
        def tmpPath = new File(webAppContextPath,grailsApplication.config.tmp.location.toString())
        def rootPath = new File(webAppContextPath,grailsApplication.config.files.location.toString())
        return [tmpPath:tmpPath, rootPath:rootPath]
    }
    
    private boolean createAllFolders(context, json, destination)
    {
        // Create all folders
        json.findAll { p,q ->
            p.startsWith("folder")
        }.each
        { key, val ->
            def path = val[0]
            def tmpDir = new File(context.get("tmpPath"), destination)
            def directory = new File(tmpDir, path)
            if (!directory.exists())
            {
                directory.mkdirs()
            }
        }
        return true;
    }
    
    private void setParams(params)
    {
        parameters = params
    }
    
    private boolean createAllFiles(def context, def json, def destination)
    {
        // Create all files
        json.findAll { p,q ->
            p.startsWith("file")
        }.each
        { key, val ->
            def path = val[0]
            def file = parameters[key]
            def tmpDir = new File(context.get("tmpPath"), destination )
            def filepath = new File(tmpDir, path)
            filepath.mkdirs()
            file.transferTo(filepath)
        }
        return true
    }
    
    
    private boolean moveDirectory(context, previous, destination)
    {
        File oldDir = new File(context.get("tmpPath"), previous)
        File newDir = new File(context.get("rootPath"), destination)
        
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

    def createDirectory(String name, String path) 
    {
        
    }
    
    
}
