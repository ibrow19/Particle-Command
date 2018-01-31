package force;

import processing.core.PVector;
import gameobject.PhysicsObject;

/// Force of drag opposing the velocity of objects.
public class Drag extends ForceGenerator {

    /// Constants to use for force calculations.
    private final float mK1;
    private final float mK2;

    /// Initialise constants.
    public Drag(float k1, float k2) {

        mK1 = k1; 
        mK2 = k2; 

    }

    /// Calculate force opposing object's velocity based on constants.
    /// \param object the game object to calculate the force for.
    /// \return the force that should be applied to the object.
    public PVector generateForce(PhysicsObject object) {

        PVector force = object.getVelocity();
        float dragCoeff = force.mag();
        dragCoeff = mK1 * dragCoeff + mK2 * dragCoeff * dragCoeff;
        force.normalize();
        force.mult(-dragCoeff);
        return force;

    }

}
