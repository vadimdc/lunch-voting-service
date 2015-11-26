package task.service.lvs.config;

import task.service.lvs.ApiEndpoints;
import task.service.lvs.domain.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
            .withUser("admin").password("admin").roles(Role.ADMIN.name())
            .and()
            .withUser("user1").password("user1").roles(Role.USER.name())
            .and()
            .withUser("user2").password("user2").roles(Role.USER.name())
            .and()
            .withUser("user3").password("user3").roles(Role.USER.name())
            .and()
            .withUser("user4").password("user4").roles(Role.USER.name())
            .and()
            .withUser("user5").password("user5").roles(Role.USER.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers(ApiEndpoints.apiEndpoint("/lunch-menu"), ApiEndpoints.apiEndpoint("/voting/results")).permitAll()
                .antMatchers(HttpMethod.GET, ApiEndpoints.apiEndpoint("/voting")).authenticated()
                .antMatchers(HttpMethod.POST, ApiEndpoints.apiEndpoint("/voting")).authenticated()
                .antMatchers(ApiEndpoints.apiEndpoint("/menu-items/**"), ApiEndpoints.apiEndpoint("/restaurants/**"), ApiEndpoints.apiEndpoint("/dishes/**")).hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, ApiEndpoints.apiEndpoint("/voting")).hasRole(Role.ADMIN.name())
                .anyRequest().denyAll()
                .and()
                .csrf().disable();
    }
}