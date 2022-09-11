package com.bhus.expensetrackerapi.service;

import com.bhus.expensetrackerapi.Exception.ExpenseNotFoundException;
import com.bhus.expensetrackerapi.model.Expense;
import com.bhus.expensetrackerapi.model.ExpenseDTO;
import com.bhus.expensetrackerapi.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public String addExpense(ExpenseDTO expenseDTO){
        Expense expense= mapFromDTO(expenseDTO);
        return expenseRepository.insert(expense).getId();
    }

    private Expense mapFromDTO(ExpenseDTO expenseDTO) {
        return Expense.builder()
                .expenseName(expenseDTO.getExpenseName())
                .expenseCategory(expenseDTO.getExpenseCategory())
                .expenseAmount(expenseDTO.getExpenseAmount())
                .build();
    }

    public ExpenseDTO getExpenseByName(String name){
        Expense expense= expenseRepository.findByName(name).orElseThrow(() -> new
                ExpenseNotFoundException(String.format("Cannot Find Expense by Name - %s",name)));
        return mapToDTO(expense);
    }

    private ExpenseDTO mapToDTO(Expense expense) {
        return ExpenseDTO.builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .expenseAmount(expense.getExpenseAmount())
                .build();
    }

    public List<ExpenseDTO> getAllExpense(){
        return expenseRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public void updateExpense(ExpenseDTO expenseDTO){
        Expense expense= expenseRepository.findById(expenseDTO.getId()).orElseThrow(() ->
                new RuntimeException(String.format("Cannot find expense by Id= %s",expenseDTO.getId())));

        expense.setExpenseName(expenseDTO.getExpenseName());
        expense.setExpenseCategory(expenseDTO.getExpenseCategory());
        expense.setExpenseAmount(expenseDTO.getExpenseAmount());

        expenseRepository.save(expense);
    }

    public void deleteExpense(String id){
        expenseRepository.deleteById(id);
    }

}
