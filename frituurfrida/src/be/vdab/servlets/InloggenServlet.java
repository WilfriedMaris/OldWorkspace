package be.vdab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/inloggen.htm")
public class InloggenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/inloggen.jsp";
	private static final String REDIRECT_URL = "%s/gastenboek.htm";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!this.getServletContext().getInitParameter("paswoord").equals(request.getParameter("paswoord"))) {
			request.setAttribute("fout", "verkeerd");
			request.getRequestDispatcher(VIEW).forward(request, response);
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("beheer", request.getParameter("paswoord"));
			response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath())));
		}
	}
}