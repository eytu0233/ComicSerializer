package myapp.comicserializer;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_FLAG = MainActivity.class.getName();

    private static final String COMIC_SET_KEY = "COMIC_SET_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.main_loading_progress);
        progressBar.setVisibility(View.VISIBLE);

        final GridLayout frameLayout = (GridLayout)findViewById(R.id.content_frame);
        final Context context = this;
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {

                EpisodeJsonReaderTask episodeJsonReaderTask = new EpisodeJsonReaderTask(context);
                episodeJsonReaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                try {
                    Map<String, Integer> episodeMap = episodeJsonReaderTask.get();
                    if(episodeMap != null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        for(String key : episodeMap.keySet()){
                            if("ASJS".equals(key)){
                                frameLayout.addView(new ComicCoverLayout(context, key, episodeMap.get(key), R.drawable.asjs));
                            }else if("OnePiece".equals(key)){
                                frameLayout.addView(new ComicCoverLayout(context, key, episodeMap.get(key), R.drawable.onepiece));
                            }else if("SJZL".equals(key)){
                                frameLayout.addView(new ComicCoverLayout(context, key, episodeMap.get(key), R.drawable.sjzl));
                            }else if("WDYXXY".equals(key)){
                                frameLayout.addView(new ComicCoverLayout(context, key, episodeMap.get(key), R.drawable.wdyxxy));
                            }else if("YJDWB".equals(key)){
                                frameLayout.addView(new ComicCoverLayout(context, key, episodeMap.get(key), R.drawable.yjdwb));
                            }else{
                                frameLayout.addView(new ComicCoverLayout(context, key, episodeMap.get(key), R.drawable.sjzl));
                            }

                        }
                       return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 2000);

        /*final SharedPreferences SP = PreferenceManager
                .getDefaultSharedPreferences(this);
        final SharedPreferences.Editor SPE = SP.edit();
        Set<String> comicSet = new HashSet<String>();
        comicSet.add("WDYXXY");
        comicSet.add("OnePiece");
        comicSet.add("YJDWB");
        comicSet.add("ASJS");
        comicSet.add("SJZL");
        SPE.putStringSet(COMIC_SET_KEY, comicSet);
        SPE.apply();*/

        if (!isServiceRunning(NetworkListenerService.class)) {
            if (startService(new Intent(this, NetworkListenerService.class)) != null) {
                Log.d(DEBUG_FLAG, "NetworkListenerService start!");
            } else {
                Log.e(DEBUG_FLAG, "NetworkListenerService start fail!");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Check the service is alive or not
     *
     * @param serviceClass
     * @return Alive state of the service
     */
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
