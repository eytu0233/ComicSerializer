package myapp.comicserializer;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by godcc on 2015/12/20.
 */
public class ComicCoverLayout extends FrameLayout {

    private ImageView comic_cover_img;
    private TextView comic_cover, comic_episode;

    public ComicCoverLayout(Context context, String title, int episode, int cover_img_resource) {
        super(context);
        init(context);
        setComic_title(title);
        setComic_episode(episode);
        setComic_cover_img(cover_img_resource);
    }

    private void init(Context context) {

        inflate(context, R.layout.fragment_comic_cover_item_, this);
        comic_cover_img = (ImageView) findViewById(R.id.comic_cover_img);
        comic_cover = (TextView) findViewById(R.id.comic_cover);
        comic_episode = (TextView) findViewById(R.id.episode);

    }

    private void setComic_title(String comic_title){
        comic_cover.setText(comic_title);
    }

    private void setComic_episode(int episode){
        comic_episode.setText(episode + "話");
    }

    private void setComic_cover_img(int cover_img_resource) {
        comic_cover_img.setImageResource(cover_img_resource);
    }


}
