
package com.n26.codingchallenge.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.codingchallenge.common.AddTransactionRequest;
import com.n26.codingchallenge.dao.model.TransactionSummary;
import com.n26.codingchallenge.dao.repository.TransactionRepository;
import com.n26.codingchallenge.rest.TransactionRestController;
import com.n26.codingchallenge.service.TransactionService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TransactionRestController.class)
public class TransactionRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private TransactionRepository transactionRepository;

	@MockBean
	private TransactionService transactionService;

	
	@Test
	public void givenTransaction_whenSavedSuccessfully_ThenReturnHttp201() throws Exception {
		when(this.transactionService.addTransaction(any(Double.class), any(Long.class)))
				.thenReturn(getCreatedResponse());
		this.mockMvc
				.perform(post("/transactions").contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(getValidTransaction())))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void givenInvalidTransaction_whenRequestFired_ThenReturnHttp204() throws Exception {
		when(this.transactionService.addTransaction(any(Double.class), any(Long.class)))
				.thenReturn(getNoContentResponse());
		this.mockMvc.perform(post("/transactions").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getInValidTransaction())))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void whenTransactionSummaryRequested_ThenReturnHttp200() throws Exception {
		when(this.transactionService.getTransactionSummary()).thenReturn(getValidTransactionSummaryUseCaseResponse());
		this.mockMvc.perform(get("/statistics")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	private int getCreatedResponse() {
		return 201;
	}
	
	private int getNoContentResponse() {
		return 204;
	}

	private TransactionSummary getValidTransactionSummaryUseCaseResponse() {
		TransactionSummary response = new TransactionSummary(100.0, 10.0, 10.0, 10.0, 10l);
		return response;
	}

	/**
	 * @return
	 */
	private Object getValidTransaction() {

		return new AddTransactionRequest(12.3d, new Date().getTime());
	}

	private Object getInValidTransaction() {

		return new AddTransactionRequest(12.3d, new Date().getTime() - (3 * 60 * 1000));
	}
}
