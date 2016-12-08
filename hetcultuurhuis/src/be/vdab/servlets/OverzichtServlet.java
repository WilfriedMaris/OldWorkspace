package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import be.vdab.entities.Voorstelling;
import be.vdab.repository.CultuurhuisRepository;

@WebServlet("/overzicht.htm")
public class OverzichtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/overzicht.jsp";
	private final transient CultuurhuisRepository cultuurhuisRepository = new CultuurhuisRepository();
	
	@Resource(name=CultuurhuisRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		cultuurhuisRepository.setDataSource(dataSource);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<Integer,Integer> gelukt = (Map<Integer,Integer>) session.getAttribute("gelukt");
		@SuppressWarnings("unchecked")
		Map<Integer,Integer> mislukt = (Map<Integer,Integer>) session.getAttribute("mislukt");
		Set<Voorstelling> voorstellingInMandje = new LinkedHashSet<>();
		gelukt.keySet().forEach(key -> 
		voorstellingInMandje.add(cultuurhuisRepository.findVoorstelling(key)));
		mislukt.keySet().forEach(key -> 
		voorstellingInMandje.add(cultuurhuisRepository.findVoorstelling(key)));
		request.setAttribute("gelukt", gelukt);
		request.setAttribute("mislukt", mislukt);
		request.setAttribute("voorstellingen", voorstellingInMandje);
		session.invalidate();
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
