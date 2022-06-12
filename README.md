# FullScreen Composable

Since we don't have (at least until now June/2022) a dedicated fullscreen composable function in the standard
JetpackCompose library, I tried to build this one that acts in this behavior.

This idea works by creating a composable that "draws" on top of the root application composables. Doing this, we can
define some modifiers to make it, for example, full opaque, making it overlap the other ones. Also, if we set its
background to something with alpha, then we can go to a simple implementation of an `Dialog`, `Popup`, `Context Menu` or
even a `Alert`.

Also, is important to note that I'm considering "full screen" "occupying max application size" and not the "entire
device screen". This means that elements such as status bar in Android mobile should still visible.

# Example

To see an example of application that can show something full screen, go
to [MyApplicationExample.kt](src/main/kotlin/lucasalfare/fullscreencomposable/MyApplicationExample.kt) file.

---

**Note:** This repository was built with Jetpack Compose with some _Desktop_ dependencies. However, since it is not using
elements specific to Desktop it should be easy to port to Mobile Android by just copying/pasting
the [FullScreenComposable.kt](src/main/kotlin/lucasalfare/fullscreencomposable/FullScreenComposable.kt) file. Multiplatform should be implemented in the future.