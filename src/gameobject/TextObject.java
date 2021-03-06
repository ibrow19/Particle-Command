package gameobject;

import processing.core.PApplet;
import processing.core.PConstants;

/// A game object that displays text.
public class TextObject extends GameObject {

    /// The size of the text.
    final int mSize;

    /// Whether the text is centred.
    final boolean mCentred;

    /// The text displayed.
    String mText;

    /// Initialise with size and orientation.
    /// \param size text size to use.
    /// \param centred whether or not to use centred orientation.
    public TextObject(int size, boolean centred) {

        mSize = size;
        mCentred = centred;
        mText = new String();

    }

    /// Set the current text being displayed.
    /// \param text the text to display.
    public void setText(String text) {

        mText = new String(text);

    }

    /// Render the text.
    /// \param core Processing core to use for rendering.
    public void render(PApplet core) {

        // Align text.
        if (mCentred) {

            core.textAlign(PConstants.CENTER, PConstants.CENTER);

        } else {

            core.textAlign(PConstants.LEFT, PConstants.TOP);

        }
        
        // Set text size and colour.
        core.textSize(mSize);
        core.fill(0f, 0f, 0f);

        // Apply the game object's transformation then draw the text.
        core.pushMatrix();
        applyTransform(core);
        core.text(mText, 0f, 0f);
        core.popMatrix();

    }

}
