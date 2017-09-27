package com.cubastion.voltastest.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cubastion.voltastest.R;
import com.cubastion.voltastest.adapters.ListviewAdapter;
import com.cubastion.voltastest.get_set.Users;
import com.cubastion.voltastest.others.Communicator_tabs;
import com.cubastion.voltastest.others.Helper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by Aatish Rana on 2/17/2016.
 */
public class Pending extends Fragment {

    private String TAG = "Voltas";
    private String SCOPE = "Pending";
    private ArrayList<Users> userlist;
    private PullToRefreshListView mPullRefreshListView;
    private ListviewAdapter adapter;
    private View v;
    private boolean HAVE_DATA = false;
    private Communicator_tabs comm;


    public Pending() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comm = (Communicator_tabs) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        userlist = args.getParcelableArrayList("USERLIST");
        if (userlist.size() > 0) {
            HAVE_DATA=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_list, container, false);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPullRefreshListView = (PullToRefreshListView) v.findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setAdapter(null);
        if (HAVE_DATA) {
            mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
            mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                    comm.LoadMore(mPullRefreshListView, Helper.TAB_pending);
                }
            });
            adapter = new ListviewAdapter(getActivity(), userlist);
            mPullRefreshListView.setAdapter(adapter);
        }
        else
        {
            mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        }
        mPullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView srno= (TextView) view.findViewById(R.id.feed_item_sr_tv);
                TextView area= (TextView) view.findViewById(R.id.feed_item_place_tv);
                String SR_no=srno.getText().toString();
                String Area=area.getText().toString();
                comm.ShowDetail(SR_no,Area);
            }
        });
    }
}

