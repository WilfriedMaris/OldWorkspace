package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
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
import be.vdab.repository.CultuurhuisRepository;

@WebServlet("/nieuweKlant.htm")
public class NieuweKlantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/nieuweKlant.jsp";
	private static final String REDIRECT_URL = "%s/bevestigingReservatie.htm";
	private transient final CultuurhuisRepository cultuurhuisRepository = new CultuurhuisRepository();
	
	@Resource(name=CultuurhuisRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		cultuurhuisRepository.setDataSource(dataSource);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Map<String,String> input = new LinkedHashMap<>();
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			if(name.equals("ok")){
				break;
			}
			input.put(name, request.getParameter(name));
		}
		List<String> fouten = new ArrayList<>();
		for(String key : input.keySet()){
			if(input.get(key).isEmpty()){
				String fout = key + " niet ingevuld.";
				fouten.add(fout);
			}
		}
		if(cultuurhuisRepository.existGebruikersnaam(input.get("Gebruikersnaam"))){
			fouten.add("Deze gebruikersnaam is reeds in gebruik.");
		}
		if(!input.get("Paswoord").equals(input.get("Herhaal paswoord"))){
			fouten.add("Het paswoord en het herhaalde paswoord zijn verschillend.");
		}
		if(!fouten.isEmpty()){
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}else{
			cultuurhuisRepository.makeKlant(input);
			Klant klant = cultuurhuisRepository.findKlant(input.get("Gebruikersnaam"), input.get("Paswoord")).get();
			session.setAttribute("klant",klant);
			response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath())));
		}
	}
}
