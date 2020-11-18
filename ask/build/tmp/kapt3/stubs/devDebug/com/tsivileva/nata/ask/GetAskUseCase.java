package com.tsivileva.nata.ask;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\u000bJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u0016\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/tsivileva/nata/ask/GetAskUseCase;", "", "client", "Lcom/tsivileva/nata/network/socket/WebSocketClient;", "context", "Landroid/content/Context;", "(Lcom/tsivileva/nata/network/socket/WebSocketClient;Landroid/content/Context;)V", "from", "Lcom/tsivileva/nata/core/model/Currency;", "to", "connectToServer", "", "disconectFromServer", "getData", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tsivileva/nata/core/model/Order;", "setCurrency", "subscribeConnectionStatus", "Landroidx/lifecycle/LiveData;", "Lcom/tsivileva/nata/core/model/webSocket/ConnectionStatus;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "ask_devDebug"})
public final class GetAskUseCase {
    private com.tsivileva.nata.core.model.Currency from;
    private com.tsivileva.nata.core.model.Currency to;
    private final com.tsivileva.nata.network.socket.WebSocketClient client = null;
    private final android.content.Context context = null;
    
    public final void setCurrency(@org.jetbrains.annotations.NotNull()
    com.tsivileva.nata.core.model.Currency from, @org.jetbrains.annotations.NotNull()
    com.tsivileva.nata.core.model.Currency to) {
    }
    
    public final void connectToServer() {
    }
    
    public final void disconectFromServer() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.tsivileva.nata.core.model.Order> getData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.tsivileva.nata.core.model.webSocket.ConnectionStatus> subscribeConnectionStatus(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope) {
        return null;
    }
    
    @javax.inject.Inject()
    public GetAskUseCase(@org.jetbrains.annotations.NotNull()
    com.tsivileva.nata.network.socket.WebSocketClient client, @org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        super();
    }
}