package com.gateway.connection

import java.io.File

object Utils {
    const val alphabet = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"
    val guildFile = File("src/main/resources/guilds.txt")
    val guildsCount = guildFile.useLines { it.count() }
    const val START = 0

    fun getRandomString(length: Int = 20) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}