package gameobject;

import texture.Texture;

/// Turret object that can be animated to fire objects or be destroyed.
public class Turret extends TextureObject {

    private enum State {
        IDLE,
        FIRING,
        DESTROYED
    }

    /// Clips corresponding to states.
    private final int mIdleClip;
    private final int mFiringClip;
    private final int mDestroyedClip;

    /// How long the turret should appear to be firing for.
    private final float mFireDuration;

    /// Time that the turret has been firing for.
    private float mFireTime;

    /// The current state of the turret.
    private State mState;
    
    /// Initialise turret texture and properties.
    /// \param texture texture to use to display turret.
    /// \param fireDuration how long the turret should take to fire.
    public Turret(Texture texture, float fireDuration) {
        
        super(texture);

        mIdleClip = 1;
        mFiringClip = 2;
        mDestroyedClip = 3;

        mFireTime = 0f;
        mFireDuration = fireDuration;
        mState = State.IDLE;
        setClip(mIdleClip);

    }

    /// check whether turret is destroyed.
    /// \return whether the turret is destroyed.
    public boolean isDestroyed() {

        return mState == State.DESTROYED;

    }

    /// Destroy the turret.
    public void destroy() {

        mState = State.DESTROYED;

    }

    /// Reset the turret to its idle state.
    public void reset() {

        mState = State.IDLE;

    }

    /// Enter firing state.
    public void fire() {

        // cannot fire if destroyed.
        if (mState != State.DESTROYED) { 

            mFireTime = 0f;
            mState = State.FIRING;

        }

    }

    /// Update the turret based on its state.
    /// \param delta time since last update.
    public void update(float delta) {

        // Set clip to current state.
        if (mState == State.IDLE) {
            
            setClip(mIdleClip);

        } else if (mState == State.FIRING) {

            // Return to idle if firing finishes.
            mFireTime += delta;
            if (mFireTime > mFireDuration) {

                mState = State.IDLE;
                setClip(mIdleClip);

            } else {

                setClip(mFiringClip);

            }

        // set clip in destroyed state.
        } else {

            setClip(mDestroyedClip);

        }

    }

}
