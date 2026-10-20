package com.kingos.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager;
import android.widget.ScrollView;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(30, 60, 30, 30);
        layout.setBackgroundColor(Color.BLACK);

        TextView logo = new TextView(this);
        logo.setText("♛");
        logo.setTextColor(Color.rgb(255, 215, 0));
        logo.setTextSize(85);
        logo.setGravity(Gravity.CENTER);
        layout.addView(logo);

        TextView title = new TextView(this);
        title.setText("KingOS");
        title.setTextColor(Color.rgb(255, 215, 0));
        title.setTextSize(36);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("DARK FUTURE 1.0");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setTextSize(14);
        subtitle.setGravity(Gravity.CENTER);
        layout.addView(subtitle);

        TextView clock = new TextView(this);
        clock.setTextColor(Color.WHITE);
        clock.setTextSize(45);
        clock.setGravity(Gravity.CENTER);
        clock.setPadding(0, 40, 0, 40);
        layout.addView(clock);

        android.os.Handler handler =
            new android.os.Handler(getMainLooper());

        Runnable updateClock = new Runnable() {
            @Override
            public void run() {
                String time = new java.text.SimpleDateFormat(
                    "HH:mm",
                    java.util.Locale.getDefault()
                ).format(new java.util.Date());

                clock.setText(time);
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(updateClock);

        TextView apps = new TextView(this);
        apps.setText("✦ ALKALMAZÁSOK ✦");
        apps.setTextColor(Color.rgb(255, 215, 0));
        apps.setTextSize(20);
        apps.setPadding(0, 30, 0, 20);
        layout.addView(apps);

        ScrollView scroll = new ScrollView(this);
        LinearLayout appList = new LinearLayout(this);
        appList.setOrientation(LinearLayout.VERTICAL);

        PackageManager pm = getPackageManager();

        Intent intent = new Intent(
            Intent.ACTION_MAIN, null
        );
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> installedApps =
            pm.queryIntentActivities(intent, 0);

        for (ResolveInfo app : installedApps) {
            TextView button = new TextView(this);

            button.setText(app.loadLabel(pm));
            button.setTextColor(Color.rgb(255, 215, 0));
            button.setTextSize(18);
            button.setPadding(20, 20, 20, 20);

            button.setOnClickListener(v -> {
                Intent launch = pm.getLaunchIntentForPackage(
                    app.activityInfo.packageName
                );
                if (launch != null) {
                    startActivity(launch);
                }
            });

            appList.addView(button);
        }

        scroll.addView(appList);
        layout.addView(scroll);

        setContentView(layout);
    }
}
