package com.global.adminsmoker.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.global.adminsmoker.Mysession;
import com.global.adminsmoker.R;
import com.global.adminsmoker.Sessionmanager;
import com.global.adminsmoker.database.AmountDbhandler;
import com.global.adminsmoker.database.Dbhandler;
import com.global.adminsmoker.database.MyAdminAmount;
import com.global.adminsmoker.database.TodayDbHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {
    private InterstitialAd mInterstitialAd;
    Date d1 = null;
    Date d2 = null;
    private AdView mAdView;
    SimpleDateFormat calulateformat = new SimpleDateFormat("HH:mm");
    TodayDbHelper todayDbHelper;
    long todaytotal;
    String pattern = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    SimpleDateFormat simpletimeformat = new SimpleDateFormat("HH:mm");
    String date = simpleDateFormat.format(new Date());
    String time = simpletimeformat.format(new Date());
    FloatingActionButton addbutton;
    TextView todaydate, todaycount, lasttime, todayamount, yeserday;
    Sessionmanager sessionmanager;
    Mysession mysession;
    AmountDbhandler amountDbhandler;
    MyAdminAmount adminAmount;
    ArrayList<String> mylist;
    double sum = 0;

    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        addbutton = root.findViewById(R.id.floatingActionButton);
        todaydate = root.findViewById(R.id.todaydate);
        todayDbHelper = new TodayDbHelper(getContext());
        amountDbhandler = new AmountDbhandler(getContext());
        adminAmount = new MyAdminAmount(getContext());
        Dbhandler dbhandler = new Dbhandler(getContext());
        todaycount = root.findViewById(R.id.todaytotalcount);
        lasttime = root.findViewById(R.id.lasttime);
        todayamount = root.findViewById(R.id.todayamount);
        yeserday=root.findViewById(R.id.yesterday);
        mysession = new Mysession(getContext());
        MobileAds.initialize(getContext(),
                "ca-app-pub-9977206186805887~2411696026");
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-9977206186805887/6159369340");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = root.findViewById(R.id.adView);

        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sessionmanager = new Sessionmanager(getContext());

        String lastaddedtime = todayDbHelper.getLastAddedRowtime();
        String lastdate = todayDbHelper.getLastAddedRowdate();


        if (date.equals(lastdate)) {
            todaydate.setText(date);
            try {
                d1 = calulateformat.parse(lastaddedtime);
                d2 = calulateformat.parse(time);
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;

                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                lasttime.setText("Last Cigarette is  " + diffHours + " Hours " + diffMinutes + " Minutes ");
                todayamount.setText(String.valueOf(sum));
                double am=amountDbhandler.gettotal();
                todayamount.setText("Today Amount is  "+format.format(new BigDecimal(am)));


            }catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            if (lastdate == null) {

                Toast.makeText(getContext(), "You Are a New One", Toast.LENGTH_SHORT).show();
            } else {
                String tttt = String.valueOf(todayDbHelper.gettodaytotalCount());
                dbhandler.adddateandtotal(lastdate, tttt);
                double am=amountDbhandler.gettotal();
                adminAmount.addamount(lastdate,String.valueOf(am));
            }
            todayamount.setText("Iam Ready");
            amountDbhandler.clearvalues();
            lasttime.setText("Start the Day");
            todayDbHelper.clearvalues();
        }


        if (todayDbHelper.gettodaytotalCount() == 0) {

            todaycount.setText("Start The Day ");
        }

        todaytotal = todayDbHelper.gettodaytotalCount();

        todaycount.setText("Today Count Is  " + String.valueOf(todaytotal));
        String yesterdaycount=dbhandler.getLastAddedcount();
        String yesterdayamount=adminAmount.getLastAddedRowamount();
        String yester=adminAmount.getLastAddedRowDate();
        if (yesterdayamount != null || yesterdayamount !=null){
            yeserday.setText(yester+" You Smoked "+yesterdaycount + "  Cigarette for " + yesterdayamount+" Rupees");
        }else {
            yeserday.setText("No Value For Yesterday");
        }
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                 String cash = sessionmanager.getValue("amount");
                long mytotal = todayDbHelper.gettodaytotalCount();
                todaycount.setText(String.valueOf(mytotal + 1));
                amountDbhandler.addamount(cash);
                double am=amountDbhandler.gettotal();
                todayamount.setText("Today Amount is  "+format.format(new BigDecimal(am)));
                lasttime.setText("Iam Calculating");
                Log.i(TAG, "onClick: " + time + date);
                DateFormat df = new SimpleDateFormat(" HH:mm");
                Calendar calobj = Calendar.getInstance();
                todayDbHelper.addtime(df.format(calobj.getTime()), date);
                sessionmanager.setLogin(true);
                mysession.setLogin(true);
                sessionmanager.savelasttime("time", time);
                Snackbar.make(v, "Added Succesfully", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                todayDbHelper.deletelastid();
                                amountDbhandler.deletelastid();
                                Toast.makeText(getContext(), "Undo Successfully", Toast.LENGTH_SHORT).show();
                                commitFragment(new HomeFragment());
                            }
                        }).show();
            }
        });
        if (!mysession.isLoggedIn()) {
            showCustomDialog(root);
        }

        if (!sessionmanager.isLoggedIn()) {

            TapTargetView.showFor(getActivity(),                 // `this` is an Activity
                    TapTarget.forView(root.findViewById(R.id.floatingActionButton), "This is ADD Button", "Its Use to Add a New Cigarettes")
                            // All options below are optional
                            .outerCircleColor(R.color.green)      // Specify a color for the outer circle
                            .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                            .targetCircleColor(R.color.white)   // Specify a color for the target circle
                            .titleTextSize(25)                  // Specify the size (in sp) of the title text
                            .titleTextColor(R.color.colorAccent)      // Specify the color of the title text
                            .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                            .descriptionTextColor(R.color.red)  // Specify the color of the description text
                            .textColor(R.color.red)            // Specify a color for both the title and description text
                            .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                            .dimColor(R.color.browser_actions_divider_color)            // If set, will dim behind the view with 30% opacity of the given color
                            .drawShadow(true)                   // Whether to draw a drop shadow or not
                            .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                            .tintTarget(true)                   // Whether to tint the target view's color
                            .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                            // Specify a custom drawable to draw as the target
                            .targetRadius(60),                  // Specify the target radius (in dp)
                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional

                            TapTargetView.showFor(getActivity(),                 // `this` is an Activity
                                    TapTarget.forView(root.findViewById(R.id.mnb), "This is Today Total Cigarettes View", "It Will be Use to Track The Cigarettes Smoked Hole Day")
                                            // All options below are optional
                                            .outerCircleColor(R.color.white)      // Specify a color for the outer circle
                                            .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                            .targetCircleColor(R.color.green)   // Specify a color for the target circle
                                            .titleTextSize(25)                  // Specify the size (in sp) of the title text
                                            .titleTextColor(R.color.colorAccent)      // Specify the color of the title text
                                            .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                                            .descriptionTextColor(R.color.red)  // Specify the color of the description text
                                            .textColor(R.color.red)            // Specify a color for both the title and description text
                                            .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                                            .dimColor(R.color.browser_actions_divider_color)            // If set, will dim behind the view with 30% opacity of the given color
                                            .drawShadow(true)                   // Whether to draw a drop shadow or not
                                            .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                            .tintTarget(false)                   // Whether to tint the target view's color
                                            .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                                            // Specify a custom drawable to draw as the target
                                            .targetRadius(60),                  // Specify the target radius (in dp)
                                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                                        @Override
                                        public void onTargetClick(TapTargetView view) {
                                            super.onTargetClick(view);      // This call is optional
                                            TapTargetView.showFor(getActivity(),                 // `this` is an Activity
                                                    TapTarget.forView(root.findViewById(R.id.cardcard), "This is Helps To Cigarettes Timing  ", "Its helps to Difference between Last Added Cigarette and CurrentTime")
                                                            // All options below are optional
                                                            .outerCircleColor(R.color.white)      // Specify a color for the outer circle
                                                            .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                                            .targetCircleColor(R.color.green)   // Specify a color for the target circle
                                                            .titleTextSize(25)                  // Specify the size (in sp) of the title text
                                                            .titleTextColor(R.color.colorAccent)      // Specify the color of the title text
                                                            .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                                                            .descriptionTextColor(R.color.red)  // Specify the color of the description text
                                                            .textColor(R.color.red)            // Specify a color for both the title and description text
                                                            .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                                                            .dimColor(R.color.browser_actions_divider_color)            // If set, will dim behind the view with 30% opacity of the given color
                                                            .drawShadow(true)                   // Whether to draw a drop shadow or not
                                                            .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                                            .tintTarget(false)                   // Whether to tint the target view's color
                                                            .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                                                            // Specify a custom drawable to draw as the target
                                                            .targetRadius(60),                  // Specify the target radius (in dp)
                                                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                                                        @Override
                                                        public void onTargetClick(TapTargetView view) {
                                                            super.onTargetClick(view);      // This call is optional

                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
        }

        return root;
    }


    private boolean commitFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
        return true;
    }

    private void showCustomDialog(View view) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.customcigurate, viewGroup, false);
        Button ok = dialogView.findViewById(R.id.csbitton);
        final EditText name = dialogView.findViewById(R.id.csname);
        final EditText amount = dialogView.findViewById(R.id.csamount);

        //Now we need an AlertDialog.Builder object
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myname = name.getText().toString();
                String myamount = amount.getText().toString();
                sessionmanager.savename("name", myname);
                sessionmanager.saveamount("amount", myamount);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}




