package lucasalfare.fullscreencomposable

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.remember

@Composable
fun MyExampleApp() {
  /*
    I created this only to demonstrate the full toggle.
    Not really needed.
   */
  var showMyFullScreen by remember { mutableStateOf(false) }

  Button(onClick = {
    showMyFullScreen = !showMyFullScreen
  }) {
    Text("Show The Full Screen")
  }

  /*
  Let's say we want handle a "full screen" call here,
  then we just need to add it to the composables list
  using the [LocalOnTopComposables] provider, but...

  ...this crazily adds composables directly inside THIS
  composable scope? How to improve? o.o
   */
  LocalOnTopComposables.current.composables.add {
    SomeFullScreenComposable(
      show = showMyFullScreen,
      onDismissRequest = { showMyFullScreen = false }
    )
  }
}
