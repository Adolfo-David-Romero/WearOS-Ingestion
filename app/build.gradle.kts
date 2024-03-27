plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.wearos_ingestion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wearos_ingestion"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //--
    implementation("androidx.concurrent:concurrent-futures-ktx:1.1.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.0.0")
    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.google.accompanist:accompanist-permissions:0.34.0@aar")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    //--

    //implementation ("com.microsoft.azure:azure-hsqldb:1.0.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    // Add the dependencies for any other desired Firebase products
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.android.gms:play-services-location:21.2.0")

    implementation ("com.google.accompanist:accompanist-pager:0.20.0")


    // https://firebase.google.com/docs/android/setup#available-libraries

    //Compose dependencies
    implementation("androidx.wear.compose:compose-foundation:1.3.0")

    // For Wear Material Design UX guidelines and specifications
    implementation("androidx.wear.compose:compose-material:1.3.0")

    // For integration between Wear Compose and Androidx Navigation libraries
    implementation("androidx.wear.compose:compose-navigation:1.3.0")

    // For Wear preview annotations
    implementation("androidx.wear.compose:compose-ui-tooling:1.3.0")
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("com.google.android.gms:play-services-fitness:21.1.0")
    implementation("androidx.compose.material3:material3-android:1.2.1")


    val appcompat_version = "1.6.1"

    implementation("androidx.appcompat:appcompat:$appcompat_version")
    // For loading and tinting drawables on older versions of the platform
    implementation("androidx.appcompat:appcompat-resources:$appcompat_version")

    implementation ("androidx.activity:activity-compose:1.3.0-alpha08")
    implementation ("androidx.compose.ui:ui:1.1.0-alpha06")
    implementation ("androidx.compose.foundation:foundation:1.1.0-alpha06")
    implementation ("androidx.compose.material:material:1.1.0-alpha06")

    // Health Services
    implementation("androidx.health:health-services-client:1.1.0-alpha02")
    implementation("androidx.health.connect:connect-client:1.0.0-alpha11")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("androidx.annotation:annotation:1.7.1")

    implementation("com.google.android.catalog.framework:casa-annotations:0.5.1")
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.wear.compose:compose-material:1.1.2")
    implementation("androidx.wear.compose:compose-foundation:1.1.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.core:core-splashscreen:1.0.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


}