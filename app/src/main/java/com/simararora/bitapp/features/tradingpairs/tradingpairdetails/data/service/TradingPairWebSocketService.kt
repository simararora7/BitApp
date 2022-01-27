package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.service

import com.google.gson.Gson
import com.simararora.bitapp.BuildConfig
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradingPairDetailResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.WebSocketMessage
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import okhttp3.*
import javax.inject.Inject

@TradingPairDetailScope
class TradingPairWebSocketService @Inject constructor(
    private val gson: Gson
) {
    private lateinit var tradingPairChangesSubject: PublishSubject<TradingPairDetailResponse>

    fun observeTradingPairChanges(symbol: String): Observable<TradingPairDetailResponse> {
        tradingPairChangesSubject = PublishSubject.create()
        val request = Request.Builder()
            .url(BuildConfig.SOCKET_URL)
            .build()

        val listener = TradingPairWebSocketListener(symbol)
        val okHttpClient = OkHttpClient.Builder().build()
        val webSocket = okHttpClient.newWebSocket(request, listener)
        okHttpClient.dispatcher.executorService.shutdown()
        return tradingPairChangesSubject.hide()
            .doFinally {
                webSocket.close(1000, null)
            }
    }

    private fun dispatchMessage(message: String) {
        try {
            val tradingPairDetailResponse =
                gson.fromJson(message, TradingPairDetailResponse::class.java)
            tradingPairChangesSubject.onNext(tradingPairDetailResponse)
        } catch (e: Exception) {
            // No error is propagated to UI
            // Individual message is discarded
        }
    }

    private fun dispatchError(throwable: Throwable) {
        tradingPairChangesSubject.onError(throwable)
    }

    inner class TradingPairWebSocketListener(private val symbol: String) : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            val message = gson.toJson(WebSocketMessage("subscribe", "ticker", symbol))
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
