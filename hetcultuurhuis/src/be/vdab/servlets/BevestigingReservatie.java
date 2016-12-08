package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import be.vdab.entities.Klant;
import be.vdab.entities.Reservatie;
import be.vdab.repository.CultuurhuisRepository;

@WebServlet("/bevestigingReservatie.htm")
public class BevestigingReservatie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  static final String VIEW = "/WEB-INF/JSP/bevestigingReservatie.jsp";
	private final static String REDIRECT_NIEUWEKLANT = "%s/nieuweKlant.htm";
	private final static String REDIRECT_OVERZICHT = "%s/overzicht.htm";
	private transient final CultuurhuisRepository cultuurhuisRepository = new CultuurhuisRepository();
	
	@Resource(name=CultuurhuisRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		cultuurhuisRepository.setDataSource(dataSource);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("zoek") != null) {
			zoeken(request, response);
		}
		if (request.getParameter("nieuw") != null) {
			response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_NIEUWEKLANT, request.getContextPath())));
		}
		if(request.getParameter("bevestigen") != null){
			bevestigen(request, response);
		}
	}
	
	private void zoeken(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		String gebruikersNaam = request.getParameter("gebruikersnaam");
		String paswoord = request.getParameter("paswoord");
		if(cultuurhuisRepository.findKlant(gebruikersNaam,paswoord).isPresent()){
			Klant klant = cultuurhuisRepository.findKlant(gebruikersNaam,paswoord).get();
			session.setAttribute("klant", klant);
		}else{
			request.setAttribute("fout", "Verkeerde gebruikersnaam of paswoord.");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	
	private void bevestigen(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(false);
		Klant klant = (Klant) session.getAttribute("klant");
		@SuppressWarnings("unchecked")
		Map<Integer,Integer> mandje = (Map<Integer,Integer>) session.getAttribute("mandje");
		Map<Integer,Integer> mislukt = new LinkedHashMap<>();
		Map<Integer,Integer> gelukt = new LinkedHashMap<>();
		for(int key : mandje.keySet()){
			if(cultuurhuisRepository.findVoorstelling(key).getVrijePlaatsen() > mandje.get(key)){
				cultuurhuisRepository.addReservatie(new Reservatie(klant.getKlantNr(), key, mandje.get(key)));
				gelukt.put(key, mandje.get(key));
			}else{
				mislukt.put(key, mandje.get(key));
			}	
		}
		session.removeAttribute("mandje");
		session.removeAttribute("klant");
		session.setAttribute("gelukt", gelukt);
		session.setAttribute("mislukt", mislukt);
		response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_OVERZICHT, request.getContextPath())));
	}

}
