/*
 * Copyright 2010 Antoine Seilles (Natoine)
 *   This file is part of ServletsRDF.

    PortletUserAccount is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    PortletUserAccount is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with ServletsRDF.  If not, see <http://www.gnu.org/licenses/>.

 */
package fr.natoine.rdf.viewResources.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.natoine.dao.annotation.DAOAnnotation;
import fr.natoine.dao.resource.DAOResource;
import fr.natoine.model_annotation.Annotation;
import fr.natoine.model_resource.Resource;

public class ServletViewRDFResources extends HttpServlet 
{
	private static final long serialVersionUID = 6621178207276093302L;

	private static EntityManagerFactory emf_annotation = null ;
	private static DAOAnnotation daoAnnotation = null ;
	//private static DAOResource daoResource = null ;
	
	 /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletViewRDFResources() 
    {
        super();
        emf_annotation = Persistence.createEntityManagerFactory("annotation");
        daoAnnotation = new DAOAnnotation(emf_annotation);
        //daoResource = new DAOResource(emf_annotation);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getParameter("id")!=null) //retrouver la bonne ressource
		{
			String _id_string = request.getParameter("id") ;
			long id = Long.parseLong(_id_string);
			Resource _to_view = daoAnnotation.retrieveResource(id);
			if(_to_view.getId() != null)
			{
				//Préparer les urls nécessaires
				String url_resources = request.getRequestURL().toString();
				String _url_view_agents = url_resources.substring(0 , url_resources.lastIndexOf("/") + 1).concat("ServletViewRDFAgents");				
				
				//Récupérer les annotations faites sur une ressource créée chez nous
				String url_to_query = _to_view.getRepresentsResource().getEffectiveURI() + "?id=" + id ;//url_resources + "?id=" + id ;
				//System.out.println("url resource : " + url_to_query);
				String rdf_toinject = "";
				ArrayList<Annotation> annotations = new ArrayList<Annotation>();
				annotations.addAll(daoAnnotation.retrieveAnnotations(url_to_query));
				//Récupérer les annotations faites sur une ressource d'ailleurs sur le web
				url_to_query = _to_view.getRepresentsResource().getEffectiveURI();
				annotations.addAll(daoAnnotation.retrieveAnnotations(url_to_query));
				if(annotations.size() > 0)
				{
					rdf_toinject = rdf_toinject.concat("<annotea:hasAnnotation>");
					for(Annotation annotation : annotations) rdf_toinject = rdf_toinject.concat(annotation.toSeeAlso(url_resources));
					rdf_toinject = rdf_toinject.concat("</annotea:hasAnnotation>");
				}
                                //RDF de la ressource
				String rdf = _to_view.toRDF(url_resources , _url_view_agents , rdf_toinject);
				request.setAttribute("rdf", rdf);
				request.setAttribute("headers" , _to_view.rdfHeader());
				
				response.setContentType("text/xml");
				RequestDispatcher _srd = this.getServletContext().getRequestDispatcher(response.encodeURL("/WEB-INF/jsp/servletViewRDFResources/view_resource.jsp"));
				_srd.include(request, response);
			}
			else
			{
				//pas un id valide
				response.setContentType("text/html");
				RequestDispatcher _srd = this.getServletContext().getRequestDispatcher(response.encodeURL("/WEB-INF/jsp/servletViewRDFResources/not_valid_id.jsp"));
				_srd.include(request, response);
			}
		}
	}
}