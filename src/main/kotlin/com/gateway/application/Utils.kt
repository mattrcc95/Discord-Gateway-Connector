package com.gateway.application

import com.gateway.application.comunication.outdoor.guildmembers.GuildUser
import java.io.File

object Utils {
    const val alphabet = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"
    val guildFile = File("src/main/resources/guilds.txt")
    val botInfo = File("src/main/resources/botInfo.txt")
    val guildsCount = guildFile.useLines { it.count() }

    fun getRandomString(length: Int = 20) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

     fun getRepresentativeCharOrRandom(usefulMembers: List<Pair<GuildUser, String?>>): String =
        usefulMembers
            .firstOrNull { it.first.username[0] == (it.second?.get(0) ?: it.first.username[0]) }
            ?.let {
                it.first.username[0].toString()
            } ?: getRandomString()
}