# GLUtilities
## Intro
GLUtilities is a Java library that is designed to make the creation of LWJGL applications easier and faster. The idea behind this project is that creating LWJGL projects that use OpenGL takes a lot of time 
and things like text rendering and textures can seem impossible with all of the configuration that can be used. This library is the solution to that problem, and it makes doing the aforementioned tasks easier as well as many others.
## Features
These are the features that are going to be in the release version of GLUtilities:
 * Native text rendering (TrueType Fonts and OpenType Fonts)
 * Texture creation
 * Shader manager
 * Input handling
 * Custom GUI system

### Native Text Rendering
Allows the user to load custom TrueType or OpenType fonts and draw them natively using lines and curves instead of textures. This has the advantages of taking up less VRAM and allows the text to be scaled to any size without distortion.

### Texture Creation
Makes the creation and loading of textures into VRAM a lot simpler. Instead of having to load the images, convert them into a usable format, make a bunch of enigmatic OpenGL calls, and upload it, you can just upload them with one call.

### Shader Manager
If you've ever wanted to use shaders in an OpenGL application, this utility will make it trivial to write shaders and upload them to the graphics card to make stunning games, scenes, or just a customized lighting engine.

### Input Handling
Reading the keyboard, mouse, and controllers can be difficult and requires a lot of callbacks. This utility just gives you access to what you need to make input handling a breeze.

## Custom GUI system
This system makes drawing GUIs using OpenGL easy. Instead of having separate windows for Swing and AWT applications and OpenGL applications, you can make simple and easy GUIs that are easy to use. This system also makes it easy for you to create your own custom GUI components that suit your personal needs.