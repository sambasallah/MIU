package edu.miu.waa.demo.repository;

import edu.miu.waa.demo.domain.Bank;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BankRepository {

    private Map<Integer, Bank> bankAccounts = new HashMap<>();

    public void saveOrUpdate(Bank bank){
        bankAccounts.put(bank.getAccountNumber(),bank);
    }
    public void delete(Integer isbn){
        bankAccounts.remove(isbn);
    }
    public Map<Integer,Bank> findAll(){
        return bankAccounts;
    }
}
