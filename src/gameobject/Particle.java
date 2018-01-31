package gameobject;

import processing.core.PApplet;
import processing.core.PVector;
import texture.Texture;
import texture.Animation;

/// Particle that is effected by forces and can explode and be destroyed.
public class Particle extends PhysicsObject {

    /// States that the particle can be in.
    private enum State {
        FLYING,
        EXPLODING,
        DESTROYED
    }

    /// Manages explosion animation of particle.
    private final Animation mExplosion;

    /// Current state the particle is in.
    private State mState;

    /// Initialise texture and properties.
    /// \param texture the texture to use to display the particle.
    /// \param mass the mass of the particle.
    /// \param velocity the initial velocity of the particle.
    /// \param explodeDuration how long it takes the particle to explode.
    public Particle(Texture texture, 
                    float mass, 
                    PVector velocity, 
                    float explodeDuration) {

        super(texture, mass, velocity);
        mState = State.FLYING;

        int particleClip = 1;
        setClip(particleClip);

        int explodeStart = 2;
        int explodeEnd = texture.getClipCount() - 1;
        mExplosion = new Animation(explodeStart, explodeEnd, explodeDuration);

    }

    /// Check whether the particle is currently flying.
    /// \return whether the particle is currently flying.
    public boolean isFlying() {

        return mState == State.FLYING;

    }

    /// Check whether the particle is currently exploding.
    /// \return whether the particle is currently exploding.
    public boolean isExploding() {

        return mState == State.EXPLODING;

    }

    /// Check whether the particle is currently destroyed.
    /// \return whether the particle is currently destroyed.
    public boolean isDestroyed() {

        return mState == State.DESTROYED;

    }

    /// Explode the particle (set its state to exploding).
    public void explode() {
        
        mState = State.EXPLODING;

    }

    /// Update the particle based on its state.
    /// \param delta time passed since last update.
    public void update(float delta) {

        // If the particle is exploding, update its explosion animation.
        if (mState == State.EXPLODING) {

            mExplosion.update(delta);
            setClip(mExplosion.getClip());

            // Destroy particle when animation finishes.
            if (mExplosion.isFinished()) {

                mState = State.DESTROYED;

            }

        // If the particle is flying the apply physics as normal.
        } else if (mState == State.FLYING) {

            super.update(delta);

        }

    }

}
