package scene;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;
import gameobject.Particle;
import rect.Rect;

public class ParticleManager {

    /// Starting wave particle count.
    private final int mBaseCount;

    /// Multiplier to increase particles per wave with.
    private final float mCountMultiplier;

    /// Multiplier to modify particles spawn intetval with.
    private final float mIntervalMultiplier;

    /// Accumulated number of particles destroyed by the player since last check.
    private int mPlayerDestroyed;

    /// Starting interval that particles first spawn at.
    private final float mBaseInterval;

    /// Interval between time that particles are currently spawned at.
    private float mInterval;

    /// Accumulated time since last interval.
    private float mTime;

    /// Remaining Particles that still need to be spawned for current wave.
    private int mRemaining;

    /// Bounding area that particles are destroyed if they leave..
    private Rect mBounds;

    /// Particles currently active in the game world.
    private ArrayList<Particle> mParticles;

    /// Factory for creating particles.
    private Supplier<Particle> mParticleFactory;

    /// Initialise particle properties.
    /// \param particleFactory factory for creating particles.
    /// \param baseCount starting number of particles for a wave.
    /// \param waveMultiplier multiplier to increase particles per wave with.
    /// \param interval time period between particle spawns.
    /// \param bounds bounds that particles must stay within.
    public ParticleManager(Supplier<Particle> particleFactory,
                           int baseCount,
                           float baseInterval,
                           float countMultiplier,
                           float intervalMultiplier,
                           Rect bounds) {

        mBaseCount = baseCount;
        mBaseInterval = baseInterval;
        mCountMultiplier = countMultiplier;
        mIntervalMultiplier = intervalMultiplier;
        mInterval = baseInterval;
        mRemaining = 0;
        mPlayerDestroyed = 0;
        mTime = 0f;

        mBounds = bounds.copy();

        mParticles = new ArrayList<Particle>();

        mParticleFactory = particleFactory;

    }

    /// Check how many particles have been destroyed by the payer then reset the count.
    /// \return int the number of particles destroyed by the player.
    public int getPlayerDestroyed() {

        int count = mPlayerDestroyed;
        mPlayerDestroyed = 0;
        return count;

    }

    /// Increment number of particles destroyed by the player.
    public void countDestroyed() {

        ++mPlayerDestroyed;

    }

    /// Get iterator over active particles.
    /// return iterator over active particles.
    public Iterator<Particle> iterator() {

        return mParticles.iterator();

    }

    /// Reset the manager to operate at specified wave 
    /// Adjusts spawn speed and particle count.
    /// \param the wave to set the manager to use.
    public void setWave(int wave) {

        mTime = 0f;
        mParticles.clear();
        --wave;  
        mPlayerDestroyed = 0;
        
        // Increase total particles and decrease spawn time based on multiplier.
        mRemaining = (int)(mBaseCount * (float)Math.pow(mCountMultiplier, wave));
        mInterval = mBaseInterval * (float)Math.pow(mIntervalMultiplier, wave);

    }

    /// Check if any particles are still active/ if particles are still spawning.
    /// \return whether there are any particles remaining.
    public boolean isActive() {

        return (mRemaining != 0) || !mParticles.isEmpty();

    }

    /// Apply forces to particles and update their position and state.
    /// \param delta time since last update.
    /// \param fManager forces to apply to particles.
    public void update(float delta, ForceManager fManager) {

        Iterator<Particle> it = mParticles.iterator();
        while (it.hasNext()) {
        
            Particle particle = it.next();
            
            // Apply forces to flying particles.
            if (particle.isFlying()) {

                fManager.applyForce(particle);

            }

            // Update particle.
            particle.update(delta);

            // Remove destroyed particles.
            if (particle.isDestroyed()) {

                it.remove();

            // explode flying particles that go out of bounds.
            } else if (particle.isFlying()) {

                float radius = particle.getRadius();
                float x = particle.getXTranslation();
                float y = particle.getYTranslation();
                if ((x - radius < mBounds.x) ||
                    (y - radius < mBounds.y) ||
                    (x + radius > mBounds.x + mBounds.width) ||
                    (y + radius > mBounds.y + mBounds.height)) {

                    particle.explode();
                    ++mPlayerDestroyed;

                } 

            }

        }

        mTime += delta;
        spawn();

    }

    /// Render each particle.
    /// \param core Processing core to render particles with.
    public void render(PApplet core) {

        Iterator<Particle> it = mParticles.iterator();
        while (it.hasNext()) {
        
            it.next().render(core);

        }

    }

    /// Spawn particles based on time passed and spawn interval.
    private void spawn() {

        while ((mRemaining > 0) && (mTime > mInterval)) {

            Particle particle = mParticleFactory.get();
            mParticles.add(particle);

            --mRemaining;
            mTime -= mInterval;

        }

    }

}
