package org.gots.springcourse.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/** This class will replace "WEB-INF/applicationContextMVC.xml" **/
public class MySpringMVCDispatcherServletInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /** This method replaces <param-value>/WEB-INF/applicationContextMVC.xml</param-value>
     * in <servlet>-section of WEB-INF/web.xml
     * @return one-item array with pointing to the config class SpringConfig
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringConfig.class };
    }

    /** Replaces the section <servlet-mapping>
     *                          <servlet-name>dispatcher</servlet-name>
     *                          <url-pattern>/</url-pattern>
     *                        </servlet-mapping>
     * @return one-item array containing the string "/"
     * from the only <url-pattern> of the <servlet-mapping>
     **/
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        addCharacterEncodingFilterUTF_8(servletContext);
        addHiddenHttpMethodFilter(servletContext);
    }

    private void addCharacterEncodingFilterUTF_8(ServletContext servletContext) {

        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("encodingFilter", filter);
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");

    }

    private void addHiddenHttpMethodFilter(ServletContext servletContext) {
        servletContext.addFilter(
                        "hiddenHttpMethodFilter",
                        new HiddenHttpMethodFilter()).
                addMappingForUrlPatterns(null ,true, "/*");
    }
}
