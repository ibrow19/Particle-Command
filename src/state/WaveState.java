package state;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import scene.Context;

/// State representing a wave where the player must repel particles.
public class WaveState extends StatusState {

    /// Initialise state.
    public WaveState(Context context) {

        super(context);

    }

    /// Update game and transition state if wave finishes or player loses.
    /// \param delta time since last update.
    /// \return this state if wave is continuing.
    ///         game over state if al cities are destroyed.
    ///         interval state if all particles in wave are destroyed.
    public SceneState update(float delta) { 

        super.update(delta);

        // Go to game over state if all cities are destroyed.
        mContext.score += mContext.pManager.getPlayerDestroyed();
        if (mContext.cManager.isDestroyed()) {

            mContext.mManager.destroyTurret(); 
            return new GameOverState(mContext);

        }

        // Go to interval state if wave finished.
        if (!mContext.pManager.isActive() && !mContext.mManager.isActive()) {

            return new IntervalState(mContext);

        }

        return this;

    }

    /// Render game with crosshair for aiming.
    /// \param core Processing core to use for rendering.
    public void render(PApplet core) {

        super.render(core);

        mContext.crosshair.setTranslation(core.mouseX, core.mouseY);
        mContext.crosshair.render(core);

    }

    /// Handle left click. Fire missile at target.
    /// \param position coordinates of mouse event.
    /// \return this state (No transition).
    public SceneState handleLeftClick(PVector position) {
        
        mContext.mManager.fireMissile(position); 
        return this;

    }

    /// Handle right click. Fire black hole at target.
    /// \param position coordinates of mouse event.
    /// \return this state (No transition).
    public SceneState handleRightClick(PVector position) {
        
        mContext.mManager.fireBlackHole(position); 
        return this;

    }

    /// Handle space press. Pause the game.
    /// \return Pause state to transition to.
    public SceneState handleSpace() {

        return new PauseState(mContext);

    }

}
