package nhl.stenden.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class filters the http request and checks if it contains a valid JSON token
 */
public class JsonTokenFilter extends GenericFilterBean {

    private JsonTokenCreator jsonTokenCreator;

    public JsonTokenFilter(JsonTokenCreator jsonTokenCreator) {
        this.jsonTokenCreator = jsonTokenCreator;
    }


    /**
     * This method checks if a JSON token is passed and authenticates it if it's valid
     * @param request The request that was sent by the user
     * @param response The response the is sent back to the user
     * @param chain The chain to be fired on method execution
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = jsonTokenCreator.getToken((HttpServletRequest) request);
        try {

            if (token != null && jsonTokenCreator.validateToken(token)) {

                Authentication authentication = jsonTokenCreator.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String newToken = jsonTokenCreator.getRefreshToken(token);
                if (newToken != null) {

                    ((HttpServletResponse) response).addHeader("jwt-new-token", newToken);
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

}
