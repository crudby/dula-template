package com.crud.dula.security;

import com.crud.dula.security.authenticate.SecurityAuthorizationManager;
import com.crud.dula.security.filter.AccessDeniedHandlerImpl;
import com.crud.dula.security.filter.AuthenticationEntryPointImpl;
import com.crud.dula.security.filter.JwtAuthenticationTokenFilter;
import com.crud.dula.security.provider.WechatAuthenticationProvider;
import com.crud.dula.security.service.WechatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author crud
 * @date 2023/9/14
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Resource(name = "sysUserDetailService")
    private UserDetailsService userDetailsService;

    @Resource
    private WechatService wechatService;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Resource
    private SecurityAuthorizationManager securityAuthorizationManager;

    @Value("${dula.security.permitAllUrls:{}}")
    private String[] permitAllUrls;

    /**
     * 用户名密码验证
     * @return provider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    /**
     * 微信登录
     * @return provider
     */
    @Bean
    public WechatAuthenticationProvider wechatAuthenticationProvider(){
        return new WechatAuthenticationProvider(wechatService);
    }

    /**
     * 认证管理器
     * @return manger
     */
    @Bean("securityAuthenticationManager")
    public AuthenticationManager authenticationManager() {
        ProviderManager authenticationManager = new ProviderManager(Arrays.asList(daoAuthenticationProvider(), wechatAuthenticationProvider()));
        // 不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:10086", "http://localhost:10087", "http://localhost:10088", "http://172.16.204.167:10086"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().configurationSource(corsConfigurationSource());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .antMatchers(permitAllUrls).permitAll()
                .anyRequest().authenticated()
                //.anyRequest().access(securityAuthorizationManager)
                .and()
                // jwt过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置认证失败处理器和授权失败处理器
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointImpl)
                .accessDeniedHandler(accessDeniedHandlerImpl);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
