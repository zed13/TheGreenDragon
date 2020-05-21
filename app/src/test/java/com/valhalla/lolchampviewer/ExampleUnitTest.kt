package com.valhalla.lolchampviewer

import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.networkModule
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        startKoin {
            modules(networkModule)
        }
        val api = KoinJavaComponent.getKoin().get<DataDragonApi>()
        runBlocking {
            println("data received ${api.getData()}")
        }
    }
}