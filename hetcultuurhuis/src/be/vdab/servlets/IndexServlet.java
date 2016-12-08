package be.vdab.servlets;

import java.io.IOException;
import java.util.Optional;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import be.vdab.repository.CultuurhuisRepository;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private final transient CultuurhuisRepository cultuurhuisRepository = new CultuurhuisRepository();
	
	@Resource(name=CultuurhuisRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		cultuurhuisRepository.setDataSource(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<String> id = Optional.ofNullable(request.getParameter("id"));
		//lambda:
		id.ifPresent(genreNr -> {request.setAttribute("gekozenGenre", cultuurhuisRepository.findGenre(genreNr));
		request.setAttribute("voorstellingen", cultuurhuisRepository.findAllVoorstellingen(genreNr));});
		//
		request.setAttribute("genres", cultuurhuisRepository.findAllGenres());
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
