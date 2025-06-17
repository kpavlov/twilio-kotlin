package com.twilio.conversations.api

import io.ktor.http.ParametersBuilder

fun ParametersBuilder.append(key: String, value:String) {
    this.append(key, value)
}
