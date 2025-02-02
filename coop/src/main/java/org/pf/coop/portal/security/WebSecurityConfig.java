package org.pf.coop.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;

    @Bean
    AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());

        return provider;
    }

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	String [] publicUrls = new String [] {
                "/api/**"
        };
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers(publicUrls))
                .authorizeHttpRequests((authorize) -> authorize
                		//.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                		.requestMatchers(
                				"/",
                				"/article/**",
                				"/assets/**",
                				"/acceptInvitation/**",
                				"/accessDenied",
                				"/carousel/**",
                				"/category/**",
                				"/contacts",
                				"/css/**", 
                				"/event/**", 
                				"/help/**", 
                				"/images/**", 
                				"/initialize",  
                				"/join", 
                				"/join/**", 
                				"/open/**",
                				"/resetPassword", 
                				"/siteLogo", 
                				"/webjars/**").permitAll()
                		.requestMatchers("/accounts/**").hasAnyRole("ACCOUNTS", "MANAGER")
                		.requestMatchers("/library/**").hasAnyRole("LIBRARY", "MANAGER")
                		.requestMatchers("/manager/**").hasRole("MANAGER")
                		.requestMatchers("/moderator/**").hasAnyRole("MODERATOR", "MANAGER")
                		.requestMatchers("/home/**", "/profile/**").hasAnyRole("ACCOUNTS", "LIBRARY", "MODERATOR", "MEMBER", "MANAGER")
                        .requestMatchers("/changeMemberPassword", "/changePassword").hasAnyRole("ACCOUNTS", "LIBRARY", "MODERATOR", "MANAGER", "MEMBER")
                        .requestMatchers("/login-success").hasAnyRole("ACCOUNTS", "LIBRARY", "MODERATOR", "MANAGER", "MEMBER")
                        .anyRequest().denyAll()
                		)
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                		.accessDeniedPage("/accessDenied"))
                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home", true))
                .logout(logout -> logout.invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/logout-success").permitAll());

        return http.build();
    }
}
