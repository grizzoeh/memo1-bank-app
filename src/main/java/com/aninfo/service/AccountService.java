package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }


    
    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {
        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }

        //Bank account promo, get 10% extra in your $2000+ deposits, up to $500
        Double sum_extra = promo(sum);

        Account account = accountRepository.findAccountByCbu(cbu);
        account.setBalance(account.getBalance() + sum + sum_extra);
        accountRepository.save(account);

        return account;
    }

    //function for promo Bank account promo, get 10% extra in your $2000+ deposits, up to $500
    public Double promo(Double sum) {
        Double sum_extra = 0.0;
        if (sum >= 2000) {
            sum_extra = (sum * 0.1);
            if (sum_extra > 500) {
                sum_extra = 500.0;
            }
        }
        return sum_extra;
    }


}
