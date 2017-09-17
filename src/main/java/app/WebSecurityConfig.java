package app;

import app.exceptions.AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


    @Autowired
    DataSource dataSource;

    /*
        Authentication configuration - use matchers to specify authentication routes
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/health").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority( "admin")
                .antMatchers("/api/**").hasAnyAuthority("user", "admin")
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    /*
        Additional configuration for jdbc/in memory authentication
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("password").roles("ADMIN");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select name, password, enabled from users where name=?")
                .authoritiesByUsernameQuery(
                        "select u.name, r.name from users u " +
                                "inner join roles_users ru on(u.id=ru.users_id) " +
                                "inner join roles r on(r.id=ru.roles_id) " +
                                "where u.name=?");
    }
}