package nhl.stenden.config;

import nhl.stenden.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

/**
 * the configuration class used to configure Spring security in the application
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JsonTokenCreator jsonTokenCreator;

    /**
     * This returns the UserDetailsService bean used in the application
     * @return The UserDetailsService used to get the users from database
     * @throws Exception
     */
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }


    /**
     * This method creates the jsonTokenCreator bean used in the application
     * @param userDetailsService The users retrieved from the database
     * @param secretKey The secret key used in the HS256 encryption
     * @return The jsonTokenCreator bean
     */
    @Bean
    public JsonTokenCreator jsonTokenCreator(UserDetailsService userDetailsService, @Value("sncikerdoodlesnickerdsnoodlewrapper") String secretKey) {
        return new JsonTokenCreator(userDetailsService, secretKey);
    }

    /**
     * This method gets the user from the database and sets them in the userDetailsService
     * @param auth Used to authenticate the user in the Database
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManager userDetailsService = auth.jdbcAuthentication()
                .dataSource(dataSource)
                .getUserDetailsService();

        userDetailsService
                .setUsersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM USERS WHERE USERNAME = ?");

        userDetailsService
                .setAuthoritiesByUsernameQuery("SELECT USERNAME, ROLE FROM USERS WHERE USERNAME = ?");
    }

    /**
     * This method configures the way and order http request are sent and which elements are secured and how
     * @param http The http object that is to be configured
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        enableLoginAuthentication(http)
                .authorizeRequests()
                .antMatchers("/hololive-members/*","/hololive-members").hasRole("ADMIN")
                .antMatchers("/hololive-members/{id}/videos").hasAnyRole("APP","ADMIN")
                .antMatchers("/videos/{id}/url").hasAnyRole("APP","ADMIN")
                .anyRequest().hasAnyRole("ADMIN","APP")
                .and()
                .csrf()
                .disable();
    }


    /**
     * This method handles the way the login portion of the http request is handled
     * @param http the object to be configured
     * @return The http object which can be used to chain more http request
     * @throws Exception
     */
    private HttpSecurity enableLoginAuthentication(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .permitAll()
                .successHandler(new JsonTokenStatusHandler(jsonTokenCreator));

        http.addFilterBefore(new JsonTokenFilter(jsonTokenCreator), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http;
    }

}
