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
package fr.natoine.rdf.viewApplications.servlets;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.natoine.controler.user.DAOUser;
import fr.natoine.model_user.Application;

public class ServletViewRDFApplications extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095381700033329938L;

	private static EntityManagerFactory emf_user = null ;
	private static DAOUser daoUser = null ;
	
	public ServletViewRDFApplications()
	{
		super();
		emf_user = Persistence.createEntityManagerFactory("user");
		daoUser = new DAOUser(emf_user);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getParameter("id")!=null) //retrouver le bon agent
		{
			String _id_string = request.getParameter("id") ;
			long id = Long.parseLong(_id_string);
			Application _to_view = daoUser.retrieveApplication(id);
			if(_to_view.getId() != null)
			{
				//afficher la ressource
				//request.setAttribute("application", _to_view);
				request.setAttribute("rdf", _to_view.toRDF(request.getRequestURL().toString() , ""));
				request.setAttribute("headers", _to_view.rdfHeader());
				response.setContentType("text/xml");
				RequestDispatcher _srd = this.getServletContext().getRequestDispatcher(response.encodeURL("/WEB-INF/jsp/servletViewRDFApplications/view_application.jsp"));
				_srd.include(request, response);
			}
			else
			{
				//pas un id valide
				response.setContentType("text/html");
				RequestDispatcher _srd = this.getServletContext().getRequestDispatcher(response.encodeURL("/WEB-INF/jsp/servletViewRDFApplications/not_valid_id.jsp"));
				_srd.include(request, response);
			}
		}
	}
}