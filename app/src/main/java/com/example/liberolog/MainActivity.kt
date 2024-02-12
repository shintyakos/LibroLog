package com.example.liberolog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.example.liberolog.ui.theme.LiberoLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Log.d("MyAmplifyApp", "Initialized Amplify")
        } catch (e: Exception) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify: ${e.message}")
        }

        setContent {
            LiberoLogTheme(dynamicColor = false) {
                LiberoLogApp()
            }
        }
    }
}
