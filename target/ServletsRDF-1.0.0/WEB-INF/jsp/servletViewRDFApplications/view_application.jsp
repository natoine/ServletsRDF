<?xml version="1.0" encoding="UTF-8"?>
<%@ 
	page language="java" 
	import="javax.servlet.*"
	import="java.util.List"
	import="java.util.Collection"
	contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%
	//Application _application = (Application)request.getAttribute("application");
	String rdf = (String)request.getAttribute("rdf");
	List<String> _namespaces = (List<String>)request.getAttribute("headers");//_application.rdfHeader();
%>
<rdf:RDF
<%	
	for(String _namespace : _namespaces)
	{
		%>
		<%=_namespace%>
		<%
	}
%>
>
<%=rdf %>
</rdf:RDF>