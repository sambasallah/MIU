package edu.miu.waa.demo.controller;

import edu.miu.waa.demo.domain.Bank;
import edu.miu.waa.demo.domain.Transaction;
import edu.miu.waa.demo.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/banks")
public class BankController {

    private final BankService bankService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody @Valid Bank bank){
        try {
            return new ResponseEntity(bankService.createAccount(bank), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/deposit/{accountNumber}/{amount}")
    public ResponseEntity<?> deposit(@PathVariable int accountNumber, @PathVariable Double amount){
        if(amount < 1)
            return new ResponseEntity("Deposit amount must be bigger than 0", HttpStatus.FORBIDDEN);
        Bank bank = bankService.getAllBanks().get(accountNumber);
        if(bank != null) {
            Transaction transaction = new Transaction("deposit",amount);
            bank.addTransaction(transaction);
            bankService.getAllBanks().put(accountNumber, bank);
            return new ResponseEntity(bank, HttpStatus.OK);
        }
        return new ResponseEntity("Bank account doesn't exists", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/withdraw/{accountNumber}/{amount}")
    public ResponseEntity<?> withdraw(@PathVariable int accountNumber,@PathVariable double amount){
        if(amount < 1)
            return new ResponseEntity("Withdraw amount must be bigger than 0", HttpStatus.FORBIDDEN);
        Bank bank = bankService.getAllBanks().get(accountNumber);
        if(bank != null) {
            if(bank.calcBalance() - amount < 0)
                return new ResponseEntity("Insufficient balance", HttpStatus.FORBIDDEN);

            Transaction transaction = new Transaction("withdraw",-amount);
            bank.addTransaction(transaction);
            bankService.getAllBanks().put(accountNumber, bank);
            return new ResponseEntity(bank, HttpStatus.OK);
        }
        return new ResponseEntity("Bank account doesn't exists", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Bank> getAccount(@PathVariable int accountNumber) throws Exception {
        Bank bank = bankService.getAccount(accountNumber);
        if(bank == null) {
            return new ResponseEntity("Bank account doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(bank.toString(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Bank>> getAllBanks(){
        return new ResponseEntity<>(bankService.getAllBanks().values(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<Bank> removeAccount(@PathVariable int accountNumber) throws Exception {
        if(bankService.getAllBanks().containsKey(accountNumber)) {
            bankService.removeAccount(accountNumber);
            return new ResponseEntity("Account number: "+accountNumber + " deleted", HttpStatus.OK);
        }
        return new ResponseEntity("Bank account doesn't exist", HttpStatus.NOT_FOUND);
    }
}
