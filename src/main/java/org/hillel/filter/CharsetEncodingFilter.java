package org.hillel.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//@WebFilter(urlPatterns = "/*", filterName = "charsetEncoding")
public class CharsetEncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        servletResponse.setContentType("text/html;charset=UTF-8");
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
