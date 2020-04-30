
object Deps {

    private const val kotlinVersion = "1.3.71"

    val Kotlin = object {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    }

    val AndroidX = object {
        private val lifecycle_version = "2.2.0"
        private val arch_version = "2.1.0"

        val appCompat ="androidx.appcompat:appcompat:1.1.0"
        val coreKtx = "androidx.core:core-ktx:1.0.2"
        val activity = "androidx.activity:activity-ktx:1.1.0"

        val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
        val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
        val savedStateViewModel = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
    }

    val Ui = object {
        val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    }

    val Plugins = object {
        val androidGradle = "com.android.tools.build:gradle:3.6.3"
        val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:1.5.2.0"
    }

    val Test = object {
        val kluent = "org.amshove.kluent:kluent:1.59"
        val mockk = "io.mockk:mockk:1.9.3"
        val junit5Api = "org.junit.jupiter:junit-jupiter-api:5.3.2"
        val junit5engine = "org.junit.jupiter:junit-jupiter-engine:5.3.2"
    }

}