package eu.fourFinance.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import eu.fourFinance.dao.SubjectDAO;
import eu.fourFinance.model.Subject;
import eu.fourFinance.services.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDAO subjectDAO;

    @Override
    public Subject getSubject(String code) {
        Assert.hasLength(code);

        Subject subject = subjectDAO.getSubject(code);

        if (subject == null)
            subject = subjectDAO.createSubject(code);

        return subject;
    }

}
