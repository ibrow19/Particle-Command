package gameobject;

import processing.core.PImage;
import processing.core.PVector;
import processing.core.PApplet;
import texture.Texture;

/// A game object that displays a texture.
public class TextureObject extends GameObject {
    
    /// The texture displayed by this object. 
    private final Texture mTexture;

    /// The current clip index used for drawing the texture.
    private int mClipIndex;

    /// Initialise texture.
    /// \param texture the texture to use for displaying this object.
    public TextureObject(Texture texture) {

        mTexture = texture;
        mClipIndex = 0;

    }

    /// Render the texture using the object's transformation.
    /// \param core Processing core to use for rendering.
    public void render(PApplet core) {

        // Apply game object's current transformation then render texture 
        // using current clip.
        core.pushMatrix();
        applyTransform(core);
        mTexture.render(core, mClipIndex);
        core.popMatrix();

    }

    
    /// Apply translation to image (clipped) centre followed by game object's transformation.
    /// \param core Processing core to use to apply transformation.
    public void applyTransform(PApplet core) {
        
        // Include transformation to origin so that image is centred on game object's
        // transformation.
        super.applyTransform(core);
        core.translate(-mTexture.getWidth(mClipIndex) / 2f, 
                       -mTexture.getHeight(mClipIndex) / 2f);

    }

    /// Get the current number of clips of the associated texture.
    /// \return the number of clips the texture has.
    protected int getClipCount() {

        return mTexture.getClipCount();

    }

    /// Set the current texture clip.
    /// \param clip the index of the clip to use.
    protected void setClip(int clip) {

        mClipIndex = clip;

    }

    /// Get width taking into account scale.
    /// \return the current width of the texture scaled by the game 
    ///         object's current x axis scaling.
    protected float getWidth() {

        return mTexture.getWidth(mClipIndex) * getXScale();

    }

    /// Get height taking into account scale.
    /// \return the current height of the texture scaled by the game 
    ///         object's current y axis scaling.
    protected float getHeight() {

        return mTexture.getHeight(mClipIndex) * getYScale();

    }

}
