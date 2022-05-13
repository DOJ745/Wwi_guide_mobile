package by.bstu.faa.wwi_guide_mobile.constants;

public class CONSTANTS {

    public static class APP_DATABASE {
        public final static String DATABASE_NAME = "main_db";

        public final static String USER_TABLE = "user";
        public final static String ACHIEVEMENTS_TABLE = "achievements";
        public final static String LOGS_TABLE = "logs";
        public final static String EVENTS_TABLE = "events";
        public final static String RANKS_TABLE = "ranks";
        public final static String COUNTRIES_TABLE = "countries";
        public final static String SURVEYS_TABLE = "surveys";
        public final static String SURVEYS_QUESTIONS_TABLE = "surveys_questions";
        public final static String SURVEYS_ANSWERS_TABLE = "surveys_answers";
        public final static String TESTS_QUESTIONS_TABLE = "tests_questions";
        public final static String TESTS_ANSWERS_TABLE = "tests_answers";
        public final static String YEARS_TABLE = "years";
        public final static String ARMAMENT_TABLE = "armament";
        public final static String TEST_THEME_TABLE = "tests_themes";
    }

    public static class LOG_STRUCT {
        public final static String ACTION_NAME_PASSED_TEST = "Passed test theme ID: ";
        public final static String ACTION_RESULT_TEST_RATIO = "Total Q amount/Correct answers/threshold: ";
        public final static String ACTION_NAME_PASSED_SURVEY = "Passed survey ID: ";
        public final static String ACTION_RESULT_SURVEY = "Chosen option: ";
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

    public static class HTTP_CODES { public final static int BAD_REQUEST = 400; }

    public static class LOG_TAGS { public static final String CONSTRUCTOR = "CONSTRUCTOR";}

    public static class URLS {
        public final static String API_URL = "/api.wwi-guide.by/";
        public final static String BASE = "https://quiet-eyrie-18331.herokuapp.com" + API_URL;
        public final static String BASE_LOCALHOST = "http://10.0.2.2:5000" + API_URL;

        public final static String IMG = BASE + "images/";
        public final static String RANK_IMG = IMG + "ranks";
        public final static String COUNTRIES_IMG = IMG + "countries";

        public final static String NO_IMG = "https://www.bakicubuk.com/wp-content/uploads/builder-image.jpg";

        public final static String BEARER = "Bearer ";
    }

    public static class LIFECYCLE_STATES {
        public final static String ON_CREATE = "onCreate";
        public final static String ON_CREATE_VIEW = "onCreateView";
        public final static String ON_VIEW_CREATED = "onViewCreated";
        public final static String ON_VIEW_STATE_RESTORED = "onViewStateRestored";
        public final static String ON_START = "onStart";
        public final static String ON_RESTART = "onRestart";
        public final static String ON_RESUME = "onResume";
        public final static String ON_PAUSE = "onPause";
        public final static String ON_STOP = "onStop";
        public final static String ON_DESTROY_VIEW = "onDestroyView";
        public final static String ON_DESTROY = "onDestroy";
        public final static String ON_DETACH = "onDetach";
    }
}