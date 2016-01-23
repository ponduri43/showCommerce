package com.samskrut.showcommerce.recivers;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.samskrut.showcommerce.ApplicationConstant;
import com.samskrut.showcommerce.CategoryGridView;
import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.database.buyerClass;
import com.samskrut.showcommerce.utilities.ConnectionDetector;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.HttpRequest;
import com.samskrut.showcommerce.utilities.InfoTracker;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by ponduri on 1/23/16.
 */
public class ConnectivityChangeReceiver extends BroadcastReceiver {

    ConnectionDetector connectionDetector;
    DBAdapter dba;
    String response;

    @Override
    public void onReceive(Context context, Intent intent) {

        connectionDetector = new ConnectionDetector(context);

        if (connectionDetector.isConnectingToInternet()) {
            //strt syncing

            dba = new DBAdapter(ApplicationConstant.getInstance().getApplicationContext());

            new postASYNC().execute("");

            Log.d("bis", "sync is being done  at reciver");
        }


    }

    private class postASYNC extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            postDataJQForAllRecord();

            return null;
        }

    }

    // for pending records
    public void postDataJQForAllRecord() {
        dba.open();
        Cursor mcursor2 = dba.retrive();

        if (mcursor2.getCount() != 0) {
            do {

                String cid = "" + mcursor2.getInt(1);
                String pid = "" + mcursor2.getInt(2);
                int tempid = mcursor2.getInt(2);
                String vid = "" + mcursor2.getString(3);

                String imid = "";
                if (mcursor2.getString(4).contentEquals("")) {
                    imid = "0";
                } else {
                    imid = "1";
                }

                String rflag = "" + mcursor2.getInt(5);

                long millis = mcursor2.getInt(6);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
                String time = "" + seconds;

                String bname = "" + mcursor2.getString(7);
                String pstatus = "" + mcursor2.getInt(8);
                String sid = mcursor2.getString(9);

                String pname = "" + mcursor2.getString(10);

                String count = mcursor2.getInt(11) + "";
                String infostatus = mcursor2.getString(12);
                String phone = mcursor2.getString(13);
                String mail = mcursor2.getString(14);
                String age = mcursor2.getString(15);
                String gender = mcursor2.getString(16);

                buyerClass bc = new buyerClass();

                bc.setCid(cid);
                bc.setPid(pid);
                bc.setVid(vid);
                bc.setImid(imid);
                bc.setRfalg(rflag);
                bc.setTime(time);
                bc.setBname(bname);
                bc.setPstatus(pstatus);
                bc.setSid(sid);
                bc.setPname(pname);
                bc.setCount(count);
                bc.setInfostatus(infostatus);
                bc.setPhone(phone);
                bc.setMail(mail);
                bc.setAge(age);
                bc.setGender(gender);

                //jobManager.addJobInBackground(new SyncDataJob(bc));

                postData(bc);

            } while (mcursor2.moveToNext());
            Log.d("bis", "pending records updated");
            dba.close();
        }
    }

    private void postData(buyerClass bc) {
        String fullUrl = "https://docs.google.com/forms/d/1KMT-N4qWGfQxv_BtxWXKIZ05FPsRXNUs6PvynGPxcDg/formResponse";
        // ProTip:Always Use Volley,Retrofit with okhttp
        HttpRequest mReq = new HttpRequest();
        String ccid = Constants.customerid + "";
        Log.d("bis", "ccid" + ccid);

        String data = "entry.524215702=" + URLEncoder.encode(bc.getCid()) + "&" + "entry.1773214368=" + URLEncoder.encode(bc.getPid()) + "&" + "entry.1667098360="
                + URLEncoder.encode(bc.getVid()) + "&" + "entry.17553355=" + URLEncoder.encode(bc.getImid()) + "&" + "entry.1827591331=" + URLEncoder.encode(bc.getRfalg()) + "&"
                + "entry.258560354=" + URLEncoder.encode(bc.getTime()) + "&" + "entry.743090124=" + URLEncoder.encode(bc.getBname()) + "&" + "entry.1853237792="
                + URLEncoder.encode(bc.getPstatus()) + "&" + "entry.1152173085=" + URLEncoder.encode(bc.getSid()) + "&" + "entry.679729860=" + URLEncoder.encode(bc.getPname())
                + "&" + "entry.813265161=" + URLEncoder.encode(bc.getCount()) + "&" + "entry.1793021485=" + URLEncoder.encode(bc.getPhone()) + "&" + "entry.1108043609="
                + URLEncoder.encode(bc.getMail()) + "&" + "entry.1959002797=" + URLEncoder.encode(bc.getAge()) + "&" + "entry.1145045882=" + URLEncoder.encode(bc.getGender())
                + "&" + "entry.1793147969=" + URLEncoder.encode(ccid);

        response = mReq.sendPost(fullUrl, data);
        int responsecode = mReq.statusCode;
        Log.i("bis", "online" + response);
        if (responsecode == 200) {


            // dao.updateBuyerinfoStatus(bc.getSid(),
            // Integer.parseInt(bc.getPid()), "web");

            dba.delete_buyer(bc.getSid(), Integer.parseInt(bc.getPid()));


        } else {

            Log.d("bis", "Exception while syncing not 200");
        }
    }
}
