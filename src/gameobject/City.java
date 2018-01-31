package gameobject;

import processing.core.PApplet;
import java.util.Iterator;
import scene.ParticleManager;
import texture.Texture;
import texture.Animation;

/// City that can be destroyed by collision with a particle.
public class City extends CollidableObject {

    /// Animation showing the city's destruction.
    private final Animation mDestruction;

    /// Index representing city version clip on texture.
    private final int mVersion;

    /// Whether the city has been destroyed or not.
    private boolean mDestroyed;

    /// Initialise city with texture and properties.
    /// \param texture the image to use to display the city.
    /// \param version the clip to use for the city when alive.
    /// \param destructionDuration how long the city's destruction animation should take.
    public City(Texture texture, int version, float destructionDuration) {
        
        super(texture);

        // Choose starting clip based on version number.
        int variations = 4;
        mVersion = (version % variations) + 1;
        setClip(mVersion);

        mDestroyed = false;
        
        // Initialise destruction animation with start and end clips.
        int destructionStart = 6;
        int destructionEnd = texture.getClipCount() - 1;
        mDestruction = new Animation(destructionStart, destructionEnd, destructionDuration);


    }

    /// Check whether city is destroyed.
    /// \return whether the city is destroyed.
    public boolean isDestroyed() {

        return mDestroyed;

    }

    /// Destroy the city if it collides with a particle. If it is already destroyed then
    /// update destruction animation.
    /// \param delta the time since the last update.
    /// \param pManager particles to check collision with.
    public void update(float delta, ParticleManager pManager) {

        /// If the city has not been destroyed: destroy it if it collides with a particle.
        if (!mDestroyed) {

            Iterator<Particle> it = pManager.iterator();
            while(!mDestroyed && it.hasNext()) {

                // Destroy particle and city on collision.
                Particle particle = it.next();
                if (particle.isFlying() && collides(particle)) {

                    mDestroyed = true;
                    particle.explode();

                }

            }

        /// Update destruction animation if city is already destroyed.
        } else {

            mDestruction.update(delta);
            setClip(mDestruction.getClip());

        }

    }

    /// Reset the city to be not be destroyed.
    public void reset() {

        mDestroyed = false;
        setClip(mVersion);
        mDestruction.reset();

    }


}
