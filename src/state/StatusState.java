package state;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import scene.Context;
import gameobject.TextObject;

/// State displaying current wave/score status in addition to the standard game scene.
public class StatusState extends SceneState {

    /// Text showing current wave and score.
    private final TextObject mWaveStatus;

    /// Text displaying available missiles and black holes.
    private final TextObject mMissileStatus;

    /// Initialise status text.
    public StatusState(Context context) {

        super(context);
        int textSize = 25;
        boolean centred = false;
        mWaveStatus = new TextObject(textSize, centred);
        mMissileStatus = new TextObject(textSize, centred);
        mMissileStatus.translate(800f, 0f);
        updateStatus();

    }

    /// Update scene and status text.
    /// \param delta time since last update.
    /// \return this state (No transition).
    public SceneState update(float delta) { 

        super.update(delta);
        updateStatus();
        return this;

    }

    /// Render game and status text.
    /// \param core Processing core to use for rendering.
    public void render(PApplet core) {

        super.render(core);
        mWaveStatus.render(core);
        mMissileStatus.render(core);

    }

    /// Update status text.
    private void updateStatus() {

        mWaveStatus.setText("Wave " + mContext.wave + "\nScore: " + mContext.score);
        mMissileStatus.setText("Missiles: " + mContext.mManager.getMissileCount() +
                               "\nBlack Holes: " + mContext.mManager.getHoleCount());

    }

}
