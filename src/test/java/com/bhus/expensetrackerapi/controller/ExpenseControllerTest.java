package com.bhus.expensetrackerapi.controller;

import com.bhus.expensetrackerapi.model.ExpenseCategory;
import com.bhus.expensetrackerapi.model.ExpenseDTO;
import com.bhus.expensetrackerapi.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ExpenseController.class)
public class ExpenseControllerTest {

    @MockBean
    private ExpenseService expenseService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should Create Expense")
    public void shouldCreateExpense() throws Exception {
        ExpenseDTO expenseDto = ExpenseDTO.builder()
                .expenseCategory(ExpenseCategory.ENTERTAINMENT)
                .expenseName("Movies")
                .expenseAmount(BigDecimal.TEN)
                .build();

        Mockito.when(expenseService.addExpense(expenseDto)).thenReturn("123");

        MvcResult mvcResult = mockMvc.perform(post("/api/expense"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION))
                .andReturn();

        assertTrue(mvcResult.getResponse().getHeaderValue(HttpHeaders.LOCATION).toString().contains("123"));

    }
}
