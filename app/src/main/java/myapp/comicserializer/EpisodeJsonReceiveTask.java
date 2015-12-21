package myapp.comicserializer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by godcc on 2015/12/20.
 */
public class EpisodeJsonReceiveTask extends JsonReceiveTask implements Runnable {

    private static final String DEBUG_FLAG = EpisodeJsonReceiveTask.class.getName();
    private static final String JSON_EPISODE_URL = "http://140.116.82.59:8080/ComicSerializeFollower/episode.php";

    private static final String COMIC_SET_KEY = "COMIC_SET_KEY";

    private static final String FILE_NAME = "COMIC_EPOSIDE";
    private Map<String, Integer> episodeMap = new HashMap<String, Integer>();

    public EpisodeJsonReceiveTask(Context mContext) {
        super(mContext);
    }

    @Override
    public void run() {
        try {
            jsonParsing(new JSONObject(jsonRecieve(JSON_EPISODE_URL)));

            saveFile(episodeMap, FILE_NAME);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jsonParsing(JSONObject jsonObject) throws Exception{
        final SharedPreferences SP = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        Set<String> comic_set = new HashSet<String>();
        comic_set = SP.getStringSet(COMIC_SET_KEY, comic_set);
        for(String comic_name : comic_set){
            episodeMap.put(comic_name, Integer.parseInt(jsonObject.getString(comic_name)));
            Log.d(DEBUG_FLAG, comic_name + " : " + episodeMap.get(comic_name));
        }
    }


}
