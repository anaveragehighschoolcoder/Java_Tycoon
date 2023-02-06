package jade;

import util.Time;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {
  private boolean changingScene = false;
  private float timeToChangeSceneSeconds = 2.0f;

  public LevelEditorScene() {

  }

  @Override
  public void update(float deltaTimeSeconds) {

    System.out.println("" + (1.0f / deltaTimeSeconds) + " FPS");

    if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
      changingScene = true;
    }

    if (changingScene && timeToChangeSceneSeconds > 0) {
      timeToChangeSceneSeconds -= deltaTimeSeconds;
    }
    else if (changingScene) {
      Window.changeScene(1);
    }
  }
}
