package lucasalfare.fullscreencomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Simple example of a composable that occupies the entire application screen.
 */
@Composable
fun SomeFullScreenComposable(show: Boolean, onDismissRequest: () -> Unit) {
  FullScreen(
    show = show,
    rootModifier = Modifier.background(Color.Gray.copy(alpha = 0.5f))
  ) {
    // here we're already inside the full screen scope
    // then we can use the entire screen for anything
    // we want to be full screen
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
    ) {
      Button(onClick = onDismissRequest, modifier = Modifier.align(Alignment.BottomEnd)) {
        Text("Dismiss this full screen composable")
      }
    }
  }
}
