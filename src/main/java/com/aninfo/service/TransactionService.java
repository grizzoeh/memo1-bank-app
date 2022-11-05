package com.aninfo.service;


import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import com.aninfo.mode.Transaction;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aninfo.exeptions.TransactionNegativeSumException;
import com.aninfo.exeptions.InvalidTransactionTypeException;




@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;


    public Transaction createTransaction(Transaction transaction) {

        //dont allow negative
        if (transaction.getAmount() < 0) {
            throw new NegativeTransactionException("Cannot create a transaction with negative sums");
        }

        if (transaction.getType().equals("deposit")) {
            accountService.deposit(transaction.getCbu(), transaction.getAmount());
        } else if (transaction.getType().equals("withdraw")) {
            accountService.withdraw(transaction.getCbu(), transaction.getAmount());
        }else{
            throw new InvalidTransactionTypeException("Invalid transaction type");
        }
        return transactionRepository.save(transaction);
    }

    public Collection<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public Collection<Transaction> findByCbu(Long cbu) {
        return transactionRepository.findByCbu(cbu);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
   


}
