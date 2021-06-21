package com.global.adminsmoker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
Sessionmanager sessionmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        sessionmanager=new Sessionmanager(MainActivity.this);
            shownotification();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icon, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.changeamount:
            showCustomDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Sessionmanager.deleteCache(MainActivity.this);
    }

    public void shownotification(){
        int notificationId = 5;

        NotificationCompat.Builder builder= new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
                .setContentTitle("")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Are u Want to Add"))
                .setAutoCancel(true)
                .setWhen(0)
                .setSound(null)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        Uri path= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        builder.addAction(android.R.drawable.ic_menu_view,"Go to the App",pendingIntent);
        builder.setSound(null);
        NotificationManager notificationManager=(NotificationManager)MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelid= "its my chann id";
            NotificationChannel channel=new NotificationChannel(channelid,"Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelid);
        }
        notificationManager.notify(notificationId,builder.build());
    }
    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.customcigurate, viewGroup, false);
        Button ok = dialogView.findViewById(R.id.csbitton);
        final EditText name = dialogView.findViewById(R.id.csname);
        final EditText amount = dialogView.findViewById(R.id.csamount);

        //Now we need an AlertDialog.Builder object
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

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
