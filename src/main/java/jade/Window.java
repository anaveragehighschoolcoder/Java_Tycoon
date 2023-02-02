package jade;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private int windowWidth, windowHeight;
  private String windowTitle;
  private long glfwWindowAddress;

  private static Window windowInstance = null;

  private Window() {
    this.windowWidth = 1920;
    this.windowHeight = 1080;
    this.windowTitle = "Mario";
  }

  public static Window getWindowInstance() {
    if  (windowInstance == null) {
      windowInstance = new Window();
    }
    return windowInstance;
  }

  public void run() {
    init();
    loop();

    // Free the memory
    glfwFreeCallbacks(glfwWindowAddress);
    glfwDestroyWindow(glfwWindowAddress);

    //Terminate GLFW and free the error callback
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

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

    glfwWindowAddress = glfwCreateWindow(windowWidth, windowHeight, windowTitle, NULL, NULL);
    if (glfwWindowAddress == NULL) {
      throw new IllegalStateException("Failed to create the GLFW window.");
    }

    // Set GLFW input callbacks
    glfwSetCursorPosCallback(glfwWindowAddress, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(glfwWindowAddress, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(glfwWindowAddress, MouseListener::mouseScrollCallback);
    glfwSetKeyCallback(glfwWindowAddress, KeyListener::keyCallback);


    // Make the OpenGL context current
    glfwMakeContextCurrent(glfwWindowAddress);
    // Enable v-sync
    glfwSwapInterval(1);

    glfwShowWindow(glfwWindowAddress);

    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();
  }

  public void loop() {
    while (!glfwWindowShouldClose(glfwWindowAddress)) {
      glfwPollEvents();

      glClear(GL_COLOR_BUFFER_BIT);

      glfwSwapBuffers(glfwWindowAddress);
    }
  }
}
