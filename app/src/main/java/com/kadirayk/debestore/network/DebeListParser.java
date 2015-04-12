package com.kadirayk.debestore.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.kadirayk.debestore.fragments.DebePageFragment;
import com.kadirayk.debestore.models.DebeListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kadiray on 12.04.2015.
 */
public class DebeListParser {

    private Context mContext;
    private Fragment currentFragment;
    private DebeListTask mDebeListTask;

    String DEBE_LIST_URL = "http://www.eksisozluk.com/debe";
    ProgressDialog mProgressDialog;


    public DebeListParser(Context context, Fragment fragmet) {
        mContext = context;
        currentFragment = fragmet;
        mDebeListTask = new DebeListTask();
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
            Toast.makeText(mContext, "Sorun internet baðlantýsýnda, bizle alakasý yok.", Toast.LENGTH_SHORT).show();
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

                Elements YMLETitle = document.select("span[class=\"caption\"]");
                Elements YMLEAuthor = document.select("div[class=\"detail\"]");
                Elements YMLELink = document.select("ol li a");

//                int lastGroup = AppController.getLastGroup(mContext);

                int i = 0;
//                String today = AppController.getSystemDate();
                for (Element element : YMLELink) {
                    DebeListItem mDebeListItem = new DebeListItem(YMLETitle.get(i).text(), YMLEAuthor.get(i).text());

                    mDebeListItem.setPlace(i+1);
                    mDebeListItem.setTitle(YMLETitle.get(i).text());
                    mDebeListItem.setAuthor(YMLEAuthor.get(i).text());
                    mDebeListItem.setUrl(element.attr("href"));
                    //TODO
                    mDebeListItem.setDate("today");


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
                ((DebePageFragment) currentFragment).OnYMLEResponseRecieved(result);
            }
            super.onPostExecute(result);
            mProgressDialog.dismiss();
        }
    }

}
