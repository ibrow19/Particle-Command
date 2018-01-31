package state;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import scene.Context;
import gameobject.TextObject;

/// State to start game with.
public class StartState extends SceneState {

    /// Text displaying how to start game.
    private final TextObject mText;

    /// Initialise how to start text.
    public StartState(Context context) {

        super(context);

        int textSize = 25;
        boolean centred = true;
        mText = new TextObject(textSize, centred);
        mText.setText("Press space to start"); 
        mText.translate(500f, 500f);

    }

    /// Render scene and text.
    /// \param core Processing core to render game with.
    public void render(PApplet core) { 

        super.render(core);
        mText.render(core);

    }

    /// Handle space press. Transition to first wave.
    /// \return state for first wave of game.
    public SceneState handleSpace() {

        mContext.pManager.setWave(mContext.wave);
        return new WaveState(mContext);

    }

}
