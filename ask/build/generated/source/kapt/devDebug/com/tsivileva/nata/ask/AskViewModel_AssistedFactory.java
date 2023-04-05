package com.tsivileva.nata.ask;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelAssistedFactory;
import androidx.lifecycle.SavedStateHandle;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("androidx.hilt.AndroidXHiltProcessor")
public final class AskViewModel_AssistedFactory implements ViewModelAssistedFactory<AskViewModel> {
    private final Provider<GetAskUseCase> getAskUseCase;

    @Inject
    AskViewModel_AssistedFactory(Provider<GetAskUseCase> getAskUseCase) {
        this.getAskUseCase = getAskUseCase;
    }

    @Override
    @NonNull
    public AskViewModel create(SavedStateHandle arg0) {
        return new AskViewModel(getAskUseCase.get());
    }
}
