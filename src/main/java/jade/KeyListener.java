package jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
  private static KeyListener keyListenerInstance;
  private final boolean[] keyPressed = new boolean[350];

  private KeyListener() {

  }

  public static KeyListener getKeyListenerInstance() {
    if (keyListenerInstance == null) {
      keyListenerInstance = new KeyListener();
    }

    return keyListenerInstance;
  }
  
  public static void keyCallback(long glfwWindowAddress, int keyButtonID, int scancode, int keyActionID, int glfwMods) {
    if (keyActionID == GLFW_PRESS) {
      getKeyListenerInstance().keyPressed[keyButtonID] = true;
    } else if (keyActionID == GLFW_RELEASE) {
      getKeyListenerInstance().keyPressed[keyButtonID] = false;
    }
  }

  public static boolean isKeyPressed(int keyButtonID) {
    if (keyButtonID < getKeyListenerInstance().keyPressed.length) {
      return getKeyListenerInstance().keyPressed[keyButtonID];
    }

    return false;
  }
}
