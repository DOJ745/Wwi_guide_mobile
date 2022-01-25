package by.bstu.faa.wwi_guide_mobile.constants;

public class Constants {
    public static class Values {
        public final static String BASE_URL = "http://10.0.2.2:9000/";

        public final static String IMG_BASE_URL = BASE_URL + "images/";
        public final static String RANK_IMG_URL = IMG_BASE_URL + "ranks";
        public final static String COUNTRIES_IMG_URL = IMG_BASE_URL + "countries";

        public final static String NO_IMG_URL = "https://www.bakicubuk.com/wp-content/uploads/builder-image.jpg";

        public final static int CODE_BAD_REQUEST = 400;
        public final static int CODE_UNAUTHORIZED = 401;
        public final static int CODE_FORBIDDEN = 403;
        public final static int CODE_NOT_FOUND= 403;
        public final static int CODE_SERVER_ERROR = 500;
    }
}
