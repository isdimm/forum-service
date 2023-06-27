package telran.java47.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dao.UserAccountRepository;
import telran.java47.accounting.model.UserAccount;

@Component
@RequiredArgsConstructor
@Order(30)
public class OwnerFilter implements Filter {

	final UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndpoint(request.getMethod(), request.getServletPath())) {
			UserAccount userAccount = userAccountRepository.findById(request.getUserPrincipal().getName()).orElse(null);
			if (userAccount == null) {
				response.sendError(401, "login or password is not valid");
				return;
			}
			
			if(userAccount.getRoles().contains("Moderator") && request.getMethod().equals("DELETE") ) {
				chain.doFilter(request, response);
			}
			
			if(!request.getUserPrincipal().getName().equals(userAccount.getLogin())) {
				response.sendError(403, "not permited");
				return;
			}
		}
		chain.doFilter(request, response);


	}

	private boolean checkEndpoint(String method, String path) {
		return !("PUT".equals(method) && path.matches("/user/.*/?")
				|| "DELETE".equals(method) && path.matches("/user/.*/?"));
	}

}
