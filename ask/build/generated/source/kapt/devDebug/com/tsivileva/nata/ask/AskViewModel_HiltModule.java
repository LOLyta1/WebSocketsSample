package com.tsivileva.nata.ask;

import androidx.hilt.lifecycle.ViewModelAssistedFactory;
import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.codegen.OriginatingElement;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(ActivityRetainedComponent.class)
@OriginatingElement(
    topLevelClass = AskViewModel.class
)
public interface AskViewModel_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.tsivileva.nata.ask.AskViewModel")
  ViewModelAssistedFactory<? extends ViewModel> bind(AskViewModel_AssistedFactory factory);
}
