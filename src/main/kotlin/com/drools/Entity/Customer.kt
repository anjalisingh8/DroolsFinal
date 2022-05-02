package com.drools.Entity

data class Customer(
    var customerId : Int,
    var customerName : String,
    var customerMobileNumber : String,
    var customerCountry : String,
    var customerKYC : String,
    var customerAccountNumber : Long,
    var customerAccountStatus : String,
    var customerAccountBalance : Double,
    var customerTransactionType : String,
    var customerTransactionAmount : Double,
    var customerFundTransferStatus : String,
    var customerFundTransferMessage : String,
    var customerType : String,
    var customerTypeMessage : String,
    var customerCustomMonthlyLimit : Double,
    var customerCustomDailyLimit: Double,
    var customerCustomPerTransactionLimit : Double

) {
}