import processing.core.PApplet;
import processing.core.PVector;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import scene.Scene;
import texture.TextureManager;
import texture.Texture;
import rect.Rect;

/// Class to initialise game and handle processing main loop.
public class Game extends PApplet {

    /// The fixed update timestep used.
    private final float mStepSize;

    /// The width to use for the window in pixels.
    private final int mWindowWidth;

    /// The height to use for the window in pixels.
    private final int mWindowHeight;

    /// The FPS to set the game to.
    private final int mFps;

    /// Accumulated time since last frame.
    private float mAccumulator;

    /// The time at the start of the frame.
    private float mStartTime;

    /// The game scene to update and draw.
    private Scene mScene;

    /// Use this class for processing main loop.
    public static void main(String[] args) {

        PApplet.main("Game");

    }

    /// Initialise final variables.
    public Game() {

        mStepSize = 0.01f;
        mWindowWidth = 1000;
        mWindowHeight = 1000;
        mFps = 60;

    }

    /// Initialise screen size settings.
    public void settings() {

        size(mWindowWidth, mWindowHeight);

    }

    /// Setup the game by loading images and initialising the scene.
    public void setup() {

        // Set frame rate and start time.
        frameRate(mFps);
        mStartTime = millis()/1000f;

        TextureManager tManager = new TextureManager();

        // Attempt to load and initialise textures, exit on failure.
        try {

            // Load crosshair texture.
            Texture crosshair = new Texture(this, "crosshair.png");

            // Load particle texture and add clips for each animation frame.
            Texture particle = new Texture(this, "particle.png");
            particle.addClip(new Rect(10, 20, 100, 100));
            addClips(particle, 1, 3, 120, 140);

            // Load missile texture and add clips for each animation frame.
            Texture missile = new Texture(this, "missile.png");
            missile.addClip(new Rect(95, 105, 100, 100));
            missile.addClip(new Rect(380, 60, 180, 180));
            missile.addClip(new Rect(675, 40, 220, 220));
            addClips(missile, 3, 6, 319, 324);

            // Load turret texture and add clips for state.
            Texture turret = new Texture(this, "turret.png");
            addClips(turret, 0, 3, 1000, 300);

            // Load turret texture and add clips for state.
            Texture city = new Texture(this, "city.png");
            addClips(city, 0, 9, 160, 150);

            // Load hole texture and add clips for each animation frame.
            Texture hole = new Texture(this, "hole.png");
            hole.addClip(new Rect(60, 60, 100, 100));
            hole.addClip(new Rect(240, 20, 180, 180));
            hole.addClip(new Rect(440, 0, 220, 220));

            // Add textures to texture manager.
            tManager.addTexture("crosshair", crosshair);
            tManager.addTexture("particle", particle);
            tManager.addTexture("missile", missile);
            tManager.addTexture("turret", turret);
            tManager.addTexture("city", city);
            tManager.addTexture("hole", hole);

            // Initialise scene with textures.
            mScene = new Scene(tManager);

        } catch (Exception e) {

            e.printStackTrace();
            exit();

        }

    }

    /// Draw the game.
    public void draw() {

        // Calculare time since last frame.
        float currentTime = millis()/1000f;
        float frameTime = currentTime - mStartTime;

        mStartTime = currentTime;
        mAccumulator += frameTime;

        // While there is still enough time left in accumulator update the
        // game with the fixed timestep.
        while (mAccumulator > mStepSize) {

            mScene.update(mStepSize); 
            mAccumulator -= mStepSize;

        }

        // Clear screen then render current game state.
        background(255);
        mScene.render(this);

    }

    /// Handle mouse pressed event.
    public void mousePressed() {

        // Delegate left or right mouse click events to scene.
        if (mouseButton == LEFT) {

            mScene.handleLeftClick(new PVector(mouseX, mouseY));

        } else if (mouseButton == RIGHT) {

            mScene.handleRightClick(new PVector(mouseX, mouseY));

        }

    }

    /// Handle key press events.
    public void keyPressed() {

        // Delegate space press events to the scene.
        if (key == ' ') {

            mScene.handleSpace();

        }

    }

    /// Add contiguous clips of a set size to a texture starting at specified index.
    /// \param texture the texture to add clips to.
    /// \param startIndex the index to start adding clips at.
    /// \param clips the number of clips to add.
    /// \param width the width of each clip to add.
    /// \param height the height of each clip to add.
    private void addClips(Texture texture, int startIndex, int clips, int width, int height) {

        for (int i = startIndex; i < clips; ++i) {

            texture.addClip(new Rect(i * width, 0, width, height));

        }

    }

}
