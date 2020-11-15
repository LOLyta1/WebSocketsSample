public class Dependency {
    public static final String KOTLIN_VERSION = "1.4.10";
    public static final String KOTLIN_SERIALIZATION_VERSION = "1.0.1";
    public static final String ANDROID_CORE_VERSION = "1.3.2";
    public static final String ANDROID_COROUTINES_VERSION = "1.3.9";

    public static final String HILT_VERSION = "2.28-alpha";
    public static final String HILT_VIEW_MODEL_VERSION = "1.0.0-alpha02";

    public static final String NAVIGATION_VERSION = "2.3.1";

    public static final String MATERIAL_VERSION = "1.2.1";

    public static final String OK_HTTP_VERSION = "4.9.0";
    public static final String SCARLET_VERSION = "0.1.11";
    public static final String SCARLET_ADDONS_VERSION = "0.1.9";

    public static final String TIMBER_VERSION = "4.7.1";
    public static final String JUNIT_VERSION = "4.13.1";
    public static final String JUNIT_TEST_VERSION = "1.1.2";
    public static final String ESPRESSO_VERSION = "3.3.0";

    public static final String kotlin = "org.jetbrains.kotlin:kotlin-stdlib:" + KOTLIN_VERSION;
    public static final String kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:" + KOTLIN_SERIALIZATION_VERSION;
    public static final String androidCore = "androidx.core:core-ktx:" + ANDROID_CORE_VERSION;

    public static final String hilt = "com.google.dagger:hilt-android:" + HILT_VERSION;
    public static final String hiltKapt = "com.google.dagger:hilt-android-compiler:" + HILT_VERSION;
    public static final String hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:" + HILT_VIEW_MODEL_VERSION;
    public static final String kaptHiltViewModel = "androidx.hilt:hilt-compiler:" + HILT_VIEW_MODEL_VERSION;
    public static final String annotationProcessorHiltViewModel = "androidx.hilt:hilt-compiler:" + HILT_VIEW_MODEL_VERSION;

    public static final String navigation = "androidx.navigation:navigation-fragment-ktx:" + NAVIGATION_VERSION;
    public static final String navigationUI = "androidx.navigation:navigation-ui-ktx:" + NAVIGATION_VERSION;

    public static final String material = "com.google.android.material:material:" + MATERIAL_VERSION;

    public static final String okHttp = "com.squareup.okhttp3:okhttp:" + OK_HTTP_VERSION;
    public static final String okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:" + OK_HTTP_VERSION;

    public static final String scarlet = "com.tinder.scarlet:scarlet:" + SCARLET_VERSION;
    public static final String scarletWebsocketOkHttp = "com.tinder.scarlet:websocket-okhttp:" + SCARLET_ADDONS_VERSION;
    public static final String scarletGsonMessageAdapter = "com.tinder.scarlet:message-adapter-gson:" + SCARLET_ADDONS_VERSION;

    public static final String androidCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:" + ANDROID_CORE_VERSION;
    public static final String scarletCoroutinesStreamAdapter = "com.tinder.scarlet:stream-adapter-coroutines:" + SCARLET_ADDONS_VERSION;

    public static final String timber = "com.jakewharton.timber:timber:" + TIMBER_VERSION;
    public static final String JUnit = "junit:junit:" + JUNIT_VERSION;
    public static final String JUnitTest = "androidx.test.ext:junit:" + JUNIT_TEST_VERSION;
    public static final String espresso = "androidx.test.espresso:espresso-core:" + ESPRESSO_VERSION;
}
