package org.jclouds.orion.config.constans;

public class OrionHttpFields {

    final static public String ORION_VERSION_FIELD = "Orion-Version";

    final static public String SLUG = "Slug";

    // Form Parameters

    final static public String USERNAME = "login";
    final static public String PASSWORD = "password";

    // Ignore form authentication header
    // api methods wtih this header key will not be subject to authentication
    // check
    public final static String IGNORE_AUTHENTICATION = "ignoreAuthentication";

}
