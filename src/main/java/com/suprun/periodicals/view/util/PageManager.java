package com.suprun.periodicals.view.util;

import com.suprun.periodicals.util.Resource;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for supporting pagination
 *
 * @author Andrei Suprun
 */
public class PageManager {

    private static final long DEFAULT_RECORDS_PER_PAGE = 10;
    private static final String DEFAULT_REQUEST_PARAMETER_PAGINATION_PAGE = Resource.PARAMETER.getProperty("pagination.page");
    private static final String DEFAULT_ATTRIBUTE_PAGINATION_PAGE = Resource.ATTRIBUTE.getProperty("pagination.page");
    private static final String DEFAULT_ATTRIBUTE_PAGINATION_NUMBER_OF_PAGES = Resource.ATTRIBUTE.getProperty("pagination.number.pages");
    private static final int FIRST_PAGE = 1;

    private final long recordsPerPage;
    private final String requestParameterPaginationPage;
    private final String attributesPaginationPage;
    private final String attributesPaginationNumberOfPage;

    public PageManager() {
        this(DEFAULT_RECORDS_PER_PAGE,
                DEFAULT_REQUEST_PARAMETER_PAGINATION_PAGE,
                DEFAULT_ATTRIBUTE_PAGINATION_PAGE,
                DEFAULT_ATTRIBUTE_PAGINATION_NUMBER_OF_PAGES);
    }

    public PageManager(long recordsPerPage) {
        this(recordsPerPage,
                DEFAULT_REQUEST_PARAMETER_PAGINATION_PAGE,
                DEFAULT_ATTRIBUTE_PAGINATION_PAGE,
                DEFAULT_ATTRIBUTE_PAGINATION_NUMBER_OF_PAGES);
    }

    public PageManager(long recordsPerPage,
                             String requestParameterPaginationPage,
                             String attributesPaginationPage,
                             String attributesPaginationNumberOfPage) {
        this.recordsPerPage = recordsPerPage;
        this.requestParameterPaginationPage = requestParameterPaginationPage;
        this.attributesPaginationPage = attributesPaginationPage;
        this.attributesPaginationNumberOfPage = attributesPaginationNumberOfPage;
    }

    public long manage(HttpServletRequest req, long rowsCount) {
        String pageStr = req.getParameter(requestParameterPaginationPage);

        long numberOfPages = rowsCount / recordsPerPage;
        if (rowsCount % recordsPerPage > 0) {
            numberOfPages++;
        }
        long page;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException ex) {
            page = FIRST_PAGE;
        }
        if (page <= 0) {
            page = FIRST_PAGE;
        }
        if (numberOfPages > 0 && page > numberOfPages) {
            page = numberOfPages;
        }
        long skip = (page - 1) * recordsPerPage;
        req.setAttribute(attributesPaginationPage, page);
        req.setAttribute(attributesPaginationNumberOfPage, numberOfPages);
        return skip;
    }

    public long getRecordsPerPage() {
        return recordsPerPage;
    }
}
