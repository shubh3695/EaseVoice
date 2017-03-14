package kushubham.com.easevoice;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by admin on 2/18/2016.
 */
public class SearchActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String searchQuery=bundle.getString("search");
        Intent Search = new Intent(Intent.ACTION_WEB_SEARCH);
        Search.putExtra(SearchManager.QUERY, searchQuery);
        startActivity(Search);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
