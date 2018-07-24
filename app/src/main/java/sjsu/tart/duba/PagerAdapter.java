package sjsu.tart.duba;

/**
 * Created by lion7 on 2018-07-25.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BottomDrawerTabTheme tab1 = new BottomDrawerTabTheme();
                return tab1;
            case 1:
                BottomDrawerTabNationality tab2 = new BottomDrawerTabNationality();
                return tab2;
            case 2:
                BottomDrawerTabGenderAge tab3 = new BottomDrawerTabGenderAge();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}