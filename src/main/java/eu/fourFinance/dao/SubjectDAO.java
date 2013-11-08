package eu.fourFinance.dao;

import eu.fourFinance.model.Subject;

public interface SubjectDAO {

    /**
     * 
     * @param code
     * @return
     */
    public Subject getSubject(String code);

    /**
     * Creates subject by given parameters
     * @param code
     * @return
     */
    public Subject createSubject(String code);
}
