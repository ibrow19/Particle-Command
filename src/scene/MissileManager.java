package scene;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;
import gameobject.Projectile;
import gameobject.Missile;
import gameobject.BlackHole;
import gameobject.Particle;
import gameobject.Turret;

/// Container managing and missile firing and destruction.
public class MissileManager {

    /// Missiles the payer starts the game with.
    private final int mBaseMissiles;

    /// Missile count that the player starts the wave with.
    private int mStartMissiles;

    /// Number of missiles currently available to fire.
    private int mMissileCount;

    /// Number of black holes currently available to fire.
    private int mHoleCount;

    /// Turret for firing missiles.
    private final Turret mTurret;

    /// Factory for creating missiles.
    private final Supplier<Missile> mMissileFactory;

    /// Factory for creating black holes.
    private final Supplier<BlackHole> mHoleFactory;

    /// Point that missiles are fired from.
    private final PVector mSpawn;

    /// Missiles currently active in the world.
    private final ArrayList<Missile> mMissiles;

    /// Black Holes currently active in the world.
    private final ArrayList<BlackHole> mHoles;

    /// Initialise factories and missile spawning properties.
    /// \param turretFactory factory for creating turret objects.
    /// \param missileFactory factory for creating missile objects.
    /// \param holeFactory factory for creating black hole objects.
    /// \param baseMissiles missiles player has at start of game.
    /// \param spawn point that missiles are fired from.
    public MissileManager(Supplier<Turret> turretFactory,
                          Supplier<Missile> missileFactory,
                          Supplier<BlackHole> holeFactory,
                          int baseMissiles,
                          PVector spawn) {

        mBaseMissiles = baseMissiles;
        mStartMissiles = baseMissiles;
        mMissileCount = baseMissiles;
        mHoleCount = 0;

        mSpawn = spawn.copy();

        mTurret = turretFactory.get();
        mTurret.setTranslation(spawn);
        mTurret.translate(0f, -50f);

        mMissileFactory = missileFactory;
        mHoleFactory = holeFactory;

        mMissiles = new ArrayList<Missile>();
        mHoles = new ArrayList<BlackHole>();

    }

    /// Check whether there are still any active projectiles.
    /// \return whether there are active projectiles.
    public boolean isActive() {

        return !(mMissiles.isEmpty() && mHoles.isEmpty());

    }

    /// Reset the manager by clearing active projectiles and resetting to base missile count.
    public void reset() {

        mStartMissiles = mBaseMissiles;
        mHoleCount = 0;
        restoreMissiles();
        mTurret.reset();

    }

    /// Restore missiles to starting count.
    public void restoreMissiles() {

        mMissileCount = mStartMissiles;

    }

    /// Get the current missile count available for firing.
    /// \return the current number of missiles available to fire.
    public int getMissileCount() {

        return mMissileCount;

    }

    /// Get the current black hole count available for firing.
    /// \return the current number of black holes available to fire.
    public int getHoleCount() {

        return mHoleCount;

    }

    /// Increase the number of missiles the player starts the wave with.
    public void addMissile() {

        ++mStartMissiles;

    }

    /// Increment the number of black holes available to fire.
    public void addHole() {

        ++mHoleCount;

    }

    /// Destroy the turret missiles are fired from.
    public void destroyTurret() {

        mTurret.destroy();

    }

    /// Fire a missile at target point.
    /// \param target point to fire the missile at.
    public void fireMissile(PVector target) {

        // Only fire a missile if there is one available to fire and turret is not destroyed.
        if (mMissileCount > 0 && !mTurret.isDestroyed()) {

            Missile missile = mMissileFactory.get();
            missile.setTranslation(mSpawn);
            missile.fire(target);
            mMissiles.add(missile);
            --mMissileCount;

            // Animate firing from turret.
            mTurret.fire();

        }

    }

    /// Fire a Black Hole at target point.
    /// \param target point to fire the Black Hole at.
    public void fireBlackHole(PVector target) {

        // Only fire a Black Hole if there is one available to fire and turret is not destroyed.
        if (mHoleCount > 0 && !mTurret.isDestroyed()) {

            BlackHole blackHole = mHoleFactory.get();
            blackHole.setTranslation(mSpawn);
            blackHole.fire(target);
            mHoles.add(blackHole);
            --mHoleCount;

            // Animate firing from turret.
            mTurret.fire();

        }

    }

    /// Update projectile positions and explode any that collide with particles.
    /// \param delta time since last update.
    /// \param pManager particles to check for collision.
    public void update(float delta, ParticleManager pManager) {

        // Update turret.
        mTurret.update(delta);

        // Update projectile positions and explode projectiles that collide with particles.
        updateProjectiles(delta, pManager, mMissiles);
        updateProjectiles(delta, pManager, mHoles);

        // Destroy particles that collide with black hole.
        Iterator<BlackHole> it = mHoles.iterator();
        while (it.hasNext()) {

            BlackHole hole = it.next();
            if (hole.isExploding()) {

                Iterator<Particle> particleIt = pManager.iterator();
                while (particleIt.hasNext()) {
                    
                    Particle particle = particleIt.next();
                    if (particle.isFlying() && particle.collides(hole)) {

                        particle.explode();
                        pManager.countDestroyed();

                    }

                }

            }

        }

    }

    /// Render turret and active projectiles.
    /// \param core Processing core to use for rendering.
    public void render(PApplet core) {

        mTurret.render(core);
        renderProjectiles(core, mMissiles);
        renderProjectiles(core, mHoles);

    }

    /// Render all projectiles in a list.
    /// \param core Processing core to use to render projectiles.
    /// \param projectiles list of projectiles to render.
    private <T extends Projectile> void renderProjectiles(PApplet core, 
                                                          ArrayList<T> projectiles) {

        Iterator<T> it = projectiles.iterator();
        while (it.hasNext()) {

            it.next().render(core);

        }

    }

    /// Update movement and explosion progress of all projectiles in a list.
    /// Explode any flying projectiles that collide with a particle.
    /// \param delta time since last update.
    /// \param pManager particles to check for collision with.
    /// \param projectiles list of projectiles to update.
    private <T extends Projectile> void updateProjectiles(float delta, 
                                                          ParticleManager pManager, 
                                                          ArrayList<T> projectiles) {

        Iterator<T> it = projectiles.iterator();
        while (it.hasNext()) {
            
            T projectile = it.next();
            if (projectile.isDestroyed()) {

                it.remove();

            } else {

                projectile.update(delta);
                if (projectile.isFlying()) {

                    Iterator<Particle> particleIt = pManager.iterator();
                    while (projectile.isFlying() && particleIt.hasNext()) {
                        
                        Particle particle = particleIt.next();
                        if (particle.isFlying() && particle.collides(projectile)) {

                            projectile.explode();

                        }

                    }

                }

            }

        }

    }

}
