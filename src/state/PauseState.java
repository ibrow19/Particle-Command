package state;

import processing.core.PApplet;
import processing.core.PConstants;
import scene.Context;
import gameobject.TextObject;

public class PauseState extends StatusState {

    private final TextObject mText;

    public PauseState(Context context) {

        super(context);
        
        int textSize = 25;
        boolean centred = true;
        mText = new TextObject(textSize, centred);
        mText.setText("Paused"); 
        mText.translate(500f, 500f);


    }

    public SceneState handleSpace() {

        return new WaveState(mContext);

    }

    public SceneState update(float delta) {
        
        return this;
    
    }

    public void render(PApplet core) {

        super.render(core);
        mText.render(core);

    }

}
