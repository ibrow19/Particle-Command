package gameobject;

import processing.core.PImage;
import processing.core.PVector;
import texture.Texture;

/// Game object that can collide with other game objects.
public class CollidableObject extends TextureObject {

    /// Initialise texture used for displaying object.
    public CollidableObject(Texture texture) {

        super(texture);

    }

    /// Get radius based on current texture width (changes with clip and scale).
    /// \return current collision radius of object.
    public float getRadius() {

        // Use smaller out of width and height to define radius.
        if (getHeight() > getWidth()) {

            return getWidth() / 2f;

        } else {

            return getHeight() / 2f;

        }

    }

    /// Check if this object collides with another Collidable object.
    /// \param other the other object to check with collision.
    /// \return whether this object and other collide.
    public boolean collides(CollidableObject other) {

        // Objects collide if sum of their radii is less than the distance 
        // between their centres.
        return (other.getRadius() + getRadius()) > getTranslation().dist(other.getTranslation());

    }

}
