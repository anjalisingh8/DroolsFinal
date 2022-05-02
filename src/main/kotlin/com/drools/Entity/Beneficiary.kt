package com.drools.Entity

data class Beneficiary (
    var beneficiaryId : Int,
    var beneficiaryName : String,
    var beneficiaryMobileNumber : String,
    var beneficiaryKYC : String,
    var beneficiaryAccountStatus : String,
    var beneficiarySmallPPIMonths : Int,
    var beneficiaryType : String,
    var beneficiaryTransactionType : String,
    var beneficiaryTransactionStatus : String,
        ){
}