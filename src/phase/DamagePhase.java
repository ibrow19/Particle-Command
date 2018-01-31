package phase;

import scene.Context;
import gameobject.TextObject;

/// Phase Giving the player a black hole for not taking damage in a wave.
public class DamagePhase extends IntervalPhase {

    /// Whether the bonus has been applied.
    private boolean mAppliedBonus;

    /// Initialise phase.
    public DamagePhase(Context context, TextObject bonus) {

        super(context, bonus);
        mAppliedBonus = false;
        mBonus.setText("No Damage Bonus: 1 Black Hole");

    }

    /// Give the player a black hole if they do not already have one.
    protected void updateBonus() {

        if (!mAppliedBonus) {

            mContext.mManager.addHole();
            mAppliedBonus = true;

        }
        
    }

    /// No subsequent phase so return null.
    protected IntervalPhase getNextPhase() {

        return null;
        
    }

}
