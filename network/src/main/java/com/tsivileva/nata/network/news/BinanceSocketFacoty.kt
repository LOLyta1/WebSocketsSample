package com.tsivileva.nata.network.news

import java.io.InputStream
import java.io.OutputStream
import java.net.*
import javax.net.SocketFactory

object BinanceSocketFacoty {

    fun createSocketFactory(): SocketFactory {
       return object : SocketFactory(){
            override fun createSocket(host: String?, port: Int): Socket {
               return Socket(host,port)
            }

            override fun createSocket(
                host: String?,
                port: Int,
                localHost: InetAddress?,
                localPort: Int
            ): Socket {
                return Socket(host,port,localHost,localPort)
            }

            override fun createSocket(host: InetAddress?, port: Int): Socket {
                return Socket()
            }

            override fun createSocket(
                address: InetAddress?,
                port: Int,
                localAddress: InetAddress?,
                localPort: Int
            ): Socket {
                return Socket(address,port,localAddress,localPort)
            }
        }
    }
}