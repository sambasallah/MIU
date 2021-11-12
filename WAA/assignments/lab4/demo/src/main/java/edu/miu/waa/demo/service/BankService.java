package edu.miu.waa.demo.service;

import edu.miu.waa.demo.domain.Bank;
import edu.miu.waa.demo.domain.Transaction;
import edu.miu.waa.demo.repository.BankRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Service
@AllArgsConstructor
public class BankService {

    private BankRepository bankRepository;

    public Bank createAccount(Bank bank) throws Exception {
        if(bank.getAccountNumber() == null && bank.getAccountHolder() == null)
            throw new Exception("Account number or account holder missing");
        if(getAllBanks().containsKey(bank.getAccountNumber()))
            throw new Exception("Account number already exists");
        bankRepository.saveOrUpdate(bank);
        return bank;
    }

    public Bank deposit(int accountNumber, Double amount) throws Exception {
        if(amount < 1)
            throw new Exception("Deposit amount must be bigger than 0");
        Bank bank = getAllBanks().get(accountNumber);
        if(bank != null) {
            Transaction transaction = new Transaction("deposit",amount);
            bank.addTransaction(transaction);
            bankRepository.saveOrUpdate(bank);
            return bank;
        }
        throw new Exception("Bank account doesn't exists");
    }

    public Bank withdraw(int accountNumber, double amount) throws Exception {
        if(amount < 1)
            throw new Exception("Withdraw amount must be bigger than 0");
        Bank bank = getAllBanks().get(accountNumber);
        if(bank != null) {
            if(bank.calcBalance() - amount < 0)
                throw new Exception("Insufficient balance");
            Transaction transaction = new Transaction("withdraw",-amount);
            bank.addTransaction(transaction);
            bankRepository.saveOrUpdate(bank);
            return bank;
        }
        throw new Exception("Bank account doesn't exists");
    }

    public Bank getAccount(int accountNumber) throws Exception {
        Bank bank = getAllBanks().get(accountNumber);
        if(bank == null)
            throw new Exception("Bank account doesn't exist");
        return bank;
    }

    public Map<Integer,Bank> getAllBanks(){
        return bankRepository.findAll();
    }

    public String removeAccount(int accountNumber) throws Exception {
        if(getAllBanks().containsKey(accountNumber)) {
            bankRepository.delete(accountNumber);
            return "Account number: "+accountNumber + " deleted";
        }
        throw new Exception("Bank account doesn't exist");
    }
}
