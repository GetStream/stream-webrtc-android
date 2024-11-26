/*
 * Copyright 2023 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.getstream.Configurations

plugins {
  id("com.android.test")
  id("org.jetbrains.kotlin.android")
  id(libs.plugins.baseline.profile.get().pluginId)
}

android {
  namespace = "io.getstream.webrtc.android.benchmark"
  compileSdk = Configurations.compileSdk

  defaultConfig {
    minSdk = 24
    targetSdk = Configurations.targetSdk
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  buildFeatures {
    buildConfig = true
  }

  targetProjectPath = ":app"
  experimentalProperties["android.experimental.self-instrumenting"] = true
  testOptions.managedDevices.devices {
    maybeCreate<com.android.build.api.dsl.ManagedVirtualDevice>("pixel6Api33").apply {
      device = "Pixel 6"
      apiLevel = 33
      systemImageSource = "aosp"
    }
  }
}

// This is the plugin configuration. Everything is optional. Defaults are in the
// comments. In this example, you use the GMD added earlier and disable connected devices.
baselineProfile {
  // This specifies the managed devices to use that you run the tests on.
  managedDevices.clear()
  managedDevices += "pixel6Api33"

  // This enables using connected devices to generate profiles. The default is true.
  // When using connected devices, they must be rooted or API 33 and higher.
  useConnectedDevices = true
}

dependencies {
  implementation(libs.androidx.test.runner)
  implementation(libs.androidx.test.uiautomator)
  implementation(libs.androidx.benchmark.macro)
  implementation(libs.androidx.profileinstaller)
}
