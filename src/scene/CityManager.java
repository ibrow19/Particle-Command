package scene;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.function.Supplier;
import texture.Texture;
import gameobject.City;
import random.Randomiser;

/// Container for cities. Manages city destruction and restoration.
public class CityManager {

    /// Whether a city has been destroyed since the last damage reset.
    private boolean mDamaged;

    /// Number of not destroyed cities.
    private int mCityCount;

    /// Cities to manage.
    private final City[] mCities;

    /// Initialise cities.
    /// \param cityFactory factory to create cities with.
    public CityManager(Texture texture, float destructionDuration) {

        mDamaged = false;

        int totalCities = 4;
        mCityCount = totalCities;

        mCities = new City[totalCities];

        float rotation = -15f;
        float intervalR = 10f;
        float x = 200f;
        float y = 650f;
        float edgeY = 35f;
        float intervalX = 200f;

        // Place and Rotate cities.
        for (int i = 0; i < totalCities; ++i) {
        
            City city = new City(texture, i, destructionDuration);
            mCities[i] = city;
            city.rotate(rotation);
            city.setTranslation(x, y);

            // Adjust translation of cities at edge.
            if (i == 0 || i == (totalCities - 1)) {

                city.translate(0f, edgeY);

            }

            rotation += intervalR;
            x += intervalX;

        }

    }

    /// Restore one destroyed city chosen at random.
    public void restoreCity() {

        if (mCityCount < mCities.length) {

            // Choose a city at random and destroy it if it is destroyed.
            int start = Randomiser.randomInt(0, mCities.length - 1);
            City startCity = mCities[start];
            if (startCity.isDestroyed()) {

                startCity.reset();

            // If the chosen city was not destroyed, iterate until a city that
            // can be restored is found.
            } else {

                boolean restored = false; 
                for (int i = (start + 1) % mCities.length; 
                     !restored && i != start; 
                     ++i, i %= mCities.length) {

                    City city = mCities[i];
                    if (city.isDestroyed()) {

                        city.reset();
                        restored = true;

                    }

                }

            }
            ++mCityCount;

        }

    }

    /// Reset damage flag.
    public void resetDamage() {

        mDamaged = false;

    }

    /// Reset all cities so that none are destroyed anymore.
    public void reset() {

        if (mCityCount < mCities.length) {

            for (int i = 0; i < mCities.length; ++i) {

                mCities[i].reset();

            }
            mCityCount = mCities.length;

        }

    }

    /// Get the total number of cities (including destroyed cities).
    /// \return the total number of cities.
    public int getTotalCities() {

        return mCities.length;

    }

    /// Get number of not destroyed cities.
    /// \return number of not destroyed cities.
    public int getCurrentCities() {

        return mCityCount;

    }

    /// Check whether all cities have been destroyed.
    /// \return whether all cities have been destroyed.
    public boolean isDestroyed() {

        return mCityCount == 0;

    }

    /// Check whether a city has been damaged.
    /// \return whether a city has been damaged.
    public boolean isDamaged() {

        return mDamaged;

    }

    /// Destroy cities that collide with a particle and 
    /// update animation of destroyed cities.
    /// \param delta time since last update.
    /// \param pManager particles to check for collision.
    public void update(float delta, ParticleManager pManager) {

        int startCount = mCityCount;
        mCityCount = 0;

        // Update all cities.
        for (int i = 0; i < mCities.length; ++i) {

            City city = mCities[i];
            city.update(delta, pManager);
            if (!city.isDestroyed()) {
                
                ++mCityCount;

            }


        }

        // If a city was destroyed then set damaged flag.
        if (mCityCount < startCount) {

            mDamaged = true;

        }

    }

    /// Render all cities.
    /// \param core Processing core to draw cities with.
    public void render(PApplet core) {

        for (int i = 0; i < mCities.length; ++i) {

            mCities[i].render(core);

        }

    }

}
