package lucasalfare.fullscreencomposable

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    /*
      When we need to have something full screen we need
      to call this wrapping the content that makes its calls.

      In this example I'm wrapping my entire example app then
      this means that I can call a [FullScreen] composable
      anywhere inside this tree.
    */
    FullScreenHandleableApp {
      MyExampleApp()
    }
  }
}
