package force;

import processing.core.PVector;
import gameobject.PhysicsObject;

/// The simplified force of gravity applying a constant downwards force.
public class BasicGravity extends ForceGenerator {

    /// Constant acceleration due to gravity.
    private final float mAcceleration;

    /// Initialise acceleration.
    public BasicGravity(float acceleration) {

        mAcceleration = acceleration;

    }

    /// Force of gravity is mg (Mass times gravitational acceleration).
    /// \param object the game object to calculate the force for.
    /// \return the force that should be applied to the object.
    public PVector generateForce(PhysicsObject object) {

        PVector force = new PVector(0f, 1f);
        float mass = object.getMass();        
        force.mult(mAcceleration).mult(mass);
        return force;

    }

}
