package eu.fourFinance.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.awt.print.Book;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import eu.fourFinance.BaseTest;
import eu.fourFinance.services.EvaluationService;
import eu.fourFinance.testsuites.categories.IntegrationTests;
import eu.fourFinance.web.beans.EvaluationCommand;

@Category(IntegrationTests.class)
@WebAppConfiguration
public class LoanControllerRequestTests extends BaseTest {

	private MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetLonaInterestRate() throws Exception {
		mockMvc.perform(get("/getLonaInterestRate.mvc"))
				.andExpect(status().isOk())
				.andExpect(
						content().string(
								String.valueOf(EvaluationService.LOAN_RATE)));
	}

	//@Test
	public void testEvaluate() throws Exception {

		mockMvc.perform(
				post("/evaluate.mvc").content(json(new EvaluationCommand()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(Boolean.FALSE));
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
