<%
String dir = params?.dir
if (dir == null) {
    return;
}
/* you can put here your own custom check with dir variable to protect from malicious request.  */
if(!dir.endsWith(File.separator)){
    dir+=File.separator
}

File f=new File(dir)
if (f.exists()) {
    List<File> files=[]

    f.eachFile { File file->
        if(!file.hidden)
            files<<file
    }

    files.sort{File file-> file.name.toUpperCase()}
    StringBuffer output=new StringBuffer('<ul class="jqueryFileTree" style="display: none;">')
    // All dirs
    files.each{File file->
        if(file.directory){
            output.append("""<li class="directory collapsed"><a href="#" rel="${dir+file.name+File.separator}">${file.name}</a></li>""")
        }
    }
    // All files
    files.each{File file->
        if(file.file){
            int dotIndex = file.name.lastIndexOf('.');
            String ext = dotIndex > 0 ? file.name.substring(dotIndex + 1) : "";
            output.append("""<li class="file ext_${ext}"><a href="#" rel="${dir+file.name}">${file.name}</a></li>""");
        }
    }
    output.append("</ul>");
    println output.toString()
}
%>