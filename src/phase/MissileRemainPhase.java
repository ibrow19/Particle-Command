package phase;

import java.lang.String;
import scene.Context;
import gameobject.TextObject;

/// Give the player score for each missile remaing at the end of a wave.
public class MissileRemainPhase extends IntervalPhase {

    /// Score to award for each missile remaining.
    private final int mMissileScore;

    /// Missiles remaining when phase starts.
    private final int mStartCount;

    /// Current remaing missiles in countdown.
    private int mCurrentCount;

    /// Initialise phase.
    public MissileRemainPhase(Context context, TextObject bonus) {

        super(context, bonus);
        mMissileScore = 10;
        mStartCount = mContext.mManager.getMissileCount(); 
        mCurrentCount = mStartCount;
        updateBonus();

    }

    /// Linearly countdown missiles remaing while awarding points.
    protected void updateBonus() {

        int consumed = (int)(mStartCount * (mTime / mDuration));
        int targetCount = mStartCount - consumed;
        while (mCurrentCount > targetCount && mCurrentCount > 0) {

            --mCurrentCount;
            mContext.score += mMissileScore;

        }

        String text = new String("Remaining Missiles: " + mCurrentCount);
        if (consumed > 0) {

            text += (" +" + (consumed * mMissileScore) + " Score");

        }
        mBonus.setText(text);
        
    }

    /// Transition to missile restore phase.
    protected IntervalPhase getNextPhase() {

        return new MissileRestorePhase(mContext, mBonus);
        
    }

}
