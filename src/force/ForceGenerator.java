package force;

import processing.core.PVector;
import gameobject.PhysicsObject;

/// Class representing a force that can be generated and applied to a PhysicsObject.
public abstract class ForceGenerator {

    /// Whether the force is current acting on objects in the game world.
    private boolean mActive;

    /// Initialise force as active.
    public ForceGenerator() {

        mActive = true;

    }

    /// Check whether force is active.
    /// \return whether the force is active.
    public boolean isActive() {

        return mActive;

    }

    /// Deactivate the force.
    public void deactivate() {

        mActive = false;

    }

    /// Calculate the strength of the force being acted upon a object.
    /// \param object the game object to calculate the force for.
    /// \return the force that should be applied to the object.
    public abstract PVector generateForce(PhysicsObject object);

}
