package com.valhalla.lolchampviewer.tools

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockWebServerRule(private val port: Int = 8080) : TestRule {

    private var _server: MockWebServer? = null

    val server: MockWebServer
        get() = _server ?: error("No webserver found")

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                _server = MockWebServer()
                _server?.start(port)
                base?.evaluate()
                _server?.shutdown()
                _server = null
            }
        }
    }

    // delegated methods and properties

    // ugly hack, because server.url call produces some network stuff
    fun url(path: String): String = runBlocking {
        withContext(Dispatchers.IO) { server.url(path) }.toString()
    }
}