package phase;

import scene.Context;
import gameobject.TextObject;

/// Phase that gives player extra missile for each city remaining.
public class CityRemainPhase extends IntervalPhase {

    /// Cities remaining when phase starts.
    private final int mStartCount;

    /// Current city remaining count in linear countdown.
    private int mCurrentCount;

    /// Initialise phase.
    public CityRemainPhase(Context context, TextObject bonus) {

        super(context, bonus);
        mStartCount = mContext.cManager.getCurrentCities(); 
        mCurrentCount = mStartCount;
        updateBonus();

    }

    /// Linearly countdown cities remaing while awarding extra missiles.
    protected void updateBonus() {

        int consumed = (int)(mStartCount * (mTime / mDuration));
        int targetCount = mStartCount - consumed;
        while (mCurrentCount > targetCount && mCurrentCount > 0) {

            --mCurrentCount;
            mContext.mManager.addMissile();
            mContext.mManager.restoreMissiles();

        }

        String text = new String("Remaining Cities: " + mCurrentCount);
        if (consumed > 0) {

            text += (" +" + consumed + " Missiles");

        }
        mBonus.setText(text);

    }

    /// Transition to city restore phase or damage phase if applicable.
    protected IntervalPhase getNextPhase() {

        if (mContext.cManager.getTotalCities() != mContext.cManager.getCurrentCities()) {

            return new CityRestorePhase(mContext, mBonus);

        } else if (mContext.cManager.isDamaged()) {

            return null;

        } else {

            return new DamagePhase(mContext, mBonus);

        }
        
    }

}
