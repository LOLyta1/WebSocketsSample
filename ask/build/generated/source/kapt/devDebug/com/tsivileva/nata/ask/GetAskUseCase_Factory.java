package com.tsivileva.nata.ask;

import android.content.Context;
import com.tsivileva.nata.network.socket.WebSocketClient;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class GetAskUseCase_Factory implements Factory<GetAskUseCase> {
  private final Provider<WebSocketClient> clientProvider;

  private final Provider<Context> contextProvider;

  public GetAskUseCase_Factory(Provider<WebSocketClient> clientProvider,
      Provider<Context> contextProvider) {
    this.clientProvider = clientProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public GetAskUseCase get() {
    return newInstance(clientProvider.get(), contextProvider.get());
  }

  public static GetAskUseCase_Factory create(Provider<WebSocketClient> clientProvider,
      Provider<Context> contextProvider) {
    return new GetAskUseCase_Factory(clientProvider, contextProvider);
  }

  public static GetAskUseCase newInstance(WebSocketClient client, Context context) {
    return new GetAskUseCase(client, context);
  }
}
