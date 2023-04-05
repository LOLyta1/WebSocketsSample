package com.tsivileva.nata.ask;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u000b\u001a\u00020\u0010J\b\u0010\u0011\u001a\u00020\u0010H\u0014J\u0016\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0017\u001a\u00020\u0018R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/tsivileva/nata/ask/AskViewModel;", "Landroidx/lifecycle/ViewModel;", "getAskUseCase", "Lcom/tsivileva/nata/ask/GetAskUseCase;", "(Lcom/tsivileva/nata/ask/GetAskUseCase;)V", "connectionStatus", "Landroidx/lifecycle/LiveData;", "Lcom/tsivileva/nata/core/model/webSocket/ConnectionStatus;", "orders", "Landroidx/lifecycle/MutableLiveData;", "Lcom/tsivileva/nata/core/model/Order;", "getOrders", "()Landroidx/lifecycle/MutableLiveData;", "setOrders", "(Landroidx/lifecycle/MutableLiveData;)V", "disconnect", "", "onCleared", "setCurrenciesAndConnect", "fromCurrency", "Lcom/tsivileva/nata/core/model/Currency;", "toCurrency", "subscribeOnConnectionStatus", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "ask_devDebug"})
public final class AskViewModel extends androidx.lifecycle.ViewModel {
    private final com.tsivileva.nata.ask.GetAskUseCase getAskUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.tsivileva.nata.core.model.Order> orders;
    private androidx.lifecycle.LiveData<com.tsivileva.nata.core.model.webSocket.ConnectionStatus> connectionStatus;

    @androidx.hilt.lifecycle.ViewModelInject()
    public AskViewModel(@org.jetbrains.annotations.NotNull()
                                com.tsivileva.nata.ask.GetAskUseCase getAskUseCase) {
        super();
    }

    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.tsivileva.nata.core.model.Order> getOrders() {
        return null;
    }

    public final void setOrders(@org.jetbrains.annotations.NotNull()
                                        androidx.lifecycle.MutableLiveData<com.tsivileva.nata.core.model.Order> p0) {
    }

    public final void setCurrenciesAndConnect(@org.jetbrains.annotations.NotNull()
                                                      com.tsivileva.nata.core.model.Currency fromCurrency, @org.jetbrains.annotations.NotNull()
                                                      com.tsivileva.nata.core.model.Currency toCurrency) {
    }

    public final void getOrders() {
    }

    public final void disconnect() {
    }

    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.tsivileva.nata.core.model.webSocket.ConnectionStatus> subscribeOnConnectionStatus(@org.jetbrains.annotations.NotNull()
                                                                                                                                           androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        return null;
    }

    @java.lang.Override()
    protected void onCleared() {
    }
}