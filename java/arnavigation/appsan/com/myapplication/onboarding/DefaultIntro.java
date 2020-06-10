package arnavigation.appsan.com.myapplication.onboarding;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import arnavigation.appsan.com.myapplication.R;



public class DefaultIntro extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.slide1_title)
                ,getResources().getString(R.string.slide1_desc)
                ,R.drawable.logo
                ,getResources().getColor(R.color.white)
                ,getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimaryDark)));

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.slide2_title)
                ,getResources().getString(R.string.slide2_desc)
                ,R.drawable.slide2
                ,getResources().getColor(R.color.white)
                ,getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimaryDark)));

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.slide3_title)
                ,getResources().getString(R.string.slide3_desc)
                ,R.drawable.slide3
                ,getResources().getColor(R.color.white)
                ,getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimaryDark)));

        setBarColor(getResources().getColor(R.color.colorAccent));
        showSkipButton(false);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

}
