package edu.miu.waa.demo.controller;

import edu.miu.waa.demo.model.Bank;
import edu.miu.waa.demo.model.Book;
import edu.miu.waa.demo.model.CustomErrorType;
import edu.miu.waa.demo.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/banks")
public class BankController {

    private Map<Integer, Bank> banks;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Bank bank){
        if(bank.getAccountNumber() == null && bank.getAccountHolder() == null)
            return new ResponseEntity(new CustomErrorType("Account number or account holder missing"),HttpStatus.FORBIDDEN);
        if(banks.containsKey(bank.getAccountNumber()))
            return new ResponseEntity(new CustomErrorType("Account number already exists"),HttpStatus.FORBIDDEN);
        banks.put(bank.getAccountNumber(),bank);
        return new ResponseEntity(bank, HttpStatus.OK);
    }

    @GetMapping("/deposit/{accountNumber}/{amount}")
    public ResponseEntity<?> deposit(@PathVariable int accountNumber, @PathVariable Double amount){
        if(amount < 1)
            return new ResponseEntity(new CustomErrorType("Deposit amount must be bigger than 0"), HttpStatus.FORBIDDEN);
        Bank bank = banks.get(accountNumber);
        if(bank != null) {
            Transaction transaction = new Transaction("deposit",amount);
            bank.addTransaction(transaction);
            banks.put(accountNumber, bank);
            return new ResponseEntity(bank, HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("Bank account doesn't exists"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/withdraw/{accountNumber}/{amount}")
    public ResponseEntity<?> withdraw(@PathVariable int accountNumber,@PathVariable double amount){
        if(amount < 1)
            return new ResponseEntity(new CustomErrorType("Withdraw amount must be bigger than 0"), HttpStatus.FORBIDDEN);
        Bank bank = banks.get(accountNumber);
        if(bank != null) {

            if(bank.calcBalance() - amount < 0)
                return new ResponseEntity(new CustomErrorType("Insufficient balance"), HttpStatus.FORBIDDEN);

            Transaction transaction = new Transaction("withdraw",-amount);
            bank.addTransaction(transaction);
            banks.put(accountNumber, bank);
            return new ResponseEntity(bank, HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("Bank account doesn't exists"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Book> getAccount(@PathVariable int accountNumber){
        Bank bank = banks.get(accountNumber);
        if(bank == null) {
            return new ResponseEntity(new CustomErrorType("Bank account doesn't exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(bank.toString(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Bank>> getAllBanks(){
        return new ResponseEntity<>(banks.values(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<Bank> removeAccount(@PathVariable int accountNumber){
        if(banks.containsKey(accountNumber)) {
            banks.remove(accountNumber);
            return new ResponseEntity("Account number: "+accountNumber + " deleted", HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("Bank account doesn't exist"), HttpStatus.NOT_FOUND);
    }
}
