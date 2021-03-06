package myapp.comicserializer;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonReceiveTask {

    private static final String DEBUG_FLAG = JsonReceiveTask.class.getName();
    protected Context mContext;

    public JsonReceiveTask(Context mContext) {
        this.mContext = mContext;
    }

    protected final String jsonRecieve(final String jsonURL) {
        HttpURLConnection urlConnection = null;
        StringBuilder responseStrBuilder = null;

        try {
            URL url = new URL(jsonURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader streamReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

        } catch (ConnectException e) {
            // TODO Auto-generated catch block
            Log.e(DEBUG_FLAG, "網頁連線逾時");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return responseStrBuilder.toString();
    }

    protected void saveFile(final Object data, final String fileName) throws IOException, NullPointerException {
        if (data == null) {
            throw new NullPointerException("argument data is null.");
        }

        try {
            /* Get internal storage directory */
            File dir = mContext.getFilesDir();
            File activityFile = new File(dir, fileName);

            ObjectOutputStream oos = null;

            oos = new ObjectOutputStream(new FileOutputStream(activityFile));
            oos.writeObject(data);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw e;
        }
    }

}