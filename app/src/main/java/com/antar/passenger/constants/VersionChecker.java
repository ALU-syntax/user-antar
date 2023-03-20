package com.antar.passenger.constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;

import com.antar.passenger.R;
import com.antar.passenger.utils.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;


public class VersionChecker extends AsyncTask<String, String, String> {

    private String newVersion;

    @SuppressLint("StaticFieldLeak")
    private Activity context;

    public VersionChecker(Activity context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {
        try {
            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
//                    .select(".IQ1z0d .htlgb")
//                    .get(5)
//                    .ownText();
            if (document != null) {
                Elements element = document.getElementsContainingOwnText("Current Version");
                for (Element ele : element) {
                    if (ele.siblingElements() != null) {
                        Elements sibElemets = ele.siblingElements();
                        for (Element sibElemet : sibElemets) {
                            newVersion = sibElemet.text();
                        }
                    }
                }
            }
            return newVersion;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newVersion;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (newVersion != null) {
            int latestVersion = Integer.parseInt(newVersion.replace(".", ""));
            int versionCode = Integer.parseInt(Objects.requireNonNull(pInfo).versionName.replace(".", ""));
            Log.e("", newVersion);
            if (versionCode < latestVersion) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.DialogStyle);
                alert.setTitle(R.string.app_name)
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("Silahkan update" + " " + context.getString(R.string.app_name) + " " + "aplikasi. Anda memiliki versi lama.")
                        .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                                context.finish();
                            }
                        });

                alert.setCancelable(false);
                alert.show();
            }
        }
    }
}