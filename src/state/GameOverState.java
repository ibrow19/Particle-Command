package state;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import scene.Context;
import gameobject.TextObject;

/// State managing game over screen.
public class GameOverState extends SceneState {

    /// Text stating game over status.
    private final TextObject mStatus;

    /// Text showing final score.
    private final TextObject mScore;

    /// Text showing restart options.
    private final TextObject mRestart;

    /// Initialise game over text.
    public GameOverState(Context context) {

        super(context);
        int textSize = 25;
        boolean centred = true;
        mStatus = new TextObject(textSize, centred);
        mScore = new TextObject(textSize, centred);
        mRestart = new TextObject(textSize, centred);
        mStatus.setText("Game Over"); 
        mScore.setText("Final Score: " + mContext.score); 
        mRestart.setText("Press space to restart"); 
        mStatus.translate(500f, 400f);
        mScore.translate(500f, 450f);
        mRestart.translate(500f, 500f);

    }

    /// Render scene and game over text.
    /// \param core Processing core to use for rendering.
    public void render(PApplet core) { 

        super.render(core);
        mStatus.render(core);
        mScore.render(core);
        mRestart.render(core);

    }

    /// Handle space press. Reset game and restart at first wave.
    /// \return new game state starting at first wave.
    public SceneState handleSpace() {

        mContext.wave = 1;
        mContext.score = 0;
        mContext.pManager.setWave(mContext.wave);
        mContext.cManager.reset();
        mContext.mManager.reset();
        return new WaveState(mContext);

    }

}
