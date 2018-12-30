package com.example.corot.config

import com.example.corot.handlers.ErrorHandler
import com.example.corot.handlers.TestHandler
import com.example.corot.routers.MainRouter
import com.example.corot.routers.StaticRouter
import com.example.corot.routers.TestRouter
import com.example.corot.service.test.TestGetService
import com.example.corot.service.test.TestGetServiceImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux

@Configuration
@EnableWebFlux
internal class ApplicationConfig {

    @Bean
    internal fun testHandler(testGetService: TestGetService,
                            errorHandler: ErrorHandler) = TestHandler(testGetService, errorHandler)



    @Bean
    internal fun errorHandler() = ErrorHandler()


    @Bean
    internal fun testRouter(testHandler: TestHandler, errorHandler: ErrorHandler) = TestRouter(testHandler, errorHandler)

    @Bean
    internal fun staticRouter() = StaticRouter()

    @Bean
    internal fun mainRouter(staticRouter: StaticRouter, testRouter: TestRouter)
            = MainRouter( staticRouter, testRouter)

    @Bean
    internal fun mainRouterFunction(mainRouter: MainRouter) = mainRouter.doRoute()

    @Bean
    internal fun getTestService(@Value("\${server.testURl}") endPoint: String): TestGetService
            = TestGetServiceImpl(endPoint)


}
