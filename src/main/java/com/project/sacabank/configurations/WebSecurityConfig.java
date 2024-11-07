package com.project.sacabank.configurations;

import static com.project.sacabank.utils.Constants.AuthPermissionAll.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.project.sacabank.auth.AuthTokenFilter;
import com.project.sacabank.auth.UserDetailsServiceImpl;
import com.project.sacabank.exception.AuthEntryPointJwt;

import lombok.AllArgsConstructor;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;
	private final AuthEntryPointJwt unauthorizedHandler;
	private final AuthTokenFilter authTokenFilter;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return this.authTokenFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.csrf(AbstractHttpConfigurer::disable)
				.exceptionHandling(exception -> exception
						.defaultAuthenticationEntryPointFor(
								unauthorizedHandler,
								new AntPathRequestMatcher("/api/**"))
						.defaultAuthenticationEntryPointFor(
								new LoginUrlAuthenticationEntryPoint(PATH_LOGIN_PAGE),
								new AntPathRequestMatcher("/**")))
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				.authorizeHttpRequests(
						auth -> auth
								.requestMatchers(

										AUTH_PATH,
										API_REGISTER_VENDOR_PATH,
										ROLE_PATH,
										USER_VENDOR,
										PRODUCT_PUBLIC,
										"/actuator",
										"/api/upload/**",
										"/api/upload/callback",
										"/v2/api-docs",
										"/v3/api-docs",
										"/v3/api-docs/**",
										"/swagger-resources",
										"/swagger-ui.html",
										"/swagger-resources/**",
										"/configuration/ui",
										"/configuration/security",
										"/webjars/**",
										"/swagger-ui/**",
										"/static/**"

								)
								.permitAll()
								.requestMatchers(

										HttpMethod.GET,
										"/xxx/**",
										"/uploads/**",
										"/api/category/parent/**",
										"/api/product/public/list",
										"/api/category",
										"/api/category/white_list",
										"/api/product_category",
										"/api/blog",
										"/api/blog/**",
										"/api/blog/**",
										"/api/banner/**",
										"/api/product_comment/product/preview/**",
										"/api/product_document/**",
										"/san-pham/**",
										"/bai-viet/**"

								)
								.permitAll()
								.requestMatchers(

										"/",
										"/home",
										"/san-pham",
										"/cart",
										"/profile",
										"/xxx/**",
										"/static/**",
										"/xxx/styles/**",
										"/uploads/**"

								)
								.permitAll()
								.anyRequest()
								.authenticated())
				.formLogin(form -> form
						.loginPage(PATH_LOGIN_PAGE)
						.loginProcessingUrl(PATH_LOGIN_PAGE)
						.defaultSuccessUrl("/", true)
						.failureUrl("/dang-nhap?error=true")
						.permitAll()

				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.invalidateHttpSession(true)
						.permitAll());

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

}
