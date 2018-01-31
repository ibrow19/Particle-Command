package state;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import scene.Context;
import gameobject.TextObject;
import phase.IntervalPhase;
import phase.MissileRemainPhase;

/// State managing interval between waves where scores are counted and bonuses
/// are applied.
public class IntervalState extends StatusState {

    /// Current score update/bonus phase.
    private IntervalPhase mPhase;

    /// Text displaying wave complete message.
    private final TextObject mWave;

    /// Text displaying current bonus being applied.
    private final TextObject mBonus;

    /// Initialise wave and bonus text and start initial bonus phase.
    public IntervalState(Context context) {

        super(context);

        int textSize = 25;
        boolean centred = true;

        mWave = new TextObject(textSize, centred);
        mBonus = new TextObject(textSize, centred);

        mWave.translate(500f, 400f);
        mBonus.translate(500f, 500f);

        mWave.setText("Wave " + mContext.wave + " completed");

        mPhase = new MissileRemainPhase(mContext, mBonus);

    }

    /// Update current bonus phase.
    /// \param delta time since last update.
    /// \return this state if phases are continuing. If phases finish then
    ///         returns WaveState for next wave.
    public SceneState update(float delta) { 

        if (mPhase != null) {

            mPhase = mPhase.update(delta);

        }
        super.update(delta);

        if (mPhase == null) {

            mBonus.setText("Press space to begin next wave");

        }
        return this;

    }

    /// Render wave end text and bonus text.
    /// \param core Processing core to use for rendering.
    public void render(PApplet core) { 

        super.render(core);

        mWave.render(core);
        mBonus.render(core);

    }

    /// Handle space pressed. Skip current phase.
    /// \return this state (No transition).
    public SceneState handleSpace() {

        if (mPhase != null) {

            mPhase.skip();
            return this;

        } else {

            mContext.cManager.resetDamage();
            mContext.pManager.setWave(++mContext.wave);
            return new WaveState(mContext);

        }

    }

}
