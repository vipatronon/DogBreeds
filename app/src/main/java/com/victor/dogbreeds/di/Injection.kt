package com.victor.dogbreeds.di

object Injection {
    val modules= listOf(
        NetworkModules.instance,
        AppModules.instance
    )
}