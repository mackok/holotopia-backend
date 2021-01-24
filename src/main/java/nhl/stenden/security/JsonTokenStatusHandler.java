package nhl.stenden.security;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used when a user succeeds at login
 */
public class JsonTokenStatusHandler implements AuthenticationSuccessHandler {

    private final JsonTokenCreator jsonTokenCreator;

    public JsonTokenStatusHandler(JsonTokenCreator jsonTokenCreator) {
        this.jsonTokenCreator = jsonTokenCreator;
    }

    /**
     * This method returns a valid JSON token to the user on successfully logging in
     * @param request The web request that caused the authentication
     * @param response The response that is being sent to the user
     * @param authentication the object used to confirm successfully authentication of login
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String token = jsonTokenCreator.createToken(authentication.getName());
        response.addHeader("json-token", token);
        response.setStatus(HttpStatus.OK.value());
    }

}
