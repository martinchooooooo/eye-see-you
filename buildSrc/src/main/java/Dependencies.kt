object Dependencies {

    private const val kotlinVersion = "1.3.71"

    val Kotlin = object {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    }

    val AndroidX = object {
        val appCompat ="androidx.appcompat:appcompat:1.1.0"
        val coreKtx = "androidx.core:core-ktx:1.0.2"
    }

    val Plugins = object {
        val androidGradle = "com.android.tools.build:gradle:3.6.3"
        val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    }

}