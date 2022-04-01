package by.bstu.faa.wwi_guide_mobile.constants;

public class CONSTANTS {

    public static class APP_DATABASE {
        public final static String DATABASE_NAME = "main_db";
        public final static String USER_TABLE = "user";
    }

    public static class WEB_APP_SUCCESS_RESPONSES {
        public final static String REG_SUCCESS = "Successful registration";
        public final static String LOGIN_SUCCESS = "Successful login";
    }

    public static class WEB_APP_ERR_RESPONSES {
        public final static String REG_SUCH_USER_EXISTS = "Such user already exists!";
        public final static String LOGIN_NO_SUCH_USER = "No such user!";
        public final static String LOGIN_INCORRECT_PASSWORD = "Incorrect password!";
    }

    public static class HTTP_CODES {
        public final static int BAD_REQUEST = 400;
        public final static int UNAUTHORIZED = 401;
        public final static int FORBIDDEN = 403;
        public final static int NOT_FOUND= 404;
        public final static int SERVER_ERROR = 500;
    }

    public static class LOG_TAGS {
        public static final String CONSTRUCTOR = "CONSTRUCTOR";
        public static final String REPO = "REPOSITORY";
        public static final String VIEW_MODEL = "VIEW MODEL";
        public static final String FRAGMENT = "FRAGMENT";

        private final static String REG = "REGISTER ";
        private final static String MAIN = "MAIN ";
        private final static String LOGIN = "LOGIN ";

        public final static String REG_REPO = REG + REPO;

        public final static String REG_VIEW_MODEL = REG + VIEW_MODEL;
        public final static String MAIN_VIEW_MODEL = MAIN + VIEW_MODEL;
        public final static String LOGIN_VIEW_MODEL = LOGIN + VIEW_MODEL;

        public final static String REG_FRAGMENT = REG + FRAGMENT;
        public final static String MAIN_FRAGMENT = MAIN + FRAGMENT;
        public final static String LOGIN_FRAGMENT = LOGIN + FRAGMENT;
    }

    public static class URLS {
        public final static String BASE = "http://10.0.2.2:9000/";

        public final static String IMG = BASE + "images/";
        public final static String RANK_IMG = IMG + "ranks";
        public final static String COUNTRIES_IMG = IMG + "countries";

        public final static String NO_IMG = "https://www.bakicubuk.com/wp-content/uploads/builder-image.jpg";

        public final static String BEARER = "Bearer ";
    }

    public static class LIFECYCLE {
        public final static String ON_CREATE = "ON CREATE";
        public final static String ON_CREATE_VIEW = "ON CREATE VIEW";
        public final static String ON_VIEW_CREATED = "ON VIEW CREATED";
    }
}
