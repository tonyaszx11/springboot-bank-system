package com.bank.bank_system;

import com.bank.bank_system.pojo.Transactions;
import com.bank.bank_system.service.AccountService;
import com.bank.bank_system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankSystemApplicationTests {
	@Autowired
	private AccountService accountService;

	@Test
	void test() {
		Transactions[] transactions = accountService.checkTransactionRecordsByAccountId(2);
		for (Transactions transaction : transactions) {
			System.out.println(transaction);
		}

	}

}
