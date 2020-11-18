package com.tsivileva.nata.ask;

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
public final class AskViewModel_AssistedFactory_Factory implements Factory<AskViewModel_AssistedFactory> {
  private final Provider<GetAskUseCase> getAskUseCaseProvider;

  public AskViewModel_AssistedFactory_Factory(Provider<GetAskUseCase> getAskUseCaseProvider) {
    this.getAskUseCaseProvider = getAskUseCaseProvider;
  }

  @Override
  public AskViewModel_AssistedFactory get() {
    return newInstance(getAskUseCaseProvider);
  }

  public static AskViewModel_AssistedFactory_Factory create(
      Provider<GetAskUseCase> getAskUseCaseProvider) {
    return new AskViewModel_AssistedFactory_Factory(getAskUseCaseProvider);
  }

  public static AskViewModel_AssistedFactory newInstance(Provider<GetAskUseCase> getAskUseCase) {
    return new AskViewModel_AssistedFactory(getAskUseCase);
  }
}
