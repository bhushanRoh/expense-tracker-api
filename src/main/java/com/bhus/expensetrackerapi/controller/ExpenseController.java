package com.bhus.expensetrackerapi.controller;

import com.bhus.expensetrackerapi.Exception.ExpenseNotFoundException;
import com.bhus.expensetrackerapi.model.Expense;
import com.bhus.expensetrackerapi.model.ExpenseDTO;
import com.bhus.expensetrackerapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Void> addExpense(@RequestBody ExpenseDTO expenseDTO){
        String expenseId= expenseService.addExpense(expenseDTO);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(expenseId)
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseDTO getExpenseByName(@PathVariable String name){

            return expenseService.getExpenseByName(name);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExpenseDTO> getAllExpense(){
        return expenseService.getAllExpense();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateExpense(@RequestBody ExpenseDTO expense){
        expenseService.updateExpense(expense);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable String id){
        expenseService.deleteExpense(id);
    }
}
