package com.drools.Controller

import com.drools.DTO.CustomerDTO
import com.drools.Entity.Beneficiary
import com.drools.Entity.Customer
import com.drools.Service.WalletService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/wallet")
class WalletController (
    private val walletService : WalletService
){


    @GetMapping("/home")
    fun homePage():String{
        return "Welcome to Wallet To Wallet Transfer Home Page"
    }

    /*@PostMapping("/Customer")
    fun newCustomer(@RequestBody customer: Customer): Customer? {
        return walletService?.addNewCustomer(customer)
    }
    */

    @PostMapping("/Customer")
    fun newCustomer(@RequestBody customer: Customer): CustomerDTO? {
        var newCustomer: Customer = walletService?.addNewCustomer(customer)
        return walletService.EntityToDTO(newCustomer)
    }

    @PostMapping("/Beneficiary")
    fun newBeneficiary(@RequestBody beneficiary: Beneficiary): Beneficiary? {
        return walletService?.addNewBeneficiary(beneficiary)
    }

}