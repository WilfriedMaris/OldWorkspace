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
import be.vdab.repository.CultuurhuisRepository;

@WebServlet("/reserveren.htm")
public class ReserverenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reserveren.jsp";
	private static final String REDIRECT_URL = "%s/reservatiemandje.htm";
	private transient final CultuurhuisRepository cultuurhuisRepository = new CultuurhuisRepository();
	
	@Resource(name=CultuurhuisRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		cultuurhuisRepository.setDataSource(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int voorstellingsNr = Integer.parseInt(request.getParameter("voorstellingsNr"));
		request.setAttribute("voorstelling",cultuurhuisRepository.findVoorstelling(voorstellingsNr));
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int voorstellingsNr = Integer.parseInt(request.getParameter("voorstellingsNr"));
		int plaatsen = Integer.parseInt(request.getParameter("teReserveren"));
//		Onderstaande kan ook vervangen worden door min en max in JSP type number
		int vrijePlaatsen = cultuurhuisRepository.findVoorstelling(voorstellingsNr).getVrijePlaatsen();
		if(plaatsen < 0 || plaatsen > vrijePlaatsen){
			request.setAttribute("fout", "Tik een getal tussen 1 en " + vrijePlaatsen + ".");
			request.setAttribute("voorstelling",cultuurhuisRepository.findVoorstelling(voorstellingsNr));
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
//
		@SuppressWarnings("unchecked")
		Map<Integer,Integer> mandje = (Map<Integer,Integer>) session.getAttribute("mandje");
		if(mandje == null){
			mandje = new LinkedHashMap<Integer,Integer>();
		}
		if(mandje.containsKey(voorstellingsNr)){
			mandje.remove(voorstellingsNr);
		}
		mandje.put(voorstellingsNr,plaatsen);
		session.setAttribute("mandje", mandje);
		response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath())));
	}
}

