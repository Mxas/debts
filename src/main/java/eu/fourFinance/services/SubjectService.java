package eu.fourFinance.services;

import eu.fourFinance.model.Subject;

public interface SubjectService {
	
	/**
	 * Finds subject by code, in not exist S\subject will be created... 
	 * @param code
	 * @return
	 */
	public Subject getSubject(String code);

}
