package org.hillel;

import org.hillel.config.WebJspConfig;
import org.hillel.filter.CharsetEncodingFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;


public class Application extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebJspConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CharacterEncodingFilter(StandardCharsets.UTF_8.displayName())};
    }

    /*extends AbstractDispatcherServletInitializer {
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();
        annotationConfigWebApplicationContext.register(WebJspConfig.class);
        return annotationConfigWebApplicationContext;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/welcome"};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }*/


    /*implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        final ServletRegistration.Dynamic welcomeServlet = servletContext.addServlet("welcomeServlet", WelcomeServlet.class);
        welcomeServlet.addMapping("/welcome");
        final ServletRegistration.Dynamic authServlet = servletContext.addServlet("authServlet", AuthenticationServlet.class);
        authServlet.addMapping("/auth");
        final FilterRegistration.Dynamic charsetEncoding = servletContext.addFilter("charsetEncoding", new CharacterEncodingFilter(StandardCharsets.UTF_8.displayName()));
        charsetEncoding.addMappingForUrlPatterns(null, true, "/*");


    }*/
}
