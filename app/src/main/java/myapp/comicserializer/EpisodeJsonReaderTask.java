package myapp.comicserializer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * Created by godcc on 2015/12/21.
 */
public class EpisodeJsonReaderTask extends AsyncTask<Void, Void, Map<String, Integer>> {

    private static final String FILE_NAME = "COMIC_EPOSIDE";
    private static final String DEBUG_FLAG = EpisodeJsonReaderTask.class.getName();

    private Context mContext;

    public EpisodeJsonReaderTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Map<String, Integer> doInBackground(Void... params) {
        File inputFile = null;
        ObjectInputStream ois = null;
        Map<String, Integer> episodeMap = null;

        try {
            inputFile = new File(mContext
                    .getFilesDir(), FILE_NAME);

            if (!inputFile.exists()) {
                Log.d(DEBUG_FLAG, "file is not exist.");
                return null;
            } else {
                ois = new ObjectInputStream(new FileInputStream(inputFile));
                episodeMap = (Map<String, Integer>) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return episodeMap;
    }

}
