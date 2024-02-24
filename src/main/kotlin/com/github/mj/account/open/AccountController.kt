package com.github.mj.account.open

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(val service: AccountService) {

    @PostMapping("/account")
    fun open(@RequestBody account: Account): String {
        service.open(account)
        return "OK"
    }

    @GetMapping("/account/{id}")
    fun getAccount(@PathVariable id: String): Account {
        return service.getAccount(id)
    }
}