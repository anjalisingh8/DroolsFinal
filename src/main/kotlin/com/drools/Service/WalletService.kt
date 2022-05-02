package com.drools.Service


import com.drools.Config.DroolConfig
import com.drools.DTO.CustomerDTO
import com.drools.Entity.Beneficiary
import com.drools.Entity.Customer
import org.kie.api.KieServices
import org.kie.api.event.rule.AfterMatchFiredEvent
import org.kie.api.event.rule.BeforeMatchFiredEvent
import org.kie.api.event.rule.DefaultAgendaEventListener
import org.kie.api.runtime.KieSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class WalletService (
    private val kieSession : KieSession ,
    val droolConfig : DroolConfig,

        ){

    //Array of String to store the fired rules.
    val allRulesFiredNames: MutableList<String> = ArrayList()


    //Value Annotation of Configurable parameter present in the application.properties.
    @Value("\${DefaultDailyTransactionLimit}")
    var defaultDailyTranLimit : Double = 0.0


    @Value("\${DefaultMonthlyTransactionLimit}")
    var defaultMonthlyTranLimit : Double = 0.0


    @Value("\${DefaultPerTransactionLimit}")
    var defaultPerTranLimit : Double = 0.0

    /*@Value("\${MaximumDailyTransactionLimit}")
    var maximumDailyTranLimit : Double = 0.0

    @Value("\${MaximumMonthlyTransactionLimit}")
    var maximumMonthlyTranLimit : Double = 0.0


    @Value("\${MaximumPerTransactionLimit}")
    var maximumPerTranLimit : Double = 0.0

    @Value("\${TransactionAmountRequireAuth}")
    var tranAmountRequireAuth : Double = 0.0

    @Value("\${MinimumAccountBalance}")
    var minAccountBalance : Double = 0.0

    @Value("\${DailyTransactionNumber}")
    var dailyTranNumber : Int = 0

    @Value("\${MonthlyTransactionNumber}")
    var monthlyTranNumber : Int = 0

     */

    val kieServices : KieServices = KieServices.Factory.get()


    //logger to log all the actions into MyApp.log file , configuration is in the log4j2.xml file in resources.
    val logger: Logger = LoggerFactory.getLogger("WalletService")
    /*val kieServices : KieServices = KieServices.Factory.get()
    var releaseId = kieServices.newReleaseId("com.drools","DroolsFinal","1.0-SNAPSHOT")
    var kieContainer = kieServices.newKieContainer(releaseId)

     */


    //function for adding rules to new customer using CustomerRules.drl .
    fun addNewCustomer(customer: Customer):Customer{
        /*var rinds : Int = (0..100000).random()
        rinds = rinds + 100000
        println("${rinds}")
         */

        if(customer.customerCustomDailyLimit == 0.0) {
            customer.customerCustomDailyLimit = defaultDailyTranLimit
        }

        if(customer.customerCustomMonthlyLimit == 0.0)
        {
            customer.customerCustomMonthlyLimit = defaultMonthlyTranLimit
        }
        if(customer.customerCustomPerTransactionLimit == 0.0)
        {
            customer.customerCustomPerTransactionLimit = defaultPerTranLimit
        }

        if (kieSession != null) {
            eventListener(kieSession)
        }
        println("$defaultDailyTranLimit")
        //Inserting global variable into KieSession containing rule files.
        //kieSession.setGlobal("MaximumDailyTranLimit",maximumDailyTranLimit)
        //kieSession.setGlobal("MaximumMonthlyTranLimit",maximumMonthlyTranLimit)
        //kieSession.setGlobal("MaximumPerTransactionLimit",maximumPerTranLimit)
        //kieSession.setGlobal("TransactionAmountRequireAuth",tranAmountRequireAuth)
        //kieSession.setGlobal("MinAccountBalance",minAccountBalance)
        logger.info("Before Inserting the Object into the Session")
        //Inserting object into the KieSession.
        kieSession?.insert(customer)
        logger.info("After Inserting the Object into the Session")
        logger.info("Before Firing All the Rules")
        //Firing all rules in the Session.
        var totalRulesFired : Int = kieSession?.fireAllRules()
        logger.info("After Firing All the Rules")
        logger.info("Total Rules Fired - $totalRulesFired")
        allRulesFiredNames.add("|")
        logger.info("Message generated - ${customer.customerFundTransferStatus}")
        println(allRulesFiredNames)
        return customer

    }

    //function for adding rules to new customer using BeneficiaryRules.drl .
    fun addNewBeneficiary(beneficiary: Beneficiary):Beneficiary{
        if (kieSession != null) {
            eventListener(kieSession)
        }
        logger.info("Before Inserting the Object into the Session")
        //Inserting object into the kieSession.
        kieSession?.insert(beneficiary)
        logger.info("After Inserting the Object into the Session")
        logger.info("Before Firing All the Rules")
        //Firing all rules in the session.
        kieSession?.fireAllRules()
        logger.info("After Firing All the Rules")
        println(allRulesFiredNames)
        return beneficiary
    }

    fun EntityToDTO(customer: Customer): CustomerDTO
    {
        var customerDTO = CustomerDTO("","","","")
        customerDTO.customerStatus = customer.customerType
        customerDTO.customerMessage = customer.customerTypeMessage
        customerDTO.transactionStatus = customer.customerFundTransferStatus
        customerDTO.transactionMessage = customer.customerFundTransferMessage
        return customerDTO
    }

    //Function containing AgendaEventListener for getting the info of rule to be fired and rules fired after matching
    fun eventListener(kSession: KieSession)
    {
        //Before match fired AgendaEventListener to get name of the rule to be fired.
        kSession?.addEventListener(object : DefaultAgendaEventListener(){
            override fun beforeMatchFired(event: BeforeMatchFiredEvent?) {
                super.beforeMatchFired(event)
                logger.info("Rule to be fired is : ${event?.match?.rule?.name}")
                logger.info("Timer Started")
                //println("Rule to be fired is : ${event?.match?.rule?.name}")
                //println("Timer Started")
                var startTime = LocalDateTime.now()
                logger.info("$startTime")
                //println("${startTime}")
            }
        })

        //After match fired AgendaEventListener to get names of fired rules.
        kSession?.addEventListener(object : DefaultAgendaEventListener(){
            override fun afterMatchFired(event: AfterMatchFiredEvent?) {
                super.afterMatchFired(event)
                logger.info("rule fired name is : ${event?.match?.rule?.name}")
                logger.info("Timer Stopped")
                //println("rule fired name is : ${event?.match?.rule?.name}")
                //println("Timer Stoped")
                var endTime: LocalDateTime = LocalDateTime.now()
                logger.info("$endTime")
                //allRulesFiredNames.add("${event?.match?.rule?.name}")
                println("${event?.match?.rule?.id}")
                allRulesFiredNames.add("${event?.match?.rule?.id}")
                //println("${endTime}")
            }
        })
    }


}