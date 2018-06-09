package com.spices.inventory

import org.glassfish.jersey.server.ResourceConfig
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.util.function.Consumer
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.ws.rs.Path
import javax.ws.rs.ext.Provider

@Component
internal class ComponentScanningJAXRS @Inject constructor(val applicationContext: ApplicationContext) : ResourceConfig() {

    @PostConstruct
    fun setup() {
        registerEnpoints()
    }

    private fun registerEnpoints() {
        /*
            jersey package scanning doesn't work with spring boot nested jars.
            Look for beans in the spring context and register them instead.
        */
        applicationContext.getBeansWithAnnotation(Provider::class.java).values.forEach(Consumer<Any> { this.register(it) })
        applicationContext.getBeansWithAnnotation(Path::class.java).values.forEach(Consumer<Any> { this.register(it) })
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(ComponentScanningJAXRS::class.java)
    }

}
