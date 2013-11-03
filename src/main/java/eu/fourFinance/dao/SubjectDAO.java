package eu.fourFinance.dao;

import eu.fourFinance.model.Subject;

public interface SubjectDAO {
	
	/**
	 * Finds subject by code, in not exist S\subject will be created... 
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
