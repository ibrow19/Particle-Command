package gameobject;

import processing.core.PVector;
import scene.ForceManager;
import scene.ParticleManager;
import texture.Texture;
import force.ForceGenerator;
import force.Explosion;

/// Missile projectile that pushed particles away upon explosion.
public class Missile extends Projectile {

    // Current clip, explosion duration per clip and force to be applied by explosion.
    private float mExplodeForce;

    /// Initialise missile properties.
    /// \param texture to use for missile.
    /// \param fManager force manager for adding force on explosion.
    /// \param flightSpeed projectile speed while flying to target.
    /// \param explodeDuration how long projectile expodes for.
    /// \param explodeForce strength of explosion force.
    public Missile(Texture texture, 
                   ForceManager fManager,
                   float flightSpeed, 
                   float explodeDuration,
                   float explodeForce) {

        super(texture,
              fManager,
              flightSpeed,
              explodeDuration);

        mExplodeForce = explodeForce;

    }

    /// Create force for missile's explosion.
    /// \param force generator that repels particles in missile's explosion.
    protected ForceGenerator createForce() {

        return new Explosion(this, mExplodeForce);

    }

}
