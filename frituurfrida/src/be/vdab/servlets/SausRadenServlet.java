package be.vdab.servlets;

import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Saus;
import be.vdab.entities.SausRaden;
import be.vdab.repositories.SausRepository;

@WebServlet("/sausraden.htm")
public class SausRadenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/sausraden.jsp";
	private static final String SPEL = "spel";
	private static transient final SausRepository sausRepository = new SausRepository();
	
	@Resource(name=SausRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		sausRepository.setDataSource(dataSource);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(SPEL) == null){
			int AANTAL = sausRepository.findAll().size();
			int GEKOZEN = new Random().nextInt(AANTAL); 
			Saus saus = sausRepository.findAll().get(GEKOZEN);
			session.setAttribute(SPEL, new SausRaden(saus));
		}
		request.getRequestDispatcher(VIEW).forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (request.getParameter("nieuwspel") != null){
			session.removeAttribute(SPEL);
		}
		else{
			String letter = request.getParameter("letter");
			SausRaden spel = (SausRaden) session.getAttribute(SPEL);
			if (spel != null){
				if (spel.getSaus().contains(letter)){
				spel.replacePuntjes(letter);
				}
			}
			session.setAttribute(SPEL, spel);	
		}
		response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
	}
	
}
