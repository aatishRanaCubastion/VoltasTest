package com.cubastion.voltastest.others;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by Aatish Rana on 2/24/2016.
 */
public interface Communicator_tabs {

    public void LoadMore(PullToRefreshListView pullToRefreshListView,String tab);

    public void ShowDetail(String SRno,String area);
}
