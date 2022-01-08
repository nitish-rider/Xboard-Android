package com.xboard.xboardandroid.Utils

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder

object API {
    val api: DiscordApi by lazy {
        val api = DiscordApiBuilder().setToken(CONSTANTS.BOT_KEY).login().join()
        api
    }
}