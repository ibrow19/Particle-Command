package phase;

import scene.Context;
import gameobject.TextObject;

/// Phase restores the player's missiles to their starting value.
public class MissileRestorePhase extends IntervalPhase {

    /// Whether the missiles have been restored.
    private boolean mRestored;

    /// Initialise phase.
    public MissileRestorePhase(Context context, TextObject bonus) {

        super(context, bonus);
        mRestored = false;
        mBonus.setText("Missiles Restored");

    }

    /// Restore missiles if they have not already been restored.
    protected void updateBonus() {

        if (!mRestored) {

            mContext.mManager.restoreMissiles();
            mRestored = true;

        }
        
    }

    /// Transition to city remaining phase.
    protected IntervalPhase getNextPhase() {

        return new CityRemainPhase(mContext, mBonus);
        
    }

}
