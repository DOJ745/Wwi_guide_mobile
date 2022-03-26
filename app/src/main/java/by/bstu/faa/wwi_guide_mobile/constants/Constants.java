package by.bstu.faa.wwi_guide_mobile.constants;

public class Constants {
    public static class Values {
        public final static String BASE_URL = "http://10.0.2.2:9000/";

        public final static String IMG_BASE_URL = BASE_URL + "images/";
        public final static String RANK_IMG_URL = IMG_BASE_URL + "ranks";
        public final static String COUNTRIES_IMG_URL = IMG_BASE_URL + "countries";

        public final static String NO_IMG_URL = "https://www.bakicubuk.com/wp-content/uploads/builder-image.jpg";

        public final static String BEARER = "Bearer ";

        public final static int CODE_BAD_REQUEST = 400;
        public final static int CODE_UNAUTHORIZED = 401;
        public final static int CODE_FORBIDDEN = 403;
        public final static int CODE_NOT_FOUND= 404;
        public final static int CODE_SERVER_ERROR = 500;

        public static final String LOG_CONSTRUCTOR = "CONSTRUCTOR";
        public static final String LOG_REPO = "REPOSITORY";
        public static final String LOG_VIEW_MODEL = "VIEW MODEL";

        private final static String LOG_TAG_REG = "REGISTER ";
        private final static String LOG_TAG_MAIN = "MAIN ";
        private final static String LOG_TAG_LOGIN = "LOGIN ";

        public final static String LOG_TAG_REG_REPO = LOG_TAG_REG + LOG_REPO;

        public final static String LOG_TAG_REG_VIEW_MODEL = LOG_TAG_REG + LOG_VIEW_MODEL;
        public final static String LOG_TAG_MAIN_VIEW_MODEL = LOG_TAG_MAIN + LOG_VIEW_MODEL;
        public final static String LOG_TAG_LOGIN_VIEW_MODEL = LOG_TAG_LOGIN + LOG_VIEW_MODEL;

        public final static String LOG_TAG_REG_FRAGMENT = "REGISTER FRAGMENT";
        public final static String LOG_TAG_MAIN_FRAGMENT = "MAIN FRAGMENT";
        public final static String LOG_TAG_LOGIN_FRAGMENT = "LOGIN FRAGMENT";
    }
}
