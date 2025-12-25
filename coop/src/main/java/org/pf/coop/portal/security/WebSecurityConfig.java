package org.pf.coop.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@Autowired
	private SecurityEmailOtpUserDetailsService securityEmailOtpUserDetailsService;

    @Bean
    AuthenticationProvider authProvider() {
    	
    	System.out.println("Activated 1st Auth Provider");
    	
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(securityUserDetailsService);

        provider.setPasswordEncoder(new BCryptPasswordEncoder());

        return provider;
    }
    
    @Bean
    AuthenticationProvider authOtpProvider() {
    	
    	System.out.println("Activated 2nd Auth Provider");
    	
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(securityEmailOtpUserDetailsService);

        provider.setPasswordEncoder(new BCryptPasswordEncoder());

        return provider;
    }

    @SuppressWarnings({ "removal" })
	@Bean
	@Order(1)
    SecurityFilterChain filterChainMobile(HttpSecurity http) throws Exception {

    	String [] publicUrls = new String [] {
                "/api/**"
        };
    	
    	//System.out.println("Mobile Security activated.");
        
    	http.securityMatcher("/mobile/**")
    		.csrf(csrf -> csrf.ignoringRequestMatchers(publicUrls))
    		.authorizeHttpRequests(authorize -> authorize
    				.requestMatchers("/mobile/accessDenied").permitAll()
    				.requestMatchers("/mobile/article/**").permitAll()
    				.requestMatchers("/mobile/index").permitAll()
    				.requestMatchers("/mobile/loginOtp").permitAll()
    				.requestMatchers("/mobile/open/**").permitAll()
    				.requestMatchers("/mobile/home/**", "/mobile/profile/**").hasAnyRole("ACCOUNTS", "LIBRARY", "MODERATOR", "MEMBER", "MANAGER")
    				.requestMatchers("/mobile/changeMemberPassword", "/mobile/changePassword").hasAnyRole("ACCOUNTS", "LIBRARY", "MODERATOR", "MANAGER", "MEMBER")
    				.requestMatchers("/mobile/login-success").hasAnyRole("ACCOUNTS", "LIBRARY", "MODERATOR", "MANAGER", "MEMBER")
    				.anyRequest().denyAll()
    				)
    		.exceptionHandling((exceptions) -> exceptions
    				.accessDeniedPage("/mobile/accessDenied"))
    		.formLogin(form -> form
    				.loginPage("/mobile/login")
    				.loginProcessingUrl("/mobile/login")
    				.defaultSuccessUrl("/mobile/home", true)
    				.permitAll())
    		.logout(logout -> logout
    				.invalidateHttpSession(true)
    				.clearAuthentication(true)
    				.deleteCookies("JSESSIONID")
    				.logoutUrl("/mobile/logout")
    				.logoutSuccessUrl("/mobile/login?logout")
    				.logoutRequestMatcher(new AntPathRequestMatcher("/mobile/logout"))
    				.permitAll())
    		.authenticationProvider(this.authProvider())
    		.authenticationProvider(this.authOtpProvider());

        return http.build();
    }
	
	@SuppressWarnings({ "removal" })
	@Bean
	@Order(2)
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	String [] publicUrls = new String [] {
                "/api/**"
        };
    	
    	//System.out.println("Normal Security activated.");
    	
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
            				"/holiday/**", 
            				"/images/**", 
            				"/initialize",  
            				"/join", 
            				"/join/**",
            				"/loginOtp",
            				"/newsFeed/**",
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
            		//.logoutUrl("/logout")
            		.clearAuthentication(true)
            		.deleteCookies("JSESSIONID")
            		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            		.logoutSuccessUrl("/logout-success").permitAll())
    		.authenticationProvider(this.authProvider())
    		.authenticationProvider(this.authOtpProvider());

        return http.build();
    }
}
