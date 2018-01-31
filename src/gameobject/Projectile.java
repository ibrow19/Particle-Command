package gameobject;

import processing.core.PVector;
import scene.ForceManager;
import texture.Texture;
import texture.Animation;
import force.ForceGenerator;

/// A Projectile that can be fired at a target location, applying a force upon explosion.
public abstract class Projectile extends CollidableObject {

    /// States that the projectile can be in.
    private enum State {
        IDLE,
        FLYING,
        EXPLODING,
        DESTROYED
    };

    /// Force manager to add explosion force to.
    private final ForceManager mFManager;

    /// Manager for explosion animation.
    private final Animation mExplosion;

    /// Flight speed of projectile.
    private final float mFlightSpeed;

    /// Current state of projectile.
    private State mState;

    /// Duration projectile should fly for to reach target.
    private float mFlightDuration;

    /// Time to use for flight and explosion progress.
    private float mTime;

    /// Flight velocity for flying towards target.
    private PVector mFlightVelocity;

    /// Initialise projectile properties.
    /// \param texture to use for projectile.
    /// \param fManager force manager for adding force on explosion.
    /// \param flightSpeed projectile speed while flying to target.
    /// \param explodeDuration how long projectile expodes for.
    public Projectile(Texture texture, 
                      ForceManager fManager,
                      float flightSpeed, 
                      float explodeDuration) {

        super(texture);

        mFManager = fManager;
        mState = State.IDLE;
        mFlightSpeed = flightSpeed;
        mFlightDuration = 0f;
        mTime = 0f;

        int startClip = 1;
        setClip(startClip);

        // Animation goes from start clip to last texture clip.
        mExplosion = new Animation(startClip, getClipCount() - 1, explodeDuration);

    }

    /// Check if projectile is destroyed.
    /// \return whether the projectile is destroyed.
    public boolean isDestroyed() {

        return mState == State.DESTROYED;

    }

    /// Check if projectile is exploding.
    /// \return whether the projectile is exploding.
    public boolean isExploding() {

        return mState == State.EXPLODING;

    }

    /// Check if projectile is flying.
    /// \return whether the projectile is flying.
    public boolean isFlying() {

        return mState == State.FLYING;

    }

    /// Fire projectile towards target point.
    /// \param target point to fire projectile to.
    public void fire(PVector target) {

        // Only fire projectile if it has not already been fired.
        if (mState == State.IDLE) {

            // Calculate flying velocity and flight duration to reach
            // target using specified flight speed.
            mState = State.FLYING;
            PVector toTarget = PVector.sub(target, getTranslation());
            mFlightDuration = toTarget.mag() / mFlightSpeed;
            mFlightVelocity = toTarget.setMag(mFlightSpeed);

        }

    }

    /// Set the projectile to its explosion state.
    public void explode() {

        mState = State.EXPLODING;
        mExplosion.reset();

        // Add explosion force to manager.
        mFManager.addForce(createForce());

    }

    /// Update projectile based on its state.
    /// \param delta time since last update.
    public void update(float delta) {

        // In flying state, progress towards target point.
        if (mState == State.FLYING) {

            updateFlight(delta);

        // In exploding state update explosion animation.
        } else if (mState == State.EXPLODING) {

            updateExplosion(delta);

        }

    }

    // Generate force created when projectile explodes.
    // \return force generator for the projectile's explosion.
    protected abstract ForceGenerator createForce();

    /// Update the flight of the projectile.
    /// \param delta the time since the last update.
    private void updateFlight(float delta) {

        mTime += delta;
        
        // While the target has not been reached, move projectile towards target.
        if (mTime < mFlightDuration) {

            translate(mFlightVelocity.x * delta, mFlightVelocity.y * delta);

        // If flight has finished, move to target then use left over time to update
        // explosion.
        } else {
            
            float explodeDelta = mTime - mFlightDuration;
            float flightDelta = delta - explodeDelta;
            translate(mFlightVelocity.x * flightDelta, mFlightVelocity.y * flightDelta);
            explode();
            updateExplosion(explodeDelta);

        }

    }

    /// Update the explosion of the projectile.
    /// \param delta the time since the last update.
    private void updateExplosion(float delta) {

        // Update explosion animation.
        mExplosion.update(delta);
        setClip(mExplosion.getClip());

        // If animation has finished, destroy projectile.
        if (mExplosion.isFinished()) {

            mState = State.DESTROYED;

        }

    }

}
