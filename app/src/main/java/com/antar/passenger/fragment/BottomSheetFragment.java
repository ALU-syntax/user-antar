package com.antar.passenger.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.antar.passenger.R;
import com.antar.passenger.activity.SplashActivity;
import com.antar.passenger.utils.LocaleHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Locale;

/**
 * Created by Ardian Iqbal Yusmartito on 26/03/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {
    ImageView tickEnglish;
    ImageView tickIndo;
    ImageView tickCambodia;
    ProfileFragment profileFragment;
    Locale myLocale;
    String currentLang;
    String currentLanguage = "en";

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(view);
        profileFragment = new ProfileFragment();

        //removing all tick mark from the layout
        tickEnglish = (ImageView) view.findViewById(R.id.tick_english);
        tickEnglish.setVisibility(View.GONE);
        tickIndo = (ImageView) view.findViewById(R.id.tick_indo);
        tickIndo.setVisibility(View.GONE);
        tickCambodia = (ImageView) view.findViewById(R.id.tick_cambodia);
        tickCambodia.setVisibility(View.GONE);

        currentLanguage = getActivity().getIntent().getStringExtra(currentLang);

        //case for making the tick mark alive
        switch(LocaleHelper.getLanguage(getContext()))
        {
            case "en":
                tickEnglish.setVisibility(View.VISIBLE);
                break;
            case "in":
                tickIndo.setVisibility(View.VISIBLE);
                break;
            case "km-rKH":
                tickCambodia.setVisibility(View.VISIBLE);
                break;
            default:
                System.out.println("no match");
        }

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }
//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }

        //close icon of bottom sheet
        ImageView imageViewClose = (ImageView) view.findViewById(R.id.imageView);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });

        //onclick on english language
        ImageView flagEnglish = (ImageView) view.findViewById(R.id.flagView_english);
        flagEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("en");
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        //onclick on indo language
        ImageView flagIndo = (ImageView) view.findViewById(R.id.flagView_indo);
        flagIndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("in");
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        //onclick on cambodia language
        ImageView flagCambodia = (ImageView) view.findViewById(R.id.flagView_cambodia);
        flagCambodia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("km");
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    //to change the language and refresh the screen
    private void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            Context context = LocaleHelper.setLocale(getContext(), localeName);
            //Resources resources = context.getResources();
            myLocale = new Locale(localeName);
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(getContext(), SplashActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
        } else {
            //Toast.makeText(getActivity(), "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }
}
