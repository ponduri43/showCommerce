package com.samskrut.showcommerce;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.samskrut.showcommerce.database.ActionbarProductObject;
import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.database.productObject;
import com.samskrut.showcommerce.recycle.RecyclerItemClickListener;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.InfoTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductList extends Activity {

    Intent intent;
    DBAdapter dba;
    int cid;
    String cname;

    Context context;


    ProductGridViewAdapter pgva;

    List<productObject> productList;

    ActionBar mActionBar;
    SearchView search;
    SearchManager manager;

    private ArrayList<ActionbarProductObject> apoList;

    private Menu menu;
    MenuItem menuSearchItem;
    MenuItem menuformItem;
    ActionBarSuggestionAdapter actionBarSuggestionAdapter;


    //Toolbar toolbar;
    RecyclerView recList;

    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    List<String> listDataHeader_catnames;
    List<List<String>> lists_pnames;
    HashMap<String, List<String>> listDataChild_key_cnames_val_ls;

    List<Integer> listDataHeader_cat_ids;
    List<List<Integer>> lists_pids;
    HashMap<String, List<Integer>> listDataChild_key_cnames_val_ls2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        cid = intent.getIntExtra("cid", 999);
        cname = intent.getStringExtra("cname");

        setContentView(R.layout.activity_product_list);


        xmlfileinitilization();
        // preparing list data
        prepareListData();
        get_list_of_products();

        ActionBar actionBar=getActionBar();
        actionBar.setTitle(cname);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        //actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setIcon(R.drawable.ic_action_back);
        /*toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(cname);
        // toolbar.setBackgroundDrawable(new
        // ColorDrawable(Color.parseColor("#ff0099cc")));
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();*/


        //====================================== Drawer coding

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        Drawer.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        setDimens();


        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, R.color.colorAccent, R.string.opendrawer, R.string.closedrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont
                // want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the
        // Drawer toggle
        mDrawerToggle.syncState(); // Finally we set the drawer toggle sync
        // State
        //mDrawerToggle.setDrawerIndicatorEnabled(false);





        listAdapter = new ExpandableListAdapter(this, Drawer, listDataHeader_catnames, listDataHeader_cat_ids, listDataChild_key_cnames_val_ls, listDataChild_key_cnames_val_ls2);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                //arg1.findViewById(R.id.btn_plus).setBackgroundResource(R.drawable.ic_launcher);

                return false;
            }
        });


    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    public void setDimens() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expListView.setIndicatorBounds(width - GetPixelFromDips(250), width - GetPixelFromDips(200));
            //searchExpListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        } else {
            expListView.setIndicatorBoundsRelative(width - GetPixelFromDips(350), width - GetPixelFromDips(250));
            //searchExpListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {

        getMenuInflater().inflate(R.menu.productlist_mi, _menu);

        this.menu = _menu;

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        menuSearchItem = menu.findItem(R.id.search);
        menuformItem = menu.findItem(R.id.survey_mi);

        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();

        search.setQueryHint(Html.fromHtml("<font color = #FFFFFF>" + getResources().getString(R.string.search) + "</font>"));
       // Log.d("bis", "pl info= " + manager.getSearchableInfo(getComponentName()).getSearchActivity());
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextChange(String query)
            {
                //Log.d("bis", "sv query pl" + query);
                //loadHistory(query);
                if (query.toString().length() < 1)
                {
                return  true;
                }
                else
                {
                    loadHistory(query);

                    return true;
                }
            }

            @Override
            public boolean onQueryTextSubmit(String query)
            {
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
        final SearchView search = (SearchView) menuSearchItem.getActionView();
        actionBarSuggestionAdapter = new ActionBarSuggestionAdapter(this, cursor, apoList);

        search.setSuggestionsAdapter(actionBarSuggestionAdapter);

        search.setOnSuggestionListener(new SearchView.OnSuggestionListener() {

            @Override
            public boolean onSuggestionSelect(int position) {
                Constants.pid = apoList.get(position).getPid();
                Constants.pname = apoList.get(position).getPname();
                Constants.pcost = apoList.get(position).getPcost();

                if (InfoTracker.usersessionid > 0) {

                    InfoTracker.resetValuesAtProductLevel();

                    InfoTracker.pid = apoList.get(position).getPid();
                    InfoTracker.pname = apoList.get(position).getPname();
                    InfoTracker.time = System.currentTimeMillis();

                }

                search.clearFocus();
                search.setQuery("", false);
               // menuSearch.collapseActionView();

                Intent intent = new Intent(context, ProductOverView.class);

                context.startActivity(intent);

                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {

                Constants.pid = apoList.get(position).getPid();
                Constants.pname = apoList.get(position).getPname();
                Constants.pcost = apoList.get(position).getPcost();

                if (InfoTracker.usersessionid > 0) {

                    InfoTracker.resetValuesAtProductLevel();

                    InfoTracker.pid = apoList.get(position).getPid();
                    InfoTracker.pname = apoList.get(position).getPname();
                    InfoTracker.time = System.currentTimeMillis();

                }

                search.clearFocus();
                search.setQuery("", false);
               // menuSearch.collapseActionView();

                Intent intent = new Intent(context, ProductOverView.class);

                context.startActivity(intent);

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

            case R.id.survey_mi:
                if (InfoTracker.usersessionid > 0) {

                    intent = new Intent(getApplicationContext(), QuickSurvey.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                } else {
                    Toast.makeText(context, R.string.casual_mode_toast, Toast.LENGTH_SHORT).show();
                }
               return  true;
            case android.R.id.home:
                intent = new Intent(getApplicationContext(), CategoryGridView.class);
                startActivity(intent);
                finish();
                return  true;
            default:
                Toast.makeText(context, "default", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }


    }

    private void xmlfileinitilization() {
        context = this;
        dba = new DBAdapter(context);
        recList = (RecyclerView) findViewById(R.id.cardList);
        productList = new ArrayList<productObject>();

    }

    private void get_list_of_products() {
        dba.open();
        Cursor mcursor = dba.retriveproductsbycategory(cid);

        if (mcursor.moveToFirst()) {
            do {

                // Log.d("bis","mcursor.getString(4)=="+mcursor.getString(4));
                productObject po = new productObject();
                po.setPid(mcursor.getInt(0));
                po.setCid(mcursor.getInt(1));
                po.setPinfo(mcursor.getString(3));
                po.setPname(mcursor.getString(2));
                po.setPurl(mcursor.getString(4));
                po.setPcost(mcursor.getString(6));

                productList.add(po);
            } while (mcursor.moveToNext());
        }

        if (mcursor != null && !mcursor.isClosed()) {
            mcursor.close();
        }

        dba.close();

        pgva = new ProductGridViewAdapter(this, productList);


        // ====================================================

        recList.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this, 4);
        // llm.setOrientation(GridLayoutManager.);
        recList.setLayoutManager(llm);

        recList.setAdapter(pgva);
        recList.setItemAnimator(new DefaultItemAnimator());

        // item click listner
        recList.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {

                Constants.pid = productList.get(pos).getPid();
                Constants.pname = productList.get(pos).getPname();
                Constants.pcost = productList.get(pos).getPcost();

                if (InfoTracker.usersessionid > 0) {

                    InfoTracker.resetValuesAtProductLevel();

                    InfoTracker.pid = productList.get(pos).getPid();
                    InfoTracker.pname = productList.get(pos).getPname();
                    InfoTracker.time = System.currentTimeMillis();

                }

                Intent intent = new Intent(context, ProductOverView.class);

                context.startActivity(intent);

            }
        }));

    }

    private void get_list_for_actionbar(String query) {


        apoList = new ArrayList<ActionbarProductObject>();

        dba.open();
        Cursor mcursor = dba.retriveallproductsByMatch(query);
        if (mcursor.moveToFirst()) {
            do {
                //Log.d("bis"," mcursor is not empty at PL Sv "+mcursor.getCount());
                ActionbarProductObject apo = new ActionbarProductObject();
                apo.setPid(mcursor.getInt(0));
                apo.setCid(mcursor.getInt(1));
                apo.setPinfo(mcursor.getString(3));
                apo.setPname(mcursor.getString(2));
                apo.setPurl(mcursor.getString(4));
                apo.setPcost(mcursor.getString(6));
                apoList.add(apo);

            } while (mcursor.moveToNext());
        }else
        {
            Log.d("bis"," mcursor is empty at PL Sv");
        }

        dba.close();

    }

    private void prepareListData() {
        listDataHeader_catnames = new ArrayList<String>();
        listDataHeader_cat_ids = new ArrayList<Integer>();

        listDataChild_key_cnames_val_ls = new HashMap<String, List<String>>();
        listDataChild_key_cnames_val_ls2 = new HashMap<String, List<Integer>>();

        getListOfCategories();
        get_list_of_products(listDataHeader_catnames.size());

        for (int i = 0; i < lists_pnames.size(); i++) {
            listDataChild_key_cnames_val_ls.put(listDataHeader_catnames.get(i), lists_pnames.get(i));
            listDataChild_key_cnames_val_ls2.put(listDataHeader_catnames.get(i), lists_pids.get(i));

        }

    }

    private void getListOfCategories() {
        // TODO Auto-generated method stub
        dba.open();
        Cursor mCursor = dba.retriveallcategories();
        dba.close();
        // Log.d("bis", "mCursor==" + mCursor.getCount());

        //Log.d("bis"," cat count= "+mCursor.getCount());

        for (int i = 0; i < mCursor.getCount(); i++) {


            // cateObject.setCurl(mCursor.getString(2));
            // cateObject.setCname(mCursor.getString(1));
            // cateObject.setCid(mCursor.getInt(0));
            listDataHeader_catnames.add(mCursor.getString(1));
            listDataHeader_cat_ids.add(mCursor.getInt(0));

            mCursor.moveToNext();

        }

    }

    private void get_list_of_products(int arrysCount) {
        lists_pnames = new ArrayList<List<String>>();
        lists_pids = new ArrayList<List<Integer>>();

        for (int i = 0; i < arrysCount; i++) {
            lists_pnames.add(new ArrayList<String>());

            lists_pids.add(new ArrayList<Integer>());
            // lists.get(i).add(getproductsBycid(listDataHeader_id.get(i)));

            addProductsToList(i, listDataHeader_cat_ids.get(i));

        }

    }

    private void addProductsToList(int i, int cid) {
        dba.open();
        Cursor mcursor = dba.retriveproductsbycategory(cid);

        //	Log.d("bis"," cat addProductsToList count= "+mcursor.getCount());

        if (mcursor.moveToFirst()) {
            do {

                // Log.d("bis","mcursor.getString(4)=="+mcursor.getString(4));

                // po.setPid(mcursor.getInt(0));
                // po.setCid(mcursor.getInt(1));
                // po.setPinfo(mcursor.getString(3));
                // po.setPname(mcursor.getString(2));
                // po.setPurl(mcursor.getString(4));
                // po.setPcost(mcursor.getString(6));

                // productList.add(po);
                lists_pnames.get(i).add(mcursor.getString(2));
                lists_pids.get(i).add(mcursor.getInt(0));

            } while (mcursor.moveToNext());
        }

        if (mcursor != null && !mcursor.isClosed()) {
            mcursor.close();
        }

        dba.close();

    }


    @Override
    public void onBackPressed() {
        finish();

        intent = new Intent(getApplicationContext(), CategoryGridView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }


}
