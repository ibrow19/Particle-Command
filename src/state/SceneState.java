package state;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import scene.Context;

/// Abstract class representing a state that the game is in.
/// Handles updating, rendering and input handling for the game while
/// it is in this state.
public abstract class SceneState {
    
    /// Objects that make up the game scene.
    protected final Context mContext;

    /// Initialise state with game context.
    public SceneState(Context context) {
        
        mContext = context;

    }

    /// Update the game. Updates each object manager in the context.
    /// \param delta time since last update.
    /// \return the next state to transition to (Stays in this state).
    public SceneState update(float delta) {
        
        mContext.pManager.update(delta, mContext.fManager);
        mContext.mManager.update(delta, mContext.pManager);
        mContext.cManager.update(delta, mContext.pManager);
        return this;
        
    }

    /// Renders the scene.
    /// \param core Processing core to use for rendering the scene.
    public void render(PApplet core) { 

        mContext.cManager.render(core);
        mContext.mManager.render(core);
        mContext.pManager.render(core);

    }

    /// Handle left click. By default does nothing.
    /// \param position coordinates of click event.
    /// \return the next state to transition to (Stays in this state).
    public SceneState handleLeftClick(PVector position) {
        
        return this;
        
    }

    /// Handle right click. By default does nothing.
    /// \param position coordinates of click event.
    /// \return the next state to transition to (Stays in this state).
    public SceneState handleRightClick(PVector position) {
        
        return this;
        
    }

    /// Handle space press. By default does nothing.
    /// \return the next state to transition to (Stays in this state).
    public SceneState handleSpace() {
        
        return this;
        
    }

}
