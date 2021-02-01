package com.suprun.periodicals.view.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * The filter allows to set request and response encoding.
 * Default encoding is UTF-8. You can change it by
 * setting "encoding" initialize parameter to web.xml.
 * The filter has to be first in a filters queue.
 *
 * @author Andrei Suprun
 */
public class EncodingFilter implements Filter {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String ENCODING_PARAM_NAME = "encoding";

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter(ENCODING_PARAM_NAME);
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestEncoding = request.getCharacterEncoding();
        if (!encoding.equalsIgnoreCase(requestEncoding)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
