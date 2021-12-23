package com.httpclient.application.connection

import java.net.http.HttpClient

interface Request<T> {
    suspend fun makeRequest(): T
}