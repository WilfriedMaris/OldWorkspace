package be.vdab.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("*.htm")
public class StatistiekFilter implements Filter {
	private static final String STATISTIEK = "statistiek";
	private final static Set<String> UITGESLOTEN_EXTENSIES = 
			new CopyOnWriteArraySet<>(
			Arrays.asList("png", "gif", "jpg", "css", "js", "ico"));

    public StatistiekFilter() {
    }


	public void destroy() {
	}

	public void doFilter(
			ServletRequest req, ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = 
					(HttpServletRequest) req;
			String url = request.getRequestURI();
			boolean verwerkRequest = true;
			int laatstePuntIndex = url.lastIndexOf('.');
			if (laatstePuntIndex != -1) {
				int index = url.indexOf(";jsessionid=");
				if (index != -1) {
					url = url.substring(0, index);
				}
				String extensie = url.substring(laatstePuntIndex + 1)
						.toLowerCase();
				if (UITGESLOTEN_EXTENSIES.contains(extensie)) {
					verwerkRequest = false;
				}
			}
			if (verwerkRequest) {
				@SuppressWarnings("unchecked")
				ConcurrentHashMap<String, AtomicInteger> statistiek = 
				(ConcurrentHashMap<String, AtomicInteger>) request
						.getServletContext().getAttribute(STATISTIEK);
				AtomicInteger aantalReedsAanwezig = 
						statistiek.putIfAbsent(url, new AtomicInteger(1));
				if (aantalReedsAanwezig != null) {
					aantalReedsAanwezig.incrementAndGet();
				}
			}
		}
		chain.doFilter(req, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		fConfig.getServletContext().setAttribute(STATISTIEK, 
		new ConcurrentHashMap<String, AtomicInteger>());
	}

}
