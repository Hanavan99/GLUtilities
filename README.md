# GLUtilities
## Intro
GLUtilities is a Java library that is designed to make the creation of LWJGL applications easier and faster. The idea behind this project is that creating LWJGL projects that use OpenGL takes a lot of time 
and things like text rendering and textures can seem impossible with all of the configuration that can be used. This library is the solution to that problem, and it makes doing the aforementioned tasks easier as well as many others.

## Features
These are the features that are in the GLUtilities library:
 * Vertex Buffer Object classes
 * Model Manager
 * Shader manager
 * Terrain Generator
 * Text Renderer
 * Texture Manager
 * Framebuffer Object classes
 * Some extra utilities

### Vertex Buffer Object classes
An easy-to-use class that allows you to create a VBO that renders primitives much faster than the standard OpenGL calls.

### Model Manager
Loads and parses all of your .OBJ and .MTL files and automatically loads them into VBOs.

### Shader Manager
Loads and manages all of your shaders so that you can create really neat post-processing effects. Combine this with FBOs and you can make much more elegant visual effects.

### Terrain Generator
A java implementation of Perlin Noise generation, as well as fractal noise to make the terrain more detailed and intricate.

### Text Renderer
Uses java's Swing library to get the shape of any system-installed font and creates VBOs for each character for rendering to the screen for custom GUIs or for in-game text.

### Texture Manager
Automatically uploads textures to be used for models, GUIs, or any other application.

## NYI Features

### Input Handling
Reading the keyboard, mouse, and controllers can be difficult and requires a lot of callbacks. This utility just gives you access to what you need to make input handling a breeze.