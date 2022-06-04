package lucasalfare.fullscreencomposable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

/**
 * Holds the application window dimensions.
 */
private val LocalAppSize = compositionLocalOf { AppSize() }

/**
 * Holds all composables that aims to be drawn in front of the
 * application.
 *
 * Order matters?
 */
val LocalOnTopComposables = compositionLocalOf { OnTopComposables() }

private data class AppSize(var size: IntSize = IntSize.Zero)

data class OnTopComposables(
  var composables: MutableList<@Composable () -> Unit> = mutableListOf()
)

/**
 * This composable works as root entry point to all applications that
 * aims to present [FullScreen] composables.
 *
 * Also, is important to known that "full screen" here should be considered
 * "occupying entire screen of the application", for example, occupying
 * the entire window of a desktop frame (without considering status bar).
 */
@Composable
fun FullScreenHandleableApp(applicationContent: @Composable () -> Unit) {
  var rootSize by remember { mutableStateOf(AppSize()) }

  Box(
    modifier = Modifier
      .onGloballyPositioned {
        rootSize = AppSize(it.size)
      }
  ) {
    CompositionLocalProvider(
      LocalOnTopComposables provides OnTopComposables(),
      LocalAppSize provides rootSize
    ) {
      applicationContent()

      /*
        invokes the pre-added composables
        over the application tree
       */
      Box(modifier = Modifier.fillMaxSize()) {
        LocalOnTopComposables.current.composables.forEach {
          it()
        }
      }
    }
  }
}

/**
 * Composable that can be used to show its [content] over all screen root space.
 */
@Composable
fun FullScreen(
  show: Boolean,
  rootModifier: Modifier = Modifier,
  content: @Composable BoxScope.() -> Unit
) {
  if (show) {
    //the full-screen outer box
    Box(
      modifier = Modifier
        .size(
          width = LocalAppSize.current.size.width.dp,
          height = LocalAppSize.current.size.height.dp
        )
        .then(rootModifier)
    ) {
      content()
    }
  }
}
