package com.cubastion.voltastest;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cubastion.voltastest.asynctasks.AsyncFetchDetail;
import com.cubastion.voltastest.asynctasks.AsyncFetchList;
import com.cubastion.voltastest.fragments.All;
import com.cubastion.voltastest.fragments.Open;
import com.cubastion.voltastest.fragments.OpenToday;
import com.cubastion.voltastest.fragments.Pending;
import com.cubastion.voltastest.get_set.Login;
import com.cubastion.voltastest.get_set.Users;
import com.cubastion.voltastest.others.Communicator_tabs;
import com.cubastion.voltastest.others.DatabaseHandler;
import com.cubastion.voltastest.others.FilterData;
import com.cubastion.voltastest.others.NetCheck;
import com.cubastion.voltastest.others.Helper;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aatish Rana on 2/16/2016.
 */
public class HomeActivity extends AppCompatActivity implements Communicator_tabs {

    /*
    * This is the main class which contains all the fragments which are the different tabs in which data are showed
    * HomeActivity contains many custom implemented functions which help in transferring data to and from here and different
    * fragments.*/
    private String TAG = "Voltas";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Users> userlistdata = new ArrayList<Users>();
    private FilterData fd;
    private NetCheck nc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        ArrayList<Users> userlist = new ArrayList<>();
        SharedPreferences login_details = HomeActivity.this.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        if (login_details.contains(Helper.Offline_data)) {
            if (login_details.getBoolean(Helper.Offline_data, false)) {
                DatabaseHandler db = new DatabaseHandler(HomeActivity.this);
                userlist = db.getallUsers();
            }
        }
        if (userlist.size() > 0) {
            userlistdata = userlist;
        }

        nc = new NetCheck(HomeActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.voltas_logo3);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (userlistdata.size() > 0) {
            fd = new FilterData(userlistdata);
            setupViewPager(viewPager);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        String tabName = login_details.getString(Helper.LastTabOpenName, "");
        TabLayout.Tab tab = tabLayout.getTabAt(getTabIndex(tabName));
        tab.select();

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void logout() {
        SharedPreferences login_details = HomeActivity.this.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        if (login_details.contains(Helper.Cred_login_id)) {
            login_details.edit().clear().commit();
            DatabaseHandler db = new DatabaseHandler(HomeActivity.this);
            db.deleteAll();
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
            HomeActivity.this.finish();
        }
    }

    private void refresh() {
        SharedPreferences login_details = HomeActivity.this.getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        if (login_details.contains(Helper.Cred_login_id)) {
            String login_name = login_details.getString(Helper.Cred_login_username, "");
            String login_password = login_details.getString(Helper.Cred_login_SecurityAnswer, "");
            String login_id = login_details.getString(Helper.Cred_login_id, "");
            Login login_cred = new Login(login_name, login_password);
            login_cred.setId(login_id);
            if (nc.isNetworkAvailable()) {
                new AsyncFetchList(HomeActivity.this, login_cred, 1).execute();
            } else {
                Toast.makeText(HomeActivity.this, Helper.Error_No_Network, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_exit:
                HomeActivity.this.finish();
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundleAll = new Bundle();
        bundleAll.putParcelableArrayList("USERLIST", userlistdata);

        Bundle bundleOpenToday = new Bundle();
        bundleOpenToday.putParcelableArrayList("USERLIST", fd.getfiltered_OpenTodayList());

        Bundle bundleOpen = new Bundle();
        bundleOpen.putParcelableArrayList("USERLIST", fd.getfiltered_OpenList());

        Bundle bundlePending = new Bundle();
        bundlePending.putParcelableArrayList("USERLIST", fd.getfiltered_PendingList());


        OpenToday opt = new OpenToday();
        opt.setArguments(bundleOpenToday);

        Open op = new Open();
        op.setArguments(bundleOpen);

        Pending pen = new Pending();
        pen.setArguments(bundlePending);

        All al = new All();
        al.setArguments(bundleAll);

        adapter.addFragment(opt, Helper.TAB_opentoday + "\n" + fd.getfiltered_OpenToday_count());
        adapter.addFragment(op, Helper.TAB_open + "\n" + fd.getfiltered_Open_count());
        adapter.addFragment(pen, Helper.TAB_pending + "\n" + fd.getfiltered_Pending_count());
        adapter.addFragment(al, Helper.TAB_all + "\n" + fd.getCount());
        viewPager.setAdapter(adapter);

    }

    @Override
    public void LoadMore(PullToRefreshListView mPullRefreshListView, String tabname) {

        SharedPreferences login_details = getSharedPreferences(Helper.SharedPref_Login, Context.MODE_PRIVATE);
        String login_name = login_details.getString(Helper.Cred_login_username, "");
        String login_password = login_details.getString(Helper.Cred_login_SecurityAnswer, "");
        String login_id = login_details.getString(Helper.Cred_login_id, "");
        int count = login_details.getInt(Helper.Cred_lastIndex, 0);
        int totalcount = Integer.parseInt(login_details.getString(Helper.Cred_totalCount, ""));
        Login login_cred = new Login(login_name, login_password);
        login_cred.setId(login_id);
        login_cred.setStartrownum(count + "");
        String lastTabName = login_details.getString(Helper.LastTabOpenName, "");

        if(Helper.DEBUG)Log.d("login detailes", "" + login_id + "," + login_name + "," + login_password + ",last index-" + count + ",total count-" + totalcount + " last tab name=" + lastTabName);
        if (count == totalcount) {
            Toast.makeText(HomeActivity.this, Helper.NO_MORE_DATA, Toast.LENGTH_LONG).show();
            mPullRefreshListView.onRefreshComplete();
        } else {
            Toast.makeText(HomeActivity.this, "Loading more records, please wait...", Toast.LENGTH_LONG).show();
            new AsyncFetchList(HomeActivity.this, login_cred, 2, mPullRefreshListView, tabname).execute();
        }

    }

    @Override
    public void ShowDetail(String SRno,String area) {

        if(nc.isNetworkAvailable()) {
            new AsyncFetchDetail(SRno, area, HomeActivity.this).execute();
        }
        else
        {
            Toast.makeText(HomeActivity.this, Helper.Error_No_Network,Toast.LENGTH_LONG).show();
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private int getTabIndex(String s) {
        if (s.equals(Helper.TAB_opentoday)) {
            return 0;
        } else if (s.equals(Helper.TAB_open)) {
            return 1;
        } else if (s.equals(Helper.TAB_pending)) {
            return 2;
        } else if (s.equals(Helper.TAB_all)) {
            return 3;
        } else {
            return 0;
        }
    }
}


