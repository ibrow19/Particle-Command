package force;

import processing.core.PVector;
import gameobject.PhysicsObject;
import gameobject.Projectile;

/// Explosion force produced by a projectile.
public class Explosion extends ForceGenerator {

    /// The projectile generating the force.
    private final Projectile mProjectile;

    /// The magnitude of the explosion force.
    private final float mMagnitude;

    /// Initialise associated projectile and force magnitude.
    public Explosion(Projectile projectile, float magnitude) {

        mProjectile = projectile;
        mMagnitude = magnitude;

    }

    /// Generate outward force if object collides with projectile explosion.
    /// \param object the game object to calculate the force for.
    /// \return the force that should be applied to the object.
    public PVector generateForce(PhysicsObject object) {

        // Deactivate force if projectile has finished exploding.
        if (mProjectile.isDestroyed()) {

            deactivate();

        // Apply force away from centre of explosion only if object is in explosion. 
        } else if (mProjectile.collides(object)) {

            PVector position = object.getTranslation();
            PVector force = PVector.sub(object.getTranslation(), mProjectile.getTranslation());
            force.setMag(mMagnitude);
            return force;

        }
        return new PVector(0f, 0f);

    }

}
