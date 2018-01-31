package gameobject;

import processing.core.PVector;
import processing.core.PApplet;

public class GameObject {

    // Rotation of object in degrees.
    private float mRotation;

    // Translation of object.
    private PVector mTranslation;

    // Scale of object.
    private PVector mScale;

    /// Initialise transformation properties.
    public GameObject() {

        mRotation = 0f;
        mTranslation = new PVector(0f, 0f);
        mScale = new PVector(1f, 1f);

    }

    /// Apply the objects current transformation to the world.
    /// \param core Processing core to use to carry out the transformation.
    void applyTransform(PApplet core) {

        // Scale, rotate, then translate to match object's current translation.
        // (Transformations applied in opposite order that they occur due to
        // column wise matrices use in underlying OpenGL API)
        core.translate(mTranslation.x, mTranslation.y);
        core.rotate(core.radians(getRotation()));
        core.scale(mScale.x, mScale.y);

    }

    /// Get the object's current rotation in degrees.
    /// \return the object's current rotation.
    public float getRotation() {

        // Ensure rotation is from 0 to 360 when fetched.
        while (mRotation >= 360f) {

            mRotation -= 360f;

        }
        while (mRotation < 0f) {

            mRotation += 360f;

        }
        return mRotation;

    }

    /// Get the object's current translation.
    /// \return the object's current translation.
    public PVector getTranslation() {

        return mTranslation.copy(); 

    }

    /// Get the translation of the object on the x axis.
    /// \return the translation of the object on the x axis.
    public float getXTranslation() {

        return mTranslation.x; 

    }

    /// Get the translation of the object on the y axis.
    /// \return the translation of the object on the y axis.
    public float getYTranslation() {

        return mTranslation.y;

    }

    /// Get the current scale of the object.
    /// \return the current scale of the object.
    public PVector getScale() {

        return mScale.copy();

    }

    /// Get the current scale of the object on the x axis.
    /// \return the current scale of the object on the x axis.
    public float getXScale() {

        return mScale.x;

    }

    /// Get the current scale of the object on the y axis.
    /// \return the current scale of the object on the y axis.
    public float getYScale() {

        return mScale.y;

    }

    /// Rotate the object.
    /// \param angle the angle in degrees to modify the object's rotation with.
    public void rotate(float angle) {

        mRotation += angle;

    }

    /// Set the object's rotation.
    /// \param angle the angle in degrees to set the object's rotation to.
    public void setRotation(float angle) {

        mRotation = angle;

    }

    /// Translate the object.
    /// \param move vector to translate the object with.
    public void translate(PVector move) {

        mTranslation.add(move); 

    }

    /// Translate the object.
    /// \param x x component of translation.
    /// \param y y component of translation.
    public void translate(float x, float y) {

        mTranslation.add(x, y); 

    }

    /// Set the object's translation.
    /// \param translation point to set the object's translation to.
    public void setTranslation(PVector translation) {

        mTranslation.set(translation);

    }

    /// Set the object's translation.
    /// \param x x component of object's new translation.
    /// \param y y component of object's new translation.
    public void setTranslation(float x, float y) {

        mTranslation.set(x, y);

    }

    /// Scale the object.
    /// \param scaling vector containing x and y components to scale object with.
    public void scale(PVector scaling) {

        mScale.x *= scaling.x; 
        mScale.y *= scaling.y; 

    }

    /// Scale the object.
    /// \param x the scaling on the x axis.
    /// \param y the scaling on the y axis.
    public void scale(float x, float y) {

        mScale.x *= x; 
        mScale.y *= y; 

    }

    /// Set the scale of the object.
    /// \param scale the new scale to set the object to use.
    public void setScale(PVector scale) {

        mScale.set(scale);

    }

    /// Set the scale of the object.
    /// \param x the new scale on the x axis.
    /// \param y the new scale on the y axis.
    public void setScale(float x, float y) {

        mScale.set(x, y);

    }

}
