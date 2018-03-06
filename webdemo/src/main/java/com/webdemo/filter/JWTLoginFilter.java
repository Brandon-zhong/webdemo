package com.webdemo.filter;

import com.webdemo.service.TokenAuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author itguang
 * @create 2018-01-02 13:48
 **/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {


    private AuthenticationManager authenticationManager;

    /**
     * @param url                   拦截的登陆URL地址
     * @param authenticationManager
     */
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url, "POST"));
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        //得到用户登陆信息,并封装到 Authentication 中,供自定义用户组件使用.
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");

        System.err.println("com.springsecurity.filter.JWTLoginFilter.attemptAuthentication--> " + id + "  " + pwd);

        if (id == null) {
            id = "";
        }

        if (pwd == null) {
            pwd = "";
        }

        id = id.trim();


        List<GrantedAuthority> authorities = new ArrayList<>();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, pwd, authorities);

        //authenticate()接受一个token参数,返回一个完全经过身份验证的对象，包括证书.
        // 这里并没有对用户名密码进行验证,而是使用 AuthenticationProvider 提供的 authenticate 方法返回一个完全经过身份验证的对象，包括证书.
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //UsernamePasswordAuthenticationToken 是 Authentication 的实现类
        return authenticate;
    }


    /**
     * 登陆成功后,此方法会被调用,因此我们可以在次方法中生成token,并返回给客户端
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.err.println("com.springsecurity.filter.JWTLoginFilter.successfulAuthentication");

        TokenAuthenticationService.addAuthenticatiotoHttpHeader(response, authResult);

        chain.doFilter(request, response);
    }
}
