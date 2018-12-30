package com.example.corot.routers

internal class MainRouter( val staticRouter: StaticRouter, val testRouter: TestRouter) {

  fun doRoute() = testRouter.doRoute()
    .andOther(staticRouter.doRoute())

}


