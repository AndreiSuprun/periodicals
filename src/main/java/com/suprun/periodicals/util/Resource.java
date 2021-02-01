package com.suprun.periodicals.util;

import java.util.ResourceBundle;

/**
 * Utility class for storing links to used resource bundles as enum entries.
 */
public enum Resource {

    DATABASE(ResourceBundle.getBundle("properties.database")),
    QUERIES(ResourceBundle.getBundle("properties.sql_queries")),
    PATH(ResourceBundle.getBundle("properties.path")),
    VIEW(ResourceBundle.getBundle("properties.view")),
    ATTRIBUTE(ResourceBundle.getBundle("properties.attribute")),
    PARAMETER(ResourceBundle.getBundle("properties.parameter")),
    FILE_UPLOAD(ResourceBundle.getBundle("properties.file_upload"));

    private ResourceBundle resourceBundle;

    Resource(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    /**
     * Gets a string for the given key from this resource bundle
     *
     * @param key the key for the desired string
     * @return the string for the given key
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
