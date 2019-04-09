package com.example.android.notepad;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by User on 7/20/2018.
 */
public class MainActivityTest {
    MainActivity mact=null;
    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);
    @Before
    public void setUp() throws Exception {
        mact=rule.getActivity();
    }
@Test
public void listview(){

    View view=mact.findViewById(R.id.listview);
    assertThat(view,notNullValue());
    ListView lv=(ListView)view;
    ListAdapter ad=lv.getAdapter();
    assertThat(ad.getCount(),greaterThan(3));
}
    @After
    public void tearDown() throws Exception {
mact=null;
    }

}