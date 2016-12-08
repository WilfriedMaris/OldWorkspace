package be.vdab.servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Adres;
import be.vdab.entities.Gemeente;

@WebServlet(urlPatterns = "/index.htm",name = "indexservlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("telefoonNummerHelpDesk",
				request.getServletContext().getInitParameter("telefoonNummerHelpDesk"));
		int dag = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		request.setAttribute("openGesloten", dag == Calendar.MONDAY || dag == Calendar.THURSDAY ?
				"gesloten" : "open");
		request.setAttribute("adres", new Adres("Kempenlaan", "15", new Gemeente("Genk", 3600)));
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
