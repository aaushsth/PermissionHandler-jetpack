package com.ayush.permissionchecker

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ayush.permission_handler.permission.HandlePermissionsRequest
import com.ayush.permissionchecker.ui.theme.PermissionCheckerTheme
import com.ayush.permission_handler.permission.PermissionCheckerScreen
import com.ayush.permission_handler.permission.PermissionsHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val permissions = remember {
                listOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }
            val permissionsHandler = remember(permissions) { PermissionsHandler() }
            val permissionsStates by permissionsHandler.state.collectAsState()
            HandlePermissionsRequest(permissions = permissions, permissionsHandler = permissionsHandler)

            PermissionCheckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(

                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (permissionsStates.multiplePermissionsState?.allPermissionsGranted == true) {
                            Text(text = "Permission Granted")
                        } else {
                            Button(onClick = {
                                Log.e("click", "cliked")
                                permissionsHandler.onEvent(PermissionsHandler.Event.PermissionRequired)

                            }) {
                                Text(text = "Request Permission")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PermissionCheckerTheme {
        Greeting("Android")
    }
}