package penguin.diabetes.ui.measure;


import android.widget.RatingBar;
import android.widget.TextView;

import penguin.diabetes.R;

public class HumorAdapter implements RatingBar.OnRatingBarChangeListener {
    private RatingBar rbHumor;
    private TextView tvHumorRating;
    private int humor;

    public HumorAdapter(RatingBar ratingBar, TextView textView) {
        rbHumor = ratingBar;
        tvHumorRating = textView;
        rbHumor.setOnRatingBarChangeListener(this);
        humor = 3;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        humor = (int) v;
        tvHumorRating.setText(Measure.humorNumberToString(humor));
    }

    public int getHumor() {
        return humor;
    }
}
