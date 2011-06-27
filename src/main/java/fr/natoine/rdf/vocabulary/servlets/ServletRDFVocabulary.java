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
package fr.natoine.rdf.vocabulary.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.natoine.dao.annotation.DAOAnnotation;
import fr.natoine.dao.annotation.DAOPost;
import fr.natoine.model_annotation.AnnotationStatus;
import fr.natoine.model_annotation.PostStatus;

public class ServletRDFVocabulary extends HttpServlet  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7306595491777211720L;
	
	private static DAOAnnotation daoAnnotation = null ; 
	private static DAOPost daoPost = null ;
	private static EntityManagerFactory emf_annotation = null ;
	
	public ServletRDFVocabulary() 
    {
        super();
        emf_annotation = Persistence.createEntityManagerFactory("annotation");
        daoAnnotation = new DAOAnnotation(emf_annotation);
        daoPost = new DAOPost(emf_annotation);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<PostStatus> post_status = daoPost.retrieveAllPostStatus();
		List<String> rdf_post_status = new ArrayList<String>();
		for(PostStatus status : post_status) 
		{
			rdf_post_status.add(status.toRDF(request.getRequestURL().toString()));
		}
		request.setAttribute("rdf_post_status", rdf_post_status);
		
		List<AnnotationStatus> annotation_status = daoAnnotation.retrieveAnnotationStatus();
		List<String> rdf_annotation_status = new ArrayList<String>();
		for(AnnotationStatus status : annotation_status) 
		{
			rdf_annotation_status.add(status.toRDF(request.getRequestURL().toString()));
		}
		request.setAttribute("rdf_annotation_status", rdf_annotation_status);
		
		response.setContentType("text/xml");
		RequestDispatcher _srd = this.getServletContext().getRequestDispatcher(response.encodeURL("/WEB-INF/jsp/servletRDFVocabulary/view.jsp"));
		_srd.include(request, response);
	}
}