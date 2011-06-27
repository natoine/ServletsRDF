package fr.natoine.rss.servlets;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.natoine.dao.resource.DAOResource;
import fr.natoine.model_annotation.Annotation;
import fr.natoine.model_resource.Resource;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;

public class ServletAllRSS extends HttpServlet 
{
	
	private static EntityManagerFactory emf_annotation = null ;
	//private static DAOAnnotation daoAnnotation = null ;
	private static DAOResource daoResource = null ;
	
	 /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAllRSS() 
    {
        super();
        emf_annotation = Persistence.createEntityManagerFactory("annotation");
        //daoAnnotation = new DAOAnnotation(emf_annotation);
        daoResource = new DAOResource(emf_annotation);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		getRSSData(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		getRSSData(request, response);
	}
	
	private void getRSSData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
                String domain = "http://".concat(request.getRequestURL().toString().split("/", 4)[2]);
                //TODO passer en static final les String et trouver une meilleure solution que -1.0.0
                String url_resource_rdf = domain.concat("/ServletsRDF-1.0.0/ServletViewRDFResources");
                String url_resource_html = domain.concat("/PortletAnnotation-1.0.0/ServletViewResources");
                
		List<Resource> resources = daoResource.retrieveAllResources();
                ArrayList<String> rss_liste_resources = new ArrayList<String>();
                ArrayList<String> rss_items = new ArrayList<String>();
                for (Resource resource : resources)
                {
                    rss_liste_resources.add("<rdf:li resource=\"" + url_resource_rdf + "?id=" + resource.getId() + "\" />");
                    rss_items.add(resource.toRSS(url_resource_rdf, url_resource_html, ""));
                }
                request.setAttribute("rss_liste_resources" , rss_liste_resources);
                request.setAttribute("rss_items" , rss_items);
                response.setContentType("text/xml");
		RequestDispatcher _srd = this.getServletContext().getRequestDispatcher(response.encodeURL("/WEB-INF/jsp/servletAllRSS/rss.jsp"));
		_srd.include(request, response);
	}
}
