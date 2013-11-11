package eu.fourFinance.services;

import eu.fourFinance.model.Subject;

/**
 * 
 * Naudojama: wirking witk subject 
 *
 * @author MindaugasK
 * @since 2013.11.11
 *
 */
public interface SubjectService {

    /**
     * Finds subject by code, in not exist S\subject will be created...
     * 
     * @param code
     * @return
     */
    public Subject getSubject(String code);

}
