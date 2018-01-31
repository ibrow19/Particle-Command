package phase;

import scene.Context;
import gameobject.TextObject;

/// Phase between waves for updating score based on player performance.
public abstract class IntervalPhase {

    // The phase states. Nothing happens in the start and end phase,
    // UPDATE is used for updating the player's score.
    private enum State {
        START,
        UPDATE,
        END
    }

    /// Current state the phase is in.
    private State mState;

    /// Game context for updating score and applying other bonuses.
    protected final Context mContext;

    /// Text for displaying current bonus.
    protected final TextObject mBonus;

    /// Duration of a state.
    protected final float mDuration;

    /// Current progress through a state.
    protected float mTime;

    /// Initialise phase.
    public IntervalPhase(Context context, TextObject bonus) {

        mState = State.START; 

        mContext = context;
        mBonus = bonus;

        mDuration = 0.8f;
        mTime = 0f;

    }

    /// Transition state and update based on time passed.
    /// \param delta time since last update.
    public IntervalPhase update(float delta) {

        // If in UPDATE state apply update.
        mTime += delta;
        if (mState == State.UPDATE && mTime < mDuration) {

            updateBonus();

        }
        // Change state when duration passed.
        while (mTime >= mDuration) {

            // Transition to UPDATE when START phase finished.
            if (mState == State.START) {

                mState = State.UPDATE;

            // at end of UPDATE carry out last bonus update then change to END state.
            } else if (mState == State.UPDATE) {

                updateBonus();     // Final bonus update.
                mState = State.END;

            // When END finished return next phase to transition to.
            } else {

                return getNextPhase();

            }
            mTime -= mDuration;

        }
        return this;

    }

    /// Skips to the next phase.
    public void skip() {

        // Set time to enough to pass all states.
        mTime = mDuration * 3;

    }

    /// Update bonus. Called during UPDATE phase.
    protected abstract void updateBonus();

    /// Gets next phase to transition to.
    /// \return next phase to transition to.
    protected abstract IntervalPhase getNextPhase();

}
