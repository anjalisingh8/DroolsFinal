package com.drools.Entity

import java.sql.Timestamp
import java.util.*

data class Transaction (
    var transactionId : Int,
    var transactionDate : Date,
    var transactionTimestamp : Timestamp,
    var transactionCustomerId : Int,
    var transactionBeneficiaryId : Int,
    var transactionAmount : Double,
    var transactionStatus : String,
        ){
}