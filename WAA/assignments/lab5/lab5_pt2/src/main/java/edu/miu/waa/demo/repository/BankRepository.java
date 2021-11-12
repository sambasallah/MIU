package edu.miu.waa.demo.repository;

import edu.miu.waa.demo.domain.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends MongoRepository<Bank, Integer> {
}
