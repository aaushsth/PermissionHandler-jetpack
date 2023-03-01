# PermissionHandler-jetpack
## add this library on your app level build.gradle

    ```
        implementation 'com.github.aaushsth:PermissionHandler-jetpack:1.0.1'

    ```

## add this line on settings.gradle
     ```
             maven { url 'https://jitpack.io' }

     ```

 for Example:

        ```
        dependencyResolutionManagement {
            repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
            repositories {
                google()
                mavenCentral()
                maven { url 'https://jitpack.io' }
            }
        }

        ```

## steps to use this component

1. First of all you need to create list of permission you want to take from user eg:

     ```
     val permissions = remember {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    ```

2. Create instance of permission handler and permission state

   ```
          val permissionsHandler = remember(permissions) { PermissionsHandler() }
          val permissionsStates by permissionsHandler.state.collectAsState()
   ```


3. Permission checker function initiation

   ```
       HandlePermissionsRequest(permissions = permissions, permissionsHandler = permissionsHandler)
   ```


4. Now you can call this when you need permission from user. Here is the example from the button click

   ```
         Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (permissionsStates.multiplePermissionsState?.allPermissionsGranted == true) {
            Text(text = "Permission Granted")
        } else {
            Button(onClick = {
                Log.e("click","cliked")
                permissionsHandler.onEvent(PermissionsHandler.Event.PermissionRequired)

            }) {
                Text(text = "Request Permission")
            }
        }
    }

   ```

