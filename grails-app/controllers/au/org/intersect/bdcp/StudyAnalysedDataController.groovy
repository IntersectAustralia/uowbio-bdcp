package au.org.intersect.bdcp

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

import grails.plugins.springsecurity.Secured

import au.org.intersect.bdcp.rifcs.Rifcs
import au.org.intersect.bdcp.ldap.LdapUser

class StudyAnalysedDataController
{
	
	def rifcsService
	
	def roleCheckService
	
	def getContextRootPath(def servletRequest)
	{
		return servletRequest.getSession().getServletContext().getRealPath("/")
	}
	

	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}


	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		[studyInstance: studyInstance]
	}

}
