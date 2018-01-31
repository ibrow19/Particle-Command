package scene;

import processing.core.PVector;
import java.util.ArrayList;
import java.util.Iterator;
import gameobject.PhysicsObject;
import force.ForceGenerator;

/// Class for managing and apply forces active in the game world.
public class ForceManager {

    /// Forces active in the game world.
    ArrayList<ForceGenerator> mForces;

    /// Initialise list of forces.
    public ForceManager() {

        mForces = new ArrayList<ForceGenerator>();

    }

    /// Add a force to the list of active forces.
    /// \param force new force to add to the manager.
    public void addForce(ForceGenerator force) {

        mForces.add(force); 

    }

    /// Apply all forces active in the game world to a object.
    /// \param object the game object to have physics applied to it.
    public void applyForce(PhysicsObject object) {

        // Apply each force in the manager to the object.
        Iterator<ForceGenerator> it = mForces.iterator();
        while (it.hasNext()) {

            ForceGenerator force = it.next();

            // Remove any forces that are no longer active.
            if (!force.isActive()) {

                it.remove();

            } else {

                object.applyForce(force.generateForce(object));

            }

        }

    }

}
