package com.github.mj.account.open

import org.springframework.stereotype.Repository

interface AccountRepository {
    fun getAccount(id: String): Account?
    fun persist(account: Account)
}

@Repository
class AccountRepositoryImpl : AccountRepository {
    private val accounts = HashMap<String, Account>()

    override fun persist(account: Account) {
        println("Persisting account: $account")
        accounts[account.id] = account
    }

    override fun getAccount(id: String): Account? {
        return accounts[id]
    }
}