dataSource
{
	pooled = true
	driverClassName = "org.postgresql.Driver"
}
hibernate
{
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development
	{
		dataSource
		{
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			driverClassName = "org.hsqldb.jdbcDriver"
			//url = "jdbc:hsqldb:mem:devDB"
			url = "jdbc:hsqldb:file:devDB;shutdown=true"
			username = "sa"
			password = ""
		}
	}
	test
	{
		dataSource
		{
			dbCreate = "create-drop"
			url = "jdbc:postgresql://localhost:5432/bdcp-test"
			driverClassName = "org.postgresql.Driver"
			username = "bdcp"
			password = "bdcp"
		}
	}
	production
	{
		dataSource
		{
			dbCreate = "create-drop" //"update"
			url = "jdbc:postgresql://localhost:5432/bdcp-prod"
			driverClassName = "org.postgresql.Driver"
			username = "bdcp"
			password = "bdcp"
		}
	}
	intersect_test
	{
		dataSource
		{
			dbCreate = "create-drop"
			url = "jdbc:postgresql://localhost:5432/bdcp-prod"
			driverClassName = "org.postgresql.Driver"
			username = "bdcp"
			password = "bdcp"
		}
	}
}
