package com.valhalla.lolchampviewer.tools

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.http.GET

class MockWebServerRuleTest {

    @get:Rule
    val mockServerRule = MockWebserviceRule()

    @Test
    fun testNormalFlow() {
        val api = Retrofit.Builder()
            .baseUrl(mockServerRule.server.url("/"))
            .addConverterFactory(
                Json(JsonConfiguration(ignoreUnknownKeys = true))
                    .asConverterFactory("application/json".toMediaType())
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(TestApi::class.java)

        with(mockServerRule.server) {
            //language=JSON
            enqueue(
                MockResponse().setBody(
                    """
                { "id": "id-1", "data": "hello world" }
            """.trimIndent()
                )
            )

            //language=JSON
            enqueue(
                MockResponse().setBody(
                    """
                { "id": "id-2", "data": "hello world" }
            """.trimIndent()
                )
            )
        }

        assertEquals(TestData(id = "id-1", data = "hello world"),
            runBlocking { api.testMethod() })

        assertEquals(TestData(id = "id-2", data = "hello world"),
            runBlocking { api.testMethod() })
    }

    @Test
    fun testErrorFlow() {
        val api = Retrofit.Builder()
            .baseUrl(mockServerRule.server.url("/"))
            .addConverterFactory(
                Json(JsonConfiguration(ignoreUnknownKeys = true))
                    .asConverterFactory("application/json".toMediaType())
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(TestApi::class.java)

        //language=JSON
        val responseJson = """{"error": "Something bad has been happened"}"""

        with(mockServerRule.server) {
            enqueue(MockResponse().apply {
                setBody(responseJson)
                setResponseCode(500)
            })
        }

        try {
            runBlocking { api.testMethod() }
            fail()
        } catch (ex: HttpException) {
            assertEquals(500, ex.code())
            assertEquals(responseJson, ex.response()?.errorBody()?.string())
        }
    }
}

internal interface TestApi {

    @GET("data")
    suspend fun testMethod(): TestData
}

@Serializable
internal data class TestData(
    val id: String,
    val data: String
)