package com.global.adminsmoker.ui.dashboard;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.global.adminsmoker.R;
import com.global.adminsmoker.Sessionmanager;
import com.global.adminsmoker.SlideAdapter;
import com.global.adminsmoker.database.AmountDbhandler;
import com.global.adminsmoker.database.Dbhandler;
import com.global.adminsmoker.database.MyAdminAmount;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


import me.relex.circleindicator.CircleIndicator;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DashboardFragment extends Fragment {
CardView day3,day7,month1,average,tableview,detail,cleardata;
    //List<DataEntry> data;
TextView todate,enddate;
    RelativeLayout relativeLayout;
    String pattern = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date());
    Dbhandler dbhandler;
    private InterstitialAd mInterstitialAd;
Cursor cursor;
Sessionmanager sessionmanager;
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.smoking1,R.drawable.smoking2,R.drawable.smoking3,R.drawable.smoking4,R.drawable.smoking5,R.drawable.smoking6,R.drawable.smoking7,R.drawable.smoking8,R.drawable.smoking9,R.drawable.smoking10,R.drawable.smoking11};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    TextView lifetime;
        MyAdminAmount adminAmount;
        AmountDbhandler amountDbhandler;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
    day3=root.findViewById(R.id.day3button);
   day7=root.findViewById(R.id.day7button);

        sessionmanager=new Sessionmanager(getContext());
        lifetime=root.findViewById(R.id.lifetime);
        adminAmount=new MyAdminAmount(getContext());
   month1=root.findViewById(R.id.monthbutton);
   average=root.findViewById(R.id.average);
   tableview=root.findViewById(R.id.tableview);
   detail=root.findViewById(R.id.details);
   cleardata=root.findViewById(R.id.cleardata);
     amountDbhandler=new AmountDbhandler(getContext());
        MobileAds.initialize(getContext(),
                "ca-app-pub-9977206186805887~2411696026");
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-9977206186805887/6230208451");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
     dbhandler=new Dbhandler(getContext());
        final List<DataEntry> data = new ArrayList<>();

        final StringBuffer buffer = new StringBuffer();


        double nowamount = amountDbhandler.gettotal();
        double yeateramount=adminAmount.gettotal();
        double tot=nowamount+yeateramount;
        Log.i(TAG, "onCreateView: "+nowamount+"   "+yeateramount);
        if (yeateramount !=0 && nowamount ==0) {
            lifetime.setText("In Your Liftime You  Wasted " + format.format(new BigDecimal(yeateramount)));

        }if (nowamount != 0 && yeateramount ==0){
            lifetime.setText("In Your Liftime You  Wasted " + format.format(new BigDecimal(nowamount)));
        }else {
            lifetime.setText("In Your Liftime You  Wasted " + format.format(new BigDecimal(tot)));
        }
        day3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
});
        day7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        month1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        tableview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        cleardata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        return root;
          }
    private void showCustomDialog(View view) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.customdilogview, viewGroup, false);
            Button ok=dialogView.findViewById(R.id.buttonOk);

        //Now we need an AlertDialog.Builder object
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }
    public void fillcolors(){

    }
}
