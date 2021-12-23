package com.httpclient.application.connection

import com.Utils.botInfo
import com.common.HttpUser
import com.httpclient.api.indoor.CreateDMChannelpayload
import com.httpclient.api.outdoor.discordapi.model.dmchannel.DMChannel
import com.httpclient.application.Constants.BASE_URL
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*

class CreateDMChannelRequest(private val httpUser: HttpUser) : Request<DMChannel> {

    private val client: HttpClient = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer()
            accept(ContentType.Application.Json)
        }
    }

    suspend fun getUserWithChannelId() = httpUser.apply { this.channelId = makeRequest().id }

    override suspend fun makeRequest(): DMChannel =
        client.post {
            url("$BASE_URL/users/@me/channels")
            contentType(ContentType.Application.Json)
            headers { append(HttpHeaders.Authorization, botInfo.useLines { it.toList() }[0].trim()) }
            body = CreateDMChannelpayload(id = httpUser.id)
        }

}
