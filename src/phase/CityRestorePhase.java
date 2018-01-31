package phase;

import scene.Context;
import gameobject.TextObject;

/// Phase restoring a city for the player.
public class CityRestorePhase extends IntervalPhase {

    /// Whether the city has been restored.
    private boolean mRestored;

    /// Initialise phase.
    public CityRestorePhase(Context context, TextObject bonus) {

        super(context, bonus);
        mRestored = false;
        mBonus.setText("1 City Restored");

    }

    /// Restore city if it has not alreay beenr restored.
    protected void updateBonus() {

        if (!mRestored) {

            mContext.cManager.restoreCity();
            mRestored = true;

        }
        
    }

    /// Transition to damage phase if applicable.
    protected IntervalPhase getNextPhase() {

        if (mContext.cManager.isDamaged()) {

            return null;

        } else {

            return new DamagePhase(mContext, mBonus);

        }
        
    }

}
