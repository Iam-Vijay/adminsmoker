package com.global.adminsmoker.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.global.adminsmoker.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class NotificationsFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        Element versionElement = new Element();
        versionElement.setTitle("Version 2.0");
        Element frstelemeny=new Element();
        frstelemeny.setTitle(getString(R.string.content2));
        root = new AboutPage(getContext())
                .isRTL(false)
                .setDescription(getString(R.string.content))
                .setImage(R.drawable.nosmoke)
                .addItem(frstelemeny)
               // .addItem(adsElement)
                .addGroup("Connect with us")
                .addInstagram("r15_v2_boy")
                .addEmail("angryvijay7@gmail.com")
                .addFacebook("vijay.hero")
                .addPlayStore("com.global.adminsmoker")
                .addGroup("Version")
                .addItem(versionElement)

                .create();

        return root;
    }
}