# FullScreen Composable

Since we don't have (at least until now June/2022) a dedicated fullscreen composable function in the standard
JetpackCompose library, I tried to build this one that acts as one.

This idea works by creating a composable that "draws" on top of the root application composables. Doing this, we can
define some modifiers to make it, for example full opaque, making it overlap the other ones. Also, if we set its
background to something with alpha, then we can go to a simple implementation of an `Dialog`, `Popup`, `Context Menu` or
even a `Alert`.

# Example

See [SomeFullScreenComposable.kt](src/main/kotlin/lucasalfare/fullscreencomposable/SomeFullScreenComposable.kt) file.
