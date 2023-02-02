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
    if (mouseButtonID < get().mouseButtonPressed.length) {
      if (action == GLFW_PRESS) {
        get().mouseButtonPressed[button] = true;
      } else if (action == GLFW_RELEASE) {
        get().mouseButtonPressed[button] = false;
        get().isDragging = false;
      }
    }
  }

  public static void mouseScrollCallback(long glfwWindowAddress, double xOffset, double yOffset) {
    get().scrollX = xOffset;
    get().scrollY = yOffset;
  }

  public static void endFrame() {
    get().scrollX = 0;
    get().scrollY = 0;
    get().lastX = get().xPos;
    get().lastY = get().yPos;
  }

  public static float getX() {
    return (float) get().xPos;
  }

  public static float getY() {
    return (float) get().yPos;
  }

  public static float getDx() {
    return (float) (get().lastX - get().xPos);
  }

  public static float getDy() {
    return (float) (get().lastY - get().yPos);
  }

  public static float getScrollX() {
    return (float) get().scrollX;
  }

  public static float getScrollY() {
    return (float) get().scrollY;
  }

  public static boolean isDragging() {
    return get().isDragging;
  }

  public static boolean mouseButtonDown(int button) {
    if (button < get().mouseButtonPressed.length) {
      return get().mouseButtonPressed[button];
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
