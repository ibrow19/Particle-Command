package gameobject;

import processing.core.PImage;
import processing.core.PVector;
import texture.Texture;

/// Game object that is effected by physics.
public class PhysicsObject extends CollidableObject {

    /// Object's mass.
    private final float mMass;

    /// Current velocity.
    private PVector mVelocity;

    /// Accumulated forces to apply in next update.
    private PVector mAccumulator;

    /// Initialise object with texture and properties.
    /// \param texture the texture to display the object with.
    /// \param mass the mass of the object.
    /// \param velocity the initial velocity of the object.
    public PhysicsObject(Texture texture, float mass, PVector velocity) {
        
        super(texture);

        mMass = mass;
        mVelocity = velocity.copy();
        mAccumulator = new PVector(0f, 0f);

    }

    /// Get the mass of the object.
    /// \return the mass of the object.
    public float getMass() {

        return mMass;

    }

    /// Get the velocity of the object.
    /// \return the velocity of the object.
    public PVector getVelocity() {

        return mVelocity.copy();

    }

    /// Apply a force to the object.
    /// \param force vector of force to be applied.
    public void applyForce(PVector force) {

        mAccumulator.add(force.copy());

    }

    /// Update the object based on forces acting on it.
    /// \param delta time since the last update.
    void update(float delta) {

        // Add acceleration to velocity.
        // Use accumulated forces and mass to calculate acceleration.
        mVelocity.add(mAccumulator.div(mMass));
        PVector move = mVelocity.copy();
        move.mult(delta);
        translate(move);

        // Reset accumulator once finished.
        mAccumulator.set(0f, 0f);

    }

}
