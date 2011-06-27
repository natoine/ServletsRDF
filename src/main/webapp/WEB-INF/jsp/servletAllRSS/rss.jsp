<?xml version="1.0" encoding="UTF-8"?>
<%@
	page language="java"
	import="javax.servlet.*"
        import="java.util.List"
        contentType="text/xml; charset=UTF-8"
        pageEncoding="UTF-8"
%>
<%
    List<String> rss_liste_resources = (List<String>)request.getAttribute("rss_liste_resources");
    List<String> rss_items = (List<String>)request.getAttribute("rss_items");
%>

<rdf:RDF
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
  xmlns="http://purl.org/rss/1.0/"
>

    <channel rdf:about="<%=request.getRequestURL().toString()%>">
    <title>title du site</title>
    <link>url du site</link>
    <description>
      bla bla bla.
    </description>

    <items>
      <rdf:Seq>
          <%
          for(String rss_liste_resource : rss_liste_resources)
          {
           %>
           <%=rss_liste_resource%>
           <%
          }
          %>
      </rdf:Seq>
    </items>

  </channel>

      <%
      for(String rss_item : rss_items)
      {
          %>
          <%=rss_item%>
          <%
      }
      %>

</rdf:RDF>