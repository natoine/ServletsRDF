<?xml version="1.0" encoding="UTF-8"?>
<%@ 
	page language="java" 
	import="javax.servlet.*"
	import="java.util.List"
	contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<rdf:RDF
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
  	xmlns:owl="http://www.w3.org/2002/07/owl#"
>
<!-- Mood, domain, judgment -->
<owl:Class rdf:about="<%=request.getRequestURL().toString()%>#Mood">
<rdfs:label xml:lang="fr">Mood</rdfs:label>
<rdfs:comment xml:lang="fr">Tag exprimant une humeur</rdfs:comment>
<rdfs:subClassOf rdf:resource="http://moat-project.org/ns#Tag"/>
<rdfs:isDefinedBy rdf:resource="<%=request.getRequestURL().toString()%>#"/>
</owl:Class>
<owl:Class rdf:about="<%=request.getRequestURL().toString()%>#Domain">
<rdfs:label xml:lang="fr">Domain</rdfs:label>
<rdfs:comment xml:lang="fr">Tag relatif à un domaine, une grande catégorie (économie, physique, biologie ...)</rdfs:comment>
<rdfs:subClassOf rdf:resource="http://moat-project.org/ns#Tag"/>
<rdfs:isDefinedBy rdf:resource="<%=request.getRequestURL().toString()%>#"/>
</owl:Class>
<owl:Class rdf:about="<%=request.getRequestURL().toString()%>#Judgment">
<rdfs:label xml:lang="fr">Judgment</rdfs:label>
<rdfs:comment xml:lang="fr">Tag exprimant un jugement de valeur, permettant de préciser une position (je suis d'accord, je ne suis pas d'accord, j'aime ...)</rdfs:comment>
<rdfs:subClassOf rdf:resource="http://moat-project.org/ns#Tag"/>
<rdfs:isDefinedBy rdf:resource="<%=request.getRequestURL().toString()%>#"/>
</owl:Class>
<!-- Definition -->
<owl:Class rdf:about="<%=request.getRequestURL().toString()%>#Definition">
<rdfs:label xml:lang="fr">définition</rdfs:label>
<rdfs:comment xml:lang="fr">une simple définition</rdfs:comment>
<rdfs:subClassOf rdf:resource="http://rdfs.org/sioc/ns#Post"/>
<rdfs:isDefinedBy rdf:resource="<%=request.getRequestURL().toString()%>#"/>
</owl:Class>
<!-- Others postStatus -->
<%
	List<String> post_status = (List<String>)request.getAttribute("rdf_post_status");
	if(post_status != null && post_status.size()>0)
	{
		for(String status : post_status)
		{
			%>
			<%=status%>
			<%
		}
	}
	%>
	<!-- AnnotationStatus -->
	<%
	List<String> annotation_status = (List<String>)request.getAttribute("rdf_annotation_status");
	if(annotation_status != null && annotation_status.size()>0)
	{
		for(String status : annotation_status)
		{
			%>
			<%=status%>
			<%
		}
	}
%>
<!-- Properties -->
</rdf:RDF>