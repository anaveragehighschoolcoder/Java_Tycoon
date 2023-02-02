package jade;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private int width, height;
  private String title;
  private long glfwWindow;

  private float r, g, b, a;

  private static Window window = null;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Mario";
    this.r = 0.f;
    this.g = 0.f;
    this.b = 0.f;
    this.a = 0.f;
  }

  public static Window get() {
    if  (window == null) {
      window = new Window();
    }

    return window;
  }

  public void run() {
    init();
    loop();

    // Free the memory
    glfwFreeCallbacks(glfwWindow);
    glfwDestroyWindow(glfwWindow);

    //Terminate GLFW and free th error callback
    glfwTerminate();
    glfwSetErrorCallback(null);
  }

  public void init() {
    // Setup an error callback
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW.");
    }

    // Configure GLFW
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

    // Create the window
    glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);
    if (glfwWindow == NULL) {
      throw new IllegalStateException("Failed to create the GLFW window.");
    }

    // Set GLFW input callbacks
    glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
    glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);


    // Make the OpenGL context current
    glfwMakeContextCurrent(glfwWindow);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(glfwWindow);

    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context hat is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();
  }

  public void loop() {
    while (!glfwWindowShouldClose(glfwWindow)) {
      // Poll events
      glfwPollEvents();

      glClearColor(r, g, b, a);
      glClear(GL_COLOR_BUFFER_BIT);

      glfwSwapBuffers(glfwWindow);
    }
  }
}