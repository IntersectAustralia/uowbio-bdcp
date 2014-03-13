eventCompileStart = { msg ->
    new File("grails-app/views/_version.gsp").text = "C:\\programs\\Git\\bin\\git describe --tags --long".execute().text
}
