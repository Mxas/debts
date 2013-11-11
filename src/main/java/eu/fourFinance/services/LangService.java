package eu.fourFinance.services;

/**
 * 
 * For: vertimai .... 
 *
 * @author MindaugasK
 * @since 2013.11.11
 *
 */
public interface LangService {

    public static final String MSG_WRONG = "wrong";
    public static final String MSG_TERM_EMPTY = "MSG_TERM_EMPTY";
    public static final String MSG_LOAN_EMPTY = "MSG_LOAN_EMPTY";
    public static final String MSG_IP_EMPTY = "MSG_IP_EMPTY";
    public static final String MSG_CLIENT_EMPTY = "MSG_CLIENT_EMPTY";
    public static final String MSG_SUCCESS_EVA = "MSG_SUCCESS_EVA";
    public static final String MSG_INCORRECT_EVALUATION = "MSG_INCORRECT_EVALUATION";

    public static final String MSG_TOO_BIG_LOAN = "MSG_TOO_BIG_LOAN";
    public static final String MSG_TOO_SMALL_LOAN = "MSG_TOO_SMALL_LOAN";
    public static final String MSG_MAX_LOAN_AND_TIME = "MSG_MAX_LOAN_AND_TIME";
    public static final String MSG_MAX_APPLICATION_FROM_IP_PER_DAY = "MSG_MAX_APPLICATION_FROM_IP_PER_DAY";
    public static final String MSG_TOO_LONG_TERM = "MSG_TOO_LONG_TERM";
    public static final String MSG_TOO_SHORT_TERM = "MSG_TOO_LONG_TERM";

    /**
     * gets message
     * 
     * @param key
     * @param args
     * @return
     */
    public String get(String key, Object... args);
}
