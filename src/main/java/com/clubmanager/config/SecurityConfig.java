package com.clubmanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.clubmanager.securiy.CustomAuthenticationFailureHandler;
import com.clubmanager.securiy.CustomAuthenticationProvider;
import com.clubmanager.securiy.CustomLoginSuccessHandler;
import com.clubmanager.securiy.CustomUserDetailsService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity //Spring MVC �� Spring Security�� �����ϴ� �뵵
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Setter(onMethod_= @Autowired)
	private DataSource dataSource;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		System.out.println("configure http...............");
		log.info("configure http...............");
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/sample/admin").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/sample/member").access("hasRole('ROLE_MEMBER')");
		
		//�α��� ����
		http.formLogin().loginPage("/customlogin").loginProcessingUrl("/loginprocess")
					    .successHandler(loginSuccessHandler())//�α��� ������
					    .failureHandler(loginFailureHandler());//�α��� ���н�
		
		
//		http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());//���� ���� ��
		
		
		http.logout().logoutUrl("/customLogout").invalidateHttpSession(true).logoutSuccessUrl("/intro");
//		http.logout().logoutUrl("/customLogout").invalidateHttpSession(true).deleteCookies("remember-me","JSESSIONID");

//		http.rememberMe()
//			.key("zerock")
//			.tokenRepository(persistentTokenRepository())
//			.tokenValiditySeconds(604800);
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
	
	@Bean  //�α��� ������ ó�� ����
	public AuthenticationSuccessHandler loginSuccessHandler() {
		log.info("loginSuccessHandler...............");
		return new CustomLoginSuccessHandler();
	}
	
	@Bean //�α��� ���н� ó�� ����
	public AuthenticationFailureHandler loginFailureHandler() {
		log.info("loginFailureHandler...............");
		return new CustomAuthenticationFailureHandler();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("configure JDBC...............");
		log.info("configure JDBC...............");
		
//		auth.
		
		auth
//		.authenticationProvider(customAuthenticationProvider())
		.userDetailsService(customUserService())
		.passwordEncoder(passwordEncoder())
		;
	}
	@Bean
	public AuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService customUserService() {
		return new CustomUserDetailsService();
	}
}
