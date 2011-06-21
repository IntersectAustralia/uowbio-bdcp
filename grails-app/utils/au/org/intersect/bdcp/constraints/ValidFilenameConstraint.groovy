package au.org.intersect.bdcp.constraints

class ValidFilenameConstraint
{
    static name = "validFilename"
    static defaultMessageCode = "default.invalid.filename.message"
    static failureCode = "invalid.characters"

    def supports = { type ->
        return type!= null && String.class.isAssignableFrom(type);
    }

    static persistent = false

    def pattern = ~"[/\\?%*:|\"<>]"

    def validate = { propertyValue -> 
	def matcher = propertyValue =~ pattern
	return !matcher.find()        
    }



}
