# GLUtilities
## Intro
_What is it?_ GLUtilities is a Java library that is designed to make the creation of LWJGL applications easier and faster. This library is mainly targeted for creating games, but it allows for easier creation of 3D modeling applications and other 3D-accelerated applications. This library also makes use of LWJGL 3.x and also targets the latest OpenGL programming styles with increased focus on shader programs and less on making direct GL calls. This library also will include some OpenAL and OpenCL utilities, and possibly even some VR classes. I don't own a VR headset so developing for this platform will be more difficult.

_What is it not?_ This library is NOT a Java implementation of the C and C++ GLU library. This library does however independently include some of its features.

## Features
These are the features that are in the GLUtilities library:
 * __Buffer Objects__ - Makes the use of Frame Buffer Objects, Vertex Buffer Objects and Vertex Array Objects much easier with easy creation and use.
 * __Model Manager__ - Loads and parses all of your .OBJ and .MTL files and automatically loads them into VBOs.
 * __Shader manager__ - Loads and manages all of your shaders so that you can create really neat post-processing effects. Combine this with FBOs and you can make much more elegant visual effects.
 * __Terrain Generator__ - A java implementation of Perlin Noise generation, as well as fractal noise to make the terrain more detailed and intricate.
 * __Text Renderer__ - Uses java's Swing library to draw characters and creates a texture as well as a VBO for drawing each glyph on for the most efficient rendering process.
 * __Texture Manager__ - Automatically uploads textures to be used for models, GUIs, or any other application.
 * __Utility Classes__ - Adds a lot of extra utility classes such as matrix, vertex, and array conversion classes.
