package jade;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
  private static MouseListener mouseListenerInstance;
  private double mouseScrollX, mouseScrollY;
  private double mouseXPosition, mouseYPosition, lastMouseXPosition, lastMouseYPosition;
  private final boolean[] mouseButtonPressed = new boolean[3];
  private boolean isMouseDragging;

  private static final int LEFT_MOUSE_BUTTON_ID = 0;
  private static final int RIGHT_MOUSE_BUTTON_ID = 1;
  private static final int MIDDLE_MOUSE_BUTTON_ID = 2;


  private MouseListener() {
    this.mouseScrollX = 0.0;
    this.mouseScrollY = 0.0;
    this.mouseXPosition = 0.0;
    this.mouseYPosition = 0.0;
    this.lastMouseXPosition = 0.0;
    this.lastMouseYPosition = 0.0;
  }

  public static MouseListener getMouseListenerInstance() {
    if (mouseListenerInstance == null) {
      mouseListenerInstance = new MouseListener();
    }
    return mouseListenerInstance;
  }

  public static void mousePosCallback(long glfwWindowAddress, double newMouseXPosition, double newMouseYPosition) {
    getMouseListenerInstance().lastMouseXPosition = getMouseListenerInstance().mouseXPosition;
    getMouseListenerInstance().lastMouseYPosition = getMouseListenerInstance().mouseYPosition;
    getMouseListenerInstance().mouseXPosition = newMouseXPosition;
    getMouseListenerInstance().mouseYPosition = newMouseYPosition;
    getMouseListenerInstance().isMouseDragging = isMouseDragging();
  }

  public static void mouseButtonCallback(long glfwWindowAddress, int mouseButtonID, int mouseActionID, int glfwMods) {
    if (mouseButtonID < getMouseListenerInstance().mouseButtonPressed.length) {
      if (mouseActionID == GLFW_PRESS) {
        getMouseListenerInstance().mouseButtonPressed[mouseButtonID] = true;
      } else if (mouseActionID == GLFW_RELEASE) {
        getMouseListenerInstance().mouseButtonPressed[mouseButtonID] = false;
        getMouseListenerInstance().isMouseDragging = false;
      }
    }
  }

  public static void mouseScrollCallback(long glfwWindowAddress, double scrollXOffset, double scrollYOffset) {
    getMouseListenerInstance().mouseScrollX = scrollXOffset;
    getMouseListenerInstance().mouseScrollY = scrollYOffset;
  }

  public static void endFrame() {
    getMouseListenerInstance().mouseScrollX = 0;
    getMouseListenerInstance().mouseScrollY = 0;
    getMouseListenerInstance().lastMouseXPosition = getMouseListenerInstance().mouseXPosition;
    getMouseListenerInstance().lastMouseYPosition = getMouseListenerInstance().mouseYPosition;
  }

  public static float getX() {
    return (float) getMouseListenerInstance().mouseXPosition;
  }

  public static float getY() {
    return (float) getMouseListenerInstance().mouseYPosition;
  }

  public static float getDeltaX() {
    return (float) (getMouseListenerInstance().lastMouseXPosition - getMouseListenerInstance().mouseXPosition);
  }

  public static float getDeltaY() {
    return (float) (getMouseListenerInstance().lastMouseYPosition - getMouseListenerInstance().mouseYPosition);
  }

  public static float getScrollX() {
    return (float) getMouseListenerInstance().mouseScrollX;
  }

  public static float getScrollY() {
    return (float) getMouseListenerInstance().mouseScrollY;
  }

  public static boolean isDragging() {
    return getMouseListenerInstance().isMouseDragging;
  }

  public static boolean mouseButtonDown(int mouseButtonID) {
    if (mouseButtonID < getMouseListenerInstance().mouseButtonPressed.length) {
      return getMouseListenerInstance().mouseButtonPressed[mouseButtonID];
    } else {
      return false;
    }
  }

  private static boolean isMouseDragging() {
    return getMouseListenerInstance().mouseButtonPressed[LEFT_MOUSE_BUTTON_ID] ||
            getMouseListenerInstance().mouseButtonPressed[RIGHT_MOUSE_BUTTON_ID] ||
            getMouseListenerInstance().mouseButtonPressed[MIDDLE_MOUSE_BUTTON_ID];
  }
}
