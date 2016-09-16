package testsample.altvr.com.testsample.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import testsample.altvr.com.testsample.R;
import testsample.altvr.com.testsample.fragments.PhotosFragment;
import testsample.altvr.com.testsample.fragments.SavedPhotosFragment;
import testsample.altvr.com.testsample.service.ApiService;
import testsample.altvr.com.testsample.util.LogUtil;

public class MainActivity extends AppCompatActivity  {
    private LogUtil log = new LogUtil(MainActivity.class);
    private ApiService mService;
    SearchView mSearchView;
    private ViewPager mViewPager;
    private MyPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = new ApiService(this);
        mService.getDefaultPhotos();
        mViewPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //displayFragment(PhotosFragment.newInstance(), R.string.toolbar_main_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(queryTextListener);
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            mService.searchPhotos(query);
            mSearchView.clearFocus();
            return true;
        }
    };





    private void displayFragment(Fragment fragment, int title) {
        setTitle(title);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    LogUtil.log("TEMPER");
                    return PhotosFragment.newInstance();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    LogUtil.log("TEMPER 2");
                    return SavedPhotosFragment.newInstance();
                default:
                    return null;
            }
        }



        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            LogUtil.log("POSITION IS "+position);
            return "Page " + position;
        }

    }



}

