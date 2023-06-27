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
import telran.java47.accounting.dto.exceptions.UserNotFoundException;
import telran.java47.accounting.model.UserAccount;

@Component
@RequiredArgsConstructor
@Order(20)
public class AdminFilter implements Filter {
	
	final UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		System.out.println(request.getUserPrincipal().getName());
		
		if (checkEndpoint(request.getMethod(), request.getServletPath())) {
			UserAccount userAccount = userAccountRepository.findById(request.getUserPrincipal().getName()).orElse(null);
			if(userAccount == null) {
				response.sendError(401, "login or password is not valid");
				return;
			}
			if(!userAccount.getRoles().contains("Moderator")) {
				response.sendError(401, "not permited");
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	private boolean checkEndpoint(String method, String path) {
		return !("PUT".equals(method) && path.matches("/user/.*/role/.*/?") || "DELETE".equals(method) && path.matches("/user/.*/role/.*/?"));
	}

}
