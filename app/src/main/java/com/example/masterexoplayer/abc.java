package com.example.masterexoplayer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class abc  extends Fragment {
    void abcd(){

        getParentFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                List<Fragment> list = getParentFragmentManager().getFragments();
                Fragment currentFragment = list.get(list.size() - 1);

                if(currentFragment == abc.this){
                    //resume
                } else {
                    //pause
                }
            }
        });
    }
}
