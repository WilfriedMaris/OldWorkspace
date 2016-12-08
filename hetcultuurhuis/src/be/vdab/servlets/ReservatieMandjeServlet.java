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

@WebServlet("/reservatiemandje.htm")
public class ReservatieMandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/reservatieMandje.jsp";
	private static final String REDIRECT_URL = "%s/reservatiemandje.htm";
	private final transient CultuurhuisRepository cultuurhuisRepository = new CultuurhuisRepository();

	@Resource(name=CultuurhuisRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		cultuurhuisRepository.setDataSource(dataSource);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session != null){
			@SuppressWarnings("unchecked")
			Map<Integer,Integer> mandje = (Map<Integer,Integer>) session.getAttribute("mandje");
			if(!mandje.isEmpty()){
				Set<Voorstelling> voorstellingInMandje = new LinkedHashSet<>();
				mandje.keySet().forEach(key -> voorstellingInMandje.add(cultuurhuisRepository.findVoorstelling(key)));
				request.setAttribute("voorstellingen", voorstellingInMandje);
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session != null){
			@SuppressWarnings("unchecked")
			Map<Integer,Integer> mandje = (Map<Integer,Integer>) session.getAttribute("mandje");
			if(mandje != null){
				String[] values = request.getParameterValues("verwijdercheck");
				if(values != null){
					for(String value : values){
						int voorstellingsNr = Integer.parseInt(value);
						if(mandje.containsKey(voorstellingsNr)){
							mandje.remove(voorstellingsNr);
						}
					}					
				}
			}
		}
		response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath())));
	}	
	
}

