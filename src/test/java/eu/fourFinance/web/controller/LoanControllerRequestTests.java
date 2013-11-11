package eu.fourFinance.web.controller;

import static eu.fourFinance.services.LangService.MSG_CLIENT_EMPTY;
import static eu.fourFinance.services.LangService.MSG_LOAN_EMPTY;
import static eu.fourFinance.services.LangService.MSG_SUCCESS_EVA;
import static eu.fourFinance.services.LangService.MSG_TERM_EMPTY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import eu.fourFinance.BaseTest;
import eu.fourFinance.model.Debts;
import eu.fourFinance.model.Evaluation;
import eu.fourFinance.services.DebtsService;
import eu.fourFinance.services.EvaluationService;
import eu.fourFinance.services.LangService;
import eu.fourFinance.services.SubjectService;
import eu.fourFinance.testsuites.categories.IntegrationTests;
import eu.fourFinance.web.beans.EvaluationCommand;

@Category(IntegrationTests.class)
@WebAppConfiguration
public class LoanControllerRequestTests extends BaseTest {

    private MockMvc mockMvc;

    @Autowired
    private LangService langService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DebtsService debtsService;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetLonaInterestRate() throws Exception {
        mockMvc.perform(get("/getLonaInterestRate.mvc")).andExpect(status().isOk()).andExpect(content().string(String.valueOf(EvaluationService.LOAN_RATE)));
    }

    @Test
    public void testApplay() throws Exception {
        Evaluation s = evaluationService.createEvaluation(subjectService.getSubject("AAA"), new Date(), 1000d, 12, "d");
        evaluationService.evaluateRisk(s);

        evaluationService.calcLoanDetails(s);

        long evaluationId = s.getId();
        mockMvc.perform(get("/applay.mvc/{evaluationId}", evaluationId)).andExpect(status().isOk()).andExpect(content().string("OK"));
    }

    @Test
    public void testExtendAndGetAll() throws Exception {
        Evaluation s = evaluationService.createEvaluation(subjectService.getSubject("AAA"), new Date(), 1000d, 12, "d");
        evaluationService.evaluateRisk(s);

        evaluationService.calcLoanDetails(s);

        Debts d = evaluationService.applay(s);

        long id = d.getId();
        mockMvc.perform(get("/extend.mvc/{evaluationId}", id)).andExpect(status().isOk()).andExpect(content().string("OK"));

        mockMvc.perform(get("/getAll.mvc")).andExpect(status().isOk()).andExpect(jsonPath("$.successfullEvaluation[0].code").value(s.getSubject().getCode()));
    }

    @Test
    public void testEvaluate() throws Exception {
        EvaluationCommand command = new EvaluationCommand();
        mockMvc.perform(post("/evaluate.mvc").content(json(command)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE)).andExpect(jsonPath("$.messages[0]").value(langService.get(MSG_LOAN_EMPTY)));

        command.setLoan(1000d);
        mockMvc.perform(post("/evaluate.mvc").content(json(command)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE)).andExpect(jsonPath("$.messages[0]").value(langService.get(MSG_TERM_EMPTY)));

        command.setTerm(10);
        mockMvc.perform(post("/evaluate.mvc").content(json(command)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE)).andExpect(jsonPath("$.messages[0]").value(langService.get(MSG_CLIENT_EMPTY)));

        command.setCode("AAA");
        mockMvc.perform(post("/evaluate.mvc").content(json(command)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(Boolean.TRUE)).andExpect(jsonPath("$.messages[0]").value(langService.get(MSG_SUCCESS_EVA)));
    }

    private String json(Object o) {

        ObjectMapper mapper = new ObjectMapper();
        String content = null;
        try {
            content = mapper.writeValueAsString(o);

        } catch (Exception e) {
            Assert.fail();
        }
        return content;
    }
}
