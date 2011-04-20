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
//	Resource _resource = (Resource)request.getAttribute("resource");
	List<String> _namespaces = (List<String>)request.getAttribute("headers");//_resource.rdfHeader();
	String _url_nms_jeu2debat = request.getRequestURL().toString();
	_url_nms_jeu2debat = _url_nms_jeu2debat.substring(0 , _url_nms_jeu2debat.lastIndexOf("/") + 1);
	_url_nms_jeu2debat = _url_nms_jeu2debat.concat("ServletRDFVocabulary");
//	String _url_view_resources = request.getRequestURL().toString() ;
//	String _url_view_agents = _url_view_resources.substring(0 , _url_view_resources.lastIndexOf("/") + 1).concat("ServletViewRDFAgents");
	String rdf = (String)request.getAttribute("rdf");
%>
<rdf:RDF
	xmlns:jeu2debat="<%=_url_nms_jeu2debat%>"
<%	
	for(String _namespace : _namespaces)
	{
		%>
		<%=_namespace%>
		<%
	}
%>
>
<%=rdf%>
</rdf:RDF>