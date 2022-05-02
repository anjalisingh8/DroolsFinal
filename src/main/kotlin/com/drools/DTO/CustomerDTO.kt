package com.drools.DTO

import com.drools.Entity.Customer

data class CustomerDTO(
    var customerStatus : String ,
    var transactionStatus : String,
    var customerMessage : String ,
    var transactionMessage : String
) {


}
