package gameobject;

import processing.core.PVector;
import texture.Texture;
import scene.ForceManager;
import force.ForceGenerator;
import force.HoleGravity;

/// A black hole projectile that sucks in particles with gravity and destroys them.
public class BlackHole extends Projectile {

    /// The rate that the black hole rotates (in degrees per second).
    private final float mRotateRate;

    /// The mass of the black hole for calculating gravitational force.
    private final float mMass;

    /// Gravitational constant used for calculating black hole's gravitational force.
    private final float mGravity;

    /// Initialise hole properties.
    /// \param texture to use for black hole.
    /// \param fManager force manager for adding force on explosion.
    /// \param flightSpeed projectile speed while flying to target.
    /// \param explodeDuration how long projectile expodes for.
    /// \param rotateRate the rate at which the hole spins in degrees per second.
    /// \param mass the mass of the black hole.
    /// \param gravity the gravitational constant to use for the hole's gravitational force.
    public BlackHole(Texture texture, 
                     ForceManager fManager,
                     float flightSpeed, 
                     float explodeDuration,
                     float rotateRate,
                     float mass,
                     float gravity) {

        super(texture,
              fManager,
              flightSpeed,
              explodeDuration);

        mRotateRate = rotateRate;
        mMass = mass;
        mGravity = gravity;


    }

    /// Get the mass of the hole.
    /// \return the mass of the black hole.
    public float getMass() {

        return mMass;

    }

    /// Rotate the black hole in addition to normal projectile update.
    /// \param delta the time passed since the last update.
    public void update(float delta) {

        super.update(delta);
        rotate(delta * mRotateRate);

    }

    /// Create gravitational force to use for back hole's explosion.
    /// \return the gravitational force associated with this black hole.
    protected ForceGenerator createForce() {

        return new HoleGravity(this, mGravity);

    }

}
