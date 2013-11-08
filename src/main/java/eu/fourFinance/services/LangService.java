package eu.fourFinance.services;

public interface LangService {

    public static final String MSG_WRONG = "wrong";
    public static final String MSG_TERM_EMPTY = "MSG_TERM_EMPTY";
    public static final String MSG_LOAN_EMPTY = "MSG_LOAN_EMPTY";
    public static final String MSG_IP_EMPTY = "MSG_IP_EMPTY";
    
    /**
     * gets message
     * 
     * @param key
     * @return
     */
    public String get(String key);
}
