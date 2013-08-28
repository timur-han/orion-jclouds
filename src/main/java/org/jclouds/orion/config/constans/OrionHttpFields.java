package org.jclouds.orion.config.constans;

public class OrionHttpFields {

    final static public String ORION_VERSION_FIELD = "Orion-Version";

    final static public String HEADER_SLUG = "Slug";

    // Form Parameters

    final static public String USERNAME = "login";
    final static public String PASSWORD = "password";

    // Ignore form authentication header
    // api methods wtih this header key will not be subject to authentication
    // check
    public final static String IGNORE_AUTHENTICATION = "ignoreAuthentication";

    public final static String FROM_PARAM_PARTS = "parts";

    // Orion Specific Fields

    public final static String ORION_NAME = "Name";
    public final static String ORION_DIRECTORY = "Directory";
    public final static String ORION_ATTRIBUTES = "Attributes";
    public final static String ORION_CONTENT_TYPE = "ContentType";

    public final static String ORION_ATTRIBUTE_READONLY = "ContentType";
    public final static String ORION_ATTRIBUTE_EXECUTABLE = "Executable";
    public final static String ORION_ATTRIBUTE_HIDDEN = "Hidden";
    public final static String ORION_ATTRIBUTE_ARCHIVE = "Archive";
    public final static String ORION_ATTRIBUTE_SYMLINK = "SymbolicLink";

}
