package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.service

import com.google.gson.Gson
import com.simararora.bitapp.BuildConfig
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.WebSocketMessage
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import okhttp3.*
import javax.inject.Inject

@TradingPairDetailScope
class TradesWebSocketService @Inject constructor(
    private val gson: Gson
) {
    private lateinit var tradesChangeSubject: PublishSubject<TradeResponse>

    fun observeTradeChanges(symbol: String): Observable<TradeResponse> {
        tradesChangeSubject = PublishSubject.create()
        val request = Request.Builder()
            .url(BuildConfig.SOCKET_URL)
            .build()

        val listener = TradesSocketListener(symbol)
        val okHttpClient = OkHttpClient.Builder().build()
        val webSocket = okHttpClient.newWebSocket(request, listener)
        okHttpClient.dispatcher.executorService.shutdown()
        return tradesChangeSubject.hide()
            .doFinally {
                webSocket.close(1000, null)
            }
    }

    private fun dispatchMessage(message: String) {
        try {
            val tradeResponse =
                gson.fromJson(message, TradeResponse::class.java)
            tradesChangeSubject.onNext(tradeResponse)
        } catch (e: Exception) {
            // No error is propagated to UI
            // Individual message is discarded
        }
    }

    private fun dispatchError(throwable: Throwable) {
        tradesChangeSubject.onError(throwable)
    }

    inner class TradesSocketListener(private val symbol: String) : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            val message = gson.toJson(WebSocketMessage("subscribe", "trades", symbol))
            webSocket.send(message)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            dispatchMessage(text)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            dispatchError(t)
        }
    }
}
