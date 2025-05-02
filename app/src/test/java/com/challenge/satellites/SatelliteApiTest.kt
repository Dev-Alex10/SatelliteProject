package com.challenge.satellites

import com.challenge.satellites.data.remote.SatelliteApi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SatelliteApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var satelliteApi: SatelliteApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        satelliteApi = retrofit.create(SatelliteApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCollection returns expected data`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setBody("""{"member": [{"satelliteId": 1,"name": "Satellite1"}]}""".trimIndent())
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = satelliteApi.getCollection("", "name", "asc")

        // Assert
        assertEquals(1, response.member[0].satelliteId)
        assertEquals("Satellite1", response.member[0].name)
    }

    @Test
    fun `getSatelliteDetails returns expected data`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setBody("""{"satelliteId": 1, "name": "Satellite 1"}""")
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = satelliteApi.getSatelliteDetails(1)

        // Assert
        assertEquals(1, response.satelliteId)
        assertEquals("Satellite 1", response.name)
    }

    @Test
    fun `getCollection handles error response`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        // Act & Assert
        try {
            satelliteApi.getCollection("", "name", "asc")
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("404"))
        }
    }

    @Test
    fun `getCollection handles empty response`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setBody("""{"member": []}""".trimIndent())
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = satelliteApi.getCollection("test", "name", "asc")

        // Assert
        assertTrue(response.member.isEmpty())
    }

    @Test
    fun `getSatelliteDetails handles error response`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        // Act & Assert
        try {
            satelliteApi.getSatelliteDetails(1)
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("404"))
        }
    }
}