package com.samskrut.showcommerce;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.samskrut.showcommerce.database.ActionbarProductObject;
import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.database.cateObject;
import com.samskrut.showcommerce.recycle.RecyclerItemClickListener;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.HttpRequest;
import com.samskrut.showcommerce.utilities.InfoTracker;
import com.samskrut.showcommerce.utilities.SharedPrefencers;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CategoryGridView extends AppCompatActivity {
    Context context;
    DBAdapter dba;
    CategoryGridViewAdapter gva;
    ArrayList<cateObject> clist;

    public static LinearLayout startSessionll;
    Button startSession, startNormaly;

    Boolean llflag = false;

    ActionBar mActionBar;
    SearchView mSearchView;

    private ArrayList<ActionbarProductObject> apoList;

    private Menu menu;
    MenuItem menuSearch;
    MenuItem menuform;
    SuggestionAdapter msuggestionAdapter;

    Toolbar toolbar;
    RecyclerView recList;

    SharedPrefencers prefencers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_gallery_screen);


        toolbar = (Toolbar) findViewById(R.id.app_bar);

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // toolbar.setNavigationIcon(R.drawable.ic_action_back);
        //toolbar.setLogo(R.drawable.ic_launcher);

        xmlfileinitilization();

        clist = new ArrayList<cateObject>();

        getListOfCategories();

        // ====================================================

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);

        GridLayoutManager llm = new GridLayoutManager(this, 4);
        // llm.setOrientation(GridLayoutManager.);

        recList.setLayoutManager(llm);


        if (InfoTracker.usersessionid > 0) {
            startSessionll.setVisibility(View.GONE);

            llflag = true;

        } else {
            startSessionll.setVisibility(View.VISIBLE);
            llflag = false;

        }

        CategoryGridViewAdapter ca = new CategoryGridViewAdapter(clist, context);
        recList.setAdapter(ca);
        recList.setItemAnimator(new DefaultItemAnimator());

        // item click listner
        recList.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (CategoryGridView.startSessionll.getVisibility() == View.GONE) {

                    if (InfoTracker.usersessionid > 0) {

                        InfoTracker.resetValuesAtCategoryLevel();

                        InfoTracker.cid = clist.get(position).getCid();

                    }

                    Intent intent = new Intent(context, ProductList.class);
                    intent.putExtra("cid", clist.get(position).getCid());
                    intent.putExtra("cname", clist.get(position).getCname());
                    context.startActivity(intent);

                }

            }
        }));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.common_actionbar, menu);

        this.menu = menu;

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        menuSearch = menu.findItem(R.id.search);
        menuform = menu.findItem(R.id.survey_mi);

        final SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setQueryHint(Html.fromHtml("<font color = #FFFFFF>" + getResources().getString(R.string.search) + "</font>"));
       // Log.d("bis", "pcgv info= " + manager.getSearchableInfo(getComponentName()).getSearchActivity());
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        if (llflag) {
            actionbariconsToggle(true);
        } else {
            actionbariconsToggle(false);
        }

        search.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String query) {
                //Log.d("bis", "sv query cgv" + query);
                if (query.toString().length() < 1) {
                    return true;
                } else {
                    loadHistory(query);

                    return true;
                }
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                return false;
            }

        });


        return true;

    }

    private void loadHistory(String query) {
        get_list_for_actionbar(query);

        // Cursor
        String[] columns = new String[]{"_id", "text"};
        Object[] temp = new Object[]{0, "default"};

        MatrixCursor cursor = new MatrixCursor(columns);

        for (int i = 0; i < apoList.size(); i++) {

            temp[0] = i;
            temp[1] = apoList.get(i).getPname();

            cursor.addRow(temp);

        }

        // SearchView
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final SearchView search = (SearchView) menuSearch.getActionView();
        msuggestionAdapter = new SuggestionAdapter(this, cursor, apoList);

        search.setSuggestionsAdapter(msuggestionAdapter);

        search.setOnSuggestionListener(new OnSuggestionListener() {

            @Override
            public boolean onSuggestionSelect(int position) {
                Constants.pid = apoList.get(position).getPid();
                Constants.pname = apoList.get(position).getPname();
                Constants.pcost = apoList.get(position).getPcost();

                // at category level along with action bar
                if (InfoTracker.usersessionid > 0) {

                    InfoTracker.resetValuesAtCategoryLevel();
                    InfoTracker.cid = clist.get(position).getCid();
                    InfoTracker.pid = apoList.get(position).getPid();
                    InfoTracker.pname = apoList.get(position).getPname();
                    InfoTracker.time = System.currentTimeMillis();

                }

                search.clearFocus();
                search.setQuery("", false);
                menuSearch.collapseActionView();

                Intent intent = new Intent(context, ProductOverView.class);

                context.startActivity(intent);

                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Constants.pid = apoList.get(position).getPid();
                Constants.pname = apoList.get(position).getPname();
                Constants.pcost = apoList.get(position).getPcost();

                // at category level along with action bar
                if (InfoTracker.usersessionid > 0) {

                    InfoTracker.resetValuesAtCategoryLevel();
                    InfoTracker.cid = clist.get(position).getCid();
                    InfoTracker.pid = apoList.get(position).getPid();
                    InfoTracker.pname = apoList.get(position).getPname();
                    InfoTracker.time = System.currentTimeMillis();

                }

                search.clearFocus();
                search.setQuery("", false);
                menuSearch.collapseActionView();

                Intent intent = new Intent(context, ProductOverView.class);

                context.startActivity(intent);

                return true;
            }
        });

    }

    private void get_list_for_actionbar(String query) {

        apoList = new ArrayList<ActionbarProductObject>();

        dba.open();
        Cursor mcursor = dba.retriveallproductsByMatch(query);
        if (mcursor.moveToFirst()) {
            do {
                Log.d("bis", " mcursor is not empty at CGV Sv " + mcursor.getCount());
                ActionbarProductObject apo = new ActionbarProductObject();
                apo.setPid(mcursor.getInt(0));
                apo.setCid(mcursor.getInt(1));
                apo.setPinfo(mcursor.getString(3));
                apo.setPname(mcursor.getString(2));
                apo.setPurl(mcursor.getString(4));
                apo.setPcost(mcursor.getString(6));
                apoList.add(apo);

            } while (mcursor.moveToNext());
        } else {
            Log.d("bis", " mcursor is empty at CGV Sv");
        }

        dba.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

            case R.id.survey_mi:
                if (InfoTracker.usersessionid > 0) {
                    intent = new Intent(getApplicationContext(), QuickSurvey.class);
                   // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                } else {
                    Toast.makeText(context, R.string.casual_mode_toast, Toast.LENGTH_SHORT).show();
                }

                break;
            case android.R.id.home:
                Toast.makeText(this, "home", Toast.LENGTH_LONG).show();

            case R.id.loogedout:
                prefencers.setloggedinstatus(false);
                if (llflag)
                    finish();
                finish();
                break;
        }

        return true;
    }

    private void getListOfCategories() {
        // TODO Auto-generated method stub
        dba.open();
        Cursor mCursor = dba.retriveallcategories();
        dba.close();

        for (int i = 0; i < mCursor.getCount(); i++) {
            cateObject cateObject = new cateObject();

            cateObject.setCurl(mCursor.getString(2));
            cateObject.setCname(mCursor.getString(1));
            cateObject.setCid(mCursor.getInt(0));
            clist.add(cateObject);
            mCursor.moveToNext();
        }

    }

    private void xmlfileinitilization() {
        context = this;
        prefencers = new SharedPrefencers(context);
        dba = new DBAdapter(context);

        startSessionll = (LinearLayout) findViewById(R.id.startSessionll);
        startSession = (Button) findViewById(R.id.startSession);
        startNormaly = (Button) findViewById(R.id.startNormaly);

        startSession.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                startSessionll.setVisibility(View.GONE);
                llflag = true;
                startNewSession();
                actionbariconsToggle(true);

            }
        });

        startNormaly.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startSessionll.setVisibility(View.GONE);
                llflag = false;
                actionbariconsToggle(true);

            }
        });

    }

    private void actionbariconsToggle(Boolean b) {
        menuform.setVisible(b);
        menuSearch.setVisible(b);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        InfoTracker.totalresetValues();
        InfoTracker.usersessionid = 0;
        InfoTracker.tableusersessionid = null;
        finish();
    }

    private void startNewSession() {

        if (InfoTracker.usersessionid == 0) {
            // Toast.makeText(context, "session id:  " + 1, 1000).show();
            InfoTracker.totalresetValues();
            InfoTracker.usersessionid = 1;
            InfoTracker.tableusersessionid = System.currentTimeMillis() + "";
            Log.d("bis", "ssid=" + InfoTracker.tableusersessionid);

        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set title
            alertDialogBuilder.setTitle("Session");

            // set dialog message
            alertDialogBuilder.setMessage("Do you want stop Session!").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Toast.makeText(context, "Session stopped", Toast.LENGTH_LONG).show();
                    // Constants.qsid = InfoTracker.tableusersessionid;
                    InfoTracker.totalresetValues();

                    InfoTracker.usersessionid = 0;
                    InfoTracker.tableusersessionid = null;

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.cancel();

                    Toast.makeText(context, "you are in same Session ", Toast.LENGTH_SHORT).show();
                    // InfoTracker.totalresetValues();
                    // InfoTracker.usersessionid = 1;
                    // InfoTracker.tableusersessionid =
                    // System.currentTimeMillis() + "";
                    // Log.d("bis", "ssid=" + InfoTracker.tableusersessionid);

                }
            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }

    }

    public void postData() {
        dba.open();
        Cursor mcursor2 = dba.retrive();
        dba.close();

        if (mcursor2.moveToFirst()) {
            do {

                Log.d("bis", "id=" + mcursor2.getInt(0));
                Log.d("bis", "cid=" + mcursor2.getInt(1));
                Log.d("bis", "pid=" + mcursor2.getInt(2));
                Log.d("bis", "vid=" + mcursor2.getString(3));
                Log.d("bis", "imid=" + mcursor2.getString(4));
                Log.d("bis", "rflag=" + mcursor2.getInt(5));
                Log.d("bis", "time=" + mcursor2.getInt(6));
                Log.d("bis", "bname=" + mcursor2.getString(7));
                Log.d("bis", "pstatus=" + mcursor2.getInt(8));
                Log.d("bis", "ssid=" + mcursor2.getString(9));
                Log.d("bis", "pname=" + mcursor2.getString(10));
                Log.d("bis", "count=" + mcursor2.getInt(11));
                Log.d("bis", "infostatus=" + mcursor2.getString(12));
                Log.d("bis", "phone=" + mcursor2.getString(13));
                Log.d("bis", "mail=" + mcursor2.getString(14));
                Log.d("bis", "age=" + mcursor2.getString(15));
                Log.d("bis", "gender=" + mcursor2.getString(16) + "\n\n");

                String cid = "" + mcursor2.getInt(1);
                String pid = "" + mcursor2.getInt(2);
                int tempid = mcursor2.getInt(2);
                String vid = "" + mcursor2.getString(3);
                String imid = "" + mcursor2.getString(4);
                String rfalg = "" + mcursor2.getInt(5);

                long millis = mcursor2.getInt(6);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
                millis -= TimeUnit.MINUTES.toMillis(minutes);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
                String time;
                if (seconds > 15) {
                    minutes++;
                    time = minutes + " MIN : " + 0 + "SEC";
                } else {
                    time = minutes + " MIN : " + seconds + "SEC";
                }

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

                String fullUrl = "https://docs.google.com/forms/d/1KMT-N4qWGfQxv_BtxWXKIZ05FPsRXNUs6PvynGPxcDg/formResponse";

                HttpRequest mReq = new HttpRequest();

                String data = "entry.524215702=" + URLEncoder.encode(cid) + "&" + "entry.1773214368=" + URLEncoder.encode(pid) + "&" + "entry.1667098360=" + URLEncoder.encode(vid)
                        + "&" + "entry.17553355=" + URLEncoder.encode(imid) + "&" + "entry.1827591331=" + URLEncoder.encode(rfalg) + "&" + "entry.258560354="
                        + URLEncoder.encode(time) + "&" + "entry.743090124=" + URLEncoder.encode(bname) + "&" + "entry.1853237792=" + URLEncoder.encode(pstatus) + "&"
                        + "entry.1152173085=" + URLEncoder.encode(sid) + "&" + "entry.679729860=" + URLEncoder.encode(pname) + "&" + "entry.813265161=" + URLEncoder.encode(count)
                        + "&" + "entry.1793021485=" + URLEncoder.encode(phone) + "&" + "entry.1108043609=" + URLEncoder.encode(mail) + "&" + "entry.1959002797="
                        + URLEncoder.encode(age) + "&" + "entry.1145045882=" + URLEncoder.encode(gender);

                String response = mReq.sendPost(fullUrl, data);

                if (mReq.statusCode == 200) {
                    // dba.open();
                    // dba.delete_buyer(s9, tempid);
                    Log.d("bis", "delete ack " + sid + " " + tempid);
                    // dba.close();

                }

            } while (mcursor2.moveToNext());
        }

    }

    public void postData2() {
        dba.open();
        Cursor mcursor2 = dba.retrive();
        dba.close();

        if (mcursor2.moveToFirst()) {
            do {
                Log.d("bis", "id=" + mcursor2.getInt(0));
                Log.d("bis", "cid=" + mcursor2.getInt(1));
                Log.d("bis", "pid=" + mcursor2.getInt(2));
                Log.d("bis", "vid=" + mcursor2.getString(3));
                Log.d("bis", "imid=" + mcursor2.getString(4));
                Log.d("bis", "rflag=" + mcursor2.getInt(5));
                Log.d("bis", "time=" + mcursor2.getInt(6));
                Log.d("bis", "bname=" + mcursor2.getString(7));
                Log.d("bis", "pstatus=" + mcursor2.getInt(8));
                Log.d("bis", "ssid=" + mcursor2.getString(9));
                Log.d("bis", "pname=" + mcursor2.getString(10));
                Log.d("bis", "count=" + mcursor2.getInt(11));
                Log.d("bis", "infostatus=" + mcursor2.getString(12));
                Log.d("bis", "phone=" + mcursor2.getString(13));
                Log.d("bis", "mail=" + mcursor2.getString(14));
                Log.d("bis", "age=" + mcursor2.getString(15));
                Log.d("bis", "gender=" + mcursor2.getString(16) + "\n\n");

            } while (mcursor2.moveToNext());
        }
    }

}
