package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.config.WebJspConfig;
import org.hillel.config.WebTLConfig;
import org.hillel.filter.CharsetEncodingFilter;
import org.hillel.servlet.AuthenticationServlet;
import org.hillel.servlet.WelcomeServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.*;
import java.nio.charset.StandardCharsets;


public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext rootConfig = new AnnotationConfigWebApplicationContext();
        rootConfig.register(RootConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootConfig));

        AnnotationConfigWebApplicationContext jspAppContext = new AnnotationConfigWebApplicationContext();
        jspAppContext.register(WebJspConfig.class);

        ServletRegistration.Dynamic jspServlet = servletContext.addServlet("jspServlet", new DispatcherServlet(jspAppContext));
        jspServlet.addMapping("/welcome");

        AnnotationConfigWebApplicationContext tlAppContext = new AnnotationConfigWebApplicationContext();
        tlAppContext.register(WebTLConfig.class);

        ServletRegistration.Dynamic tlServlet = servletContext.addServlet("tlServlet", new DispatcherServlet(tlAppContext));
        tlServlet.addMapping("/tl", "/tl/*");


        final FilterRegistration.Dynamic charsetEncoding = servletContext.addFilter("charsetEncoding", new CharacterEncodingFilter(StandardCharsets.UTF_8.displayName()));
        charsetEncoding.addMappingForUrlPatterns(null, true, "/*");

        final FilterRegistration.Dynamic hiddenHttpMethodFilter = servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter());
        hiddenHttpMethodFilter.addMappingForUrlPatterns(null, true, "/*");
    }

    /*extends AbstractAnnotationConfigDispatcherServletInitializer {
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
    }*/

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

}
