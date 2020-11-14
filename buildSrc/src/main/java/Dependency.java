public class Dependency {
    private static final String KOTLIN_VERSION = "1.4.10";
    private static final String ANDROID_CORE_VERSION = "1.3.2";
    private static final String MATERIAL_VERSION = "1.2.1";
    private static final String SCARLET_VERSION = "0.1.11";
    private static final String OK_HTTP_VERSION = "4.9.0";

    private static final String JUNIT_VERSION = "4.13.1";
    private static final String JUNIT_TEST_VERSION = "1.1.2";
    private static final String ESPRESSO_VERSION = "3.3.0";

    public static final String kotlin = "org.jetbrains.kotlin:kotlin-stdlib:" + KOTLIN_VERSION;
    public static final String androidCore = "androidx.core:core-ktx:" + ANDROID_CORE_VERSION;

    public static final String material = "com.google.android.material:material:" + MATERIAL_VERSION;

    public static final String scarlet = "com.tinder.scarlet:scarlet:" + SCARLET_VERSION;
    public static final String OkHTTP = "com.squareup.okhttp3:okhttp:" + OK_HTTP_VERSION;

    public static final String JUnit = "junit:junit:" + JUNIT_VERSION;
    public static final String JUnitTest = "androidx.test.ext:junit:" + JUNIT_TEST_VERSION;
    public static final String espresso = "androidx.test.espresso:espresso-core:" + ESPRESSO_VERSION;
}
