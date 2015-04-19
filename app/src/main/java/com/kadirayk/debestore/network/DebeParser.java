package com.kadirayk.debestore.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import com.kadirayk.debestore.R;
import com.kadirayk.debestore.application.AppController;
import com.kadirayk.debestore.fragments.DebeDetailPageFragment;
import com.kadirayk.debestore.fragments.DebePageFragment;
import com.kadirayk.debestore.model.DebeDetailItem;
import com.kadirayk.debestore.model.DebeListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kadiray on 12.04.2015.
 */
public class DebeParser {

    private Context mContext;
    private Fragment currentFragment;
    private DebeListTask mDebeListTask;
    private DebeDetailTask mDebeDetailTask;

    String DEBE_LIST_URL = "https://eksisozluk.com/debe";
    ProgressDialog mProgressDialog;


    public DebeParser(Context context, Fragment fragmet) {
        mContext = context;
        currentFragment = fragmet;
        mDebeListTask = new DebeListTask();
        mDebeDetailTask = new DebeDetailTask();
    }


    public boolean isConnected(Context mContext) {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    public void callDebeListTask() {
        if (isConnected(mContext)) {
            mDebeListTask.execute();
        } else {
            Toast.makeText(mContext, "Sorun internet bağlantısında, bizle alakası yok.", Toast.LENGTH_SHORT).show();
        }
    }

    public void callDebeDetailTask(String url){
        if(isConnected(mContext)){
            mDebeDetailTask.execute(url);
        }else{
            Toast.makeText(mContext, "Sorun internet bağlantısında, bizle muhtemelen alakası yok.", Toast.LENGTH_SHORT).show();
        }
    }

    //YMLE AsyncTask
    private class DebeListTask extends AsyncTask<Void, Void, ArrayList<DebeListItem>> {

        ArrayList<String> Debes = new ArrayList<String>();
        ArrayList<DebeListItem> mDebeListItemList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle("Debe Listesi");
            mProgressDialog.setMessage("yükleniyor...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<DebeListItem> doInBackground(Void... params) {
            try {

                Document document = Jsoup.connect(DEBE_LIST_URL).get();

                Elements debeTitle = document.select("span[class=\"caption\"]");
                Elements debeAuthor = document.select("div[class=\"detail\"]");
                Elements debeLink = document.select("ol li a");

//                int lastGroup = AppController.getLastGroup(mContext);

                int i = 0;
                int j = 0;
//                String today = AppController.getSystemDate();
                for (Element element : debeLink) {

                    if(debeAuthor.get(0).toString().contains("reklam")){
                        j = i + 1;
                    }else{
                        j = i;
                    }

                    String currentDate = AppController.getSystemDate();

                    DebeListItem mDebeListItem = new DebeListItem(AppController.getDebeListCount(mContext), i+1, debeTitle.get(i).text(), debeAuthor.get(j).text(), "url", currentDate);

                    mDebeListItem.setPlace(i+1);
                    mDebeListItem.setTitle(debeTitle.get(i).text());
                    mDebeListItem.setAuthor(debeAuthor.get(j).text());
                    mDebeListItem.setUrl(element.attr("href"));

                    mDebeListItem.setDate(currentDate);


                    mDebeListItemList.add(mDebeListItem);

                    i++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return mDebeListItemList;
        }

        @Override
        protected void onPostExecute(ArrayList<DebeListItem> result) {
            if (result == null) {
                Toast.makeText(mContext, "başaramadık :(", Toast.LENGTH_SHORT).show();
            } else {
                ((DebePageFragment) currentFragment).OnDebeListResponseRecieved(result);
            }
            super.onPostExecute(result);
            mProgressDialog.dismiss();
        }
    }

    private class DebeDetailTask extends AsyncTask<String, Void, DebeDetailItem>{

        DebeDetailItem debeDetailItem = new DebeDetailItem(0,0,"","","","", "");

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

//            mProgressDialog = new ProgressDialog(mContext);
//            mProgressDialog.setTitle("Entry");
//            mProgressDialog.setMessage("yükleniyor...");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();

        }

        @Override
        protected DebeDetailItem doInBackground(String... params) {
            try {

                String mUrl = (String) params[0];
                Document document = Jsoup.connect(mUrl).get();

                String debeDetailTitle = document.title();
                String debeDetailAuthor = document.select("a[class=\"entry-author\"]").text();
                String debeDetailContent = document.select("div[class=\"content\"]").text();
                String debeDetailDate = document.select("a[class=\"entry-date permalink\"]").text();

                debeDetailItem.setAuthor(debeDetailAuthor);
                debeDetailItem.setTitle(debeDetailTitle);
                debeDetailItem.setContent(debeDetailContent);
                debeDetailItem.setDate(debeDetailDate);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return debeDetailItem;
        }

        @Override
        protected void onPostExecute(DebeDetailItem result) {
            if (result == null) {
                Toast.makeText(mContext, "başaramadık :(", Toast.LENGTH_SHORT).show();
            } else {
                ((DebeDetailPageFragment) currentFragment).OnDebeDetailResponseRecieved(result);
            }
            super.onPostExecute(result);
//            mProgressDialog.dismiss();
        }


    }



}
