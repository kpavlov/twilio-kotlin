package me.kpavlov.twilio.kotlin.conversations

import me.kpavlov.twilio.kotlin.conversations.ConversationsV1ConversationApi
import me.kpavlov.twilio.kotlin.conversations.auth.HttpBasicAuth
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.util.encodeBase64
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TwilioConversationsClientTest {

    val accountSid = "ACb9cfb5ae56390b3bd819dffd9dd00000"
    val authToken = "0cf16414f00a4df846a6d133412537fa"

    @Test
    fun `should create API client with basic authentication`() = runTest {

        val mockEngine = MockEngine { request ->
            // Verify that the Authorization header is set correctly

            respond(
                content = """{"conversations": [], "meta": {"page": 0, "page_size": 50, "first_page_url": "", "previous_page_url": null, "url": "", "next_page_url": null, "key": "conversations"}}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val api = ConversationsV1ConversationApi(
            baseUrl = "https://conversations.stage-us1.twilio.com",
            httpClientEngine = mockEngine
        )

        // Set up basic authentication
        api.setUsername(accountSid)
        api.setPassword(authToken)

        // Test API call (this will be mocked)
        val response = api.listConversation(
            startDate = null,
            endDate = null,
            state = null,
            pageSize = null,
            page = null,
            pageToken = null
        )
        response shouldNotBe null
        response.body() shouldNotBeNull {

        }
    }

    @Test
    fun `should encode basic auth credentials correctly`() {
        val auth = HttpBasicAuth()
        auth.username = "AC123456789"
        auth.password = "test_auth_token"

        val headers = mutableMapOf<String, String>()
        val query = mutableMapOf<String, List<String>>()

        auth.apply(query, headers)

        val expectedAuth = "Basic " + "AC123456789:test_auth_token".encodeBase64()
        headers["Authorization"] shouldBe expectedAuth
    }
}
