package lucasalfare.fullscreencomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application


fun main() = application {
  Window(
    state = WindowState(width = 300.dp, height = 300.dp),
    onCloseRequest = ::exitApplication
  ) {
    /*
    Wrapping entire application with the [FullScreenHandleableApplication] composable container
     */
    FullScreenHandleableApplication {
      MyApplication()
    }
  }
}

@Composable
fun MyApplication() {
  Row {
    ThisCanCallFullscreen("1")

    ThisCanCallFullscreen("2")

    ThisCanCallFullscreen("3")
  }
}

/**
Example of a composable that can handle a [FullScreen] call.

As this is the composable that is used to "toggle" the full screen state,
is needed that it defines the full screen content inside itself.

I'm implementing the toggling here only for demonstration,
however, it is not needed. The [FullScreen] composable can be
called everywhere inside the [FullScreenHandleableApplication]
hierarchy.

To do it, just set the `LocalMutableFullScreenState.current.state.value`
to `FullScreenState.Active`, then set it to `FullScreenState.Inactive`
when needs to dismiss it.
 */
@Composable
fun ThisCanCallFullscreen(suffix: String) {
  var localToggleState by remember { mutableStateOf(FullScreenState.Inactive) }

  Button(
    onClick = {
      println("click on $suffix")
      localToggleState = FullScreenState.Active
    },
    modifier = Modifier
  ) {
    Text("click $suffix")
  }

  /**
   * The toggle logic is that the button always sets the
   * local state to Active then, when it is  Active, just
   * defines the target full screen content and sets it
   * to the local composition.
   */
  if (localToggleState == FullScreenState.Active) {
    // first sets the full screen content...
    LocalFullScreenComposableReference
      .current
      .composableReference = {
      IAmUsingFullScreen(suffix)
    }

    // ...second updates the local composition full screen state
    LocalMutableFullScreenState.current.state.value = localToggleState

    // ... then, resets local toggle field, to make incoming recompositions work
    localToggleState = FullScreenState.Inactive
  }
}

/**
 * This is an example of composable that wraps the main [FullScreen] composable.
 *
 * Is recommended always to wrap the main [FullScreen] composable once it properly
 * handles interaction over its containers.
 *
 * In this example is handled toggling but is important to notice that toggling
 * is not needed to make full screen composable.
 */
@Composable
fun IAmUsingFullScreen(suffix: String) {
  /*
  You can try to play with the [maxSizeFraction] to experiment the composable
  acting as a simple Dialog
   */
  FullScreen(innerBoxSizeFraction = 1f) {
    val fullScreenStateRetrieve = LocalMutableFullScreenState.current

    Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
      Button(
        onClick = {
          fullScreenStateRetrieve.state.value = FullScreenState.Inactive
        },
        modifier = Modifier
          .align(Alignment.BottomEnd)
      ) {
        Text("dismiss $suffix")
      }
    }
  }
}
