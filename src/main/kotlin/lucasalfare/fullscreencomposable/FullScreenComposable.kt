package lucasalfare.fullscreencomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Enum class representing the only two different states that a full screen composable can have.
 */
enum class FullScreenState {
  /**
   * Indicates that the full screen composable is being shown.
   */
  Active,

  /**
   * Indicates that the full screen composable is not being shown.
   */
  Inactive
}

/**
 * Data class to hold the current [FullScreenHandleableApplication] hierarchy
 * full screen state.
 */
data class MutableFullScreenState(
  var state: MutableState<FullScreenState> = mutableStateOf(FullScreenState.Inactive)
)

/**
 * Data class to hold the current [FullScreenHandleableApplication]
 * full screen composable view.
 */
data class FullScreenComposableRef(
  var composableReference: @Composable () -> Unit = {}
)

/**
 * Exposes a data value representing the full screen state inside the [FullScreenHandleableApplication]
 * hierarchy.
 */
val LocalMutableFullScreenState = compositionLocalOf { MutableFullScreenState() }

/**
 * Exposes a data value representing the target composable that should be treated as
 * full screen inside the [FullScreenHandleableApplication]
 * hierarchy.
 */
val LocalFullScreenComposableReference = compositionLocalOf { FullScreenComposableRef() }

/**
 * Use this composable when working in an application that will launch something fullscreen. This must wrap the [applicationContent] in order to make it work.
 *
 * @param [applicationContent] The main application content.
 */
@Composable
fun FullScreenHandleableApplication(
  applicationContent: @Composable () -> Unit
) {
  val mutableFullScreenState = MutableFullScreenState()
  val fullScreenComposableReference = FullScreenComposableRef()

  CompositionLocalProvider(
    LocalMutableFullScreenState provides mutableFullScreenState,
    LocalFullScreenComposableReference provides fullScreenComposableReference
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
    ) {
      // always draws the main application content
      applicationContent()

      // decides if we must draw the full screen content
      if (mutableFullScreenState.state.value == FullScreenState.Active) {
        fullScreenComposableReference.composableReference()
      }
    }
  }
}

/**
 * This is a pre-made composable used to show full screen content.
 *
 * Notice that full screen content here is defined as occupying entire
 * application window only, not entire device screen/window (mobile/desktop).
 *
 * This composable is composed by two main containers:
 * - an outer box that is set to max size;
 * - an inner box that is the main content wrapper.
 *
 * This is the preferred composable that should be used to full screen,
 * in order it already implements the properly [interactionSource] to outer and
 * inner boxes.
 *
 * This composable can be also used to build simple Dialogs, since outer box
 * can have alpha color to indicate inactive background, and it is also clickable,
 * making the [FullScreen] composable easy to dismiss.
 *
 * @param innerBoxSizeFraction The size fraction that will be applied to the inner box. Valid range to this value is `0f` to `1f`.
 * @param fullScreenContent The content of the [FullScreen].
 */
@Composable
fun FullScreen(
  outerBoxBackground: Color = Color.Gray.copy(alpha = 0.5f),
  innerBoxSizeFraction: Float = 1f,
  fullScreenContent: @Composable BoxScope.() -> Unit // TODO: is extension to `BoxScope` needed?
) {
  val interactionSource = remember { MutableInteractionSource() }
  val fullScreenState = LocalMutableFullScreenState.current

  // outer box; this can perform full screen state handling
  Box(
    modifier = Modifier
      .background(outerBoxBackground)
      .fillMaxSize()
      .clickable {
        fullScreenState.state.value = FullScreenState.Inactive
      }
  ) {
    // inner box; this can not perform full screen state handling
    Box(
      modifier = Modifier
        .fillMaxSize(innerBoxSizeFraction)
        .align(Alignment.Center)
        .clickable(
          interactionSource = interactionSource,
          indication = null
        ) { /* pass */ }
    ) {
      fullScreenContent()
    }
  }
}
