package com.drools.Config

import org.kie.api.KieServices
import org.kie.api.builder.KieFileSystem
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.KieSession
import org.kie.internal.io.ResourceFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException

@Configuration
class DroolConfig {

    private val kieServices = KieServices.Factory.get();

    @Throws(IOException::class)
    fun getKieFileSystem() : KieFileSystem?{
        val fileSystem = kieServices.newKieFileSystem()
        fileSystem.write(ResourceFactory.newClassPathResource("Rules/CustomerRules.drl"))
        fileSystem.write(ResourceFactory.newClassPathResource("Rules/BeneficiaryRules.drl"))

        return fileSystem
    }

    @Bean
    @Throws(IOException::class)
    fun getContainer() : KieContainer?{
        println("Container created")
        getKieRepository()
        var kb = kieServices.newKieBuilder(getKieFileSystem())
        kb.buildAll()
        val kieModule = kb.kieModule
        return kieServices.newKieContainer(kieModule.releaseId)
    }

    private fun getKieRepository() {
        val kieRepository = kieServices.repository
        kieRepository.addKieModule{
            kieRepository.defaultReleaseId
        }
    }

    @Bean
    @Throws(IOException::class)
    fun getKieSession() : KieSession?{
        println("Session")
        return getContainer()?.newKieSession()
    }
}