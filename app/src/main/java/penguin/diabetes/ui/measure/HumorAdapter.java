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
        switch (humor){
            case 1:
                tvHumorRating.setText(R.string.med_humor_rating1);
                break;
            case 2:
                tvHumorRating.setText(R.string.med_humor_rating2);
                break;
            case 3:
                tvHumorRating.setText(R.string.med_humor_rating3);
                break;
            case 4:
                tvHumorRating.setText(R.string.med_humor_rating4);
                break;
            case 5:
                tvHumorRating.setText(R.string.med_humor_rating5);
                break;
        }
    }
//FIXME
//    public String humorString(int ratting){
//        String humor;
//        switch (ratting){
//            case 1:
//                humor = R.string.med_humor_rating1 ;
//                break;
//            case 2:
//                tvHumorRating.setText(R.string.med_humor_rating2);
//                break;
//            case 3:
//                tvHumorRating.setText(R.string.med_humor_rating3);
//                break;
//            case 4:
//                tvHumorRating.setText(R.string.med_humor_rating4);
//                break;
//            case 5:
//                tvHumorRating.setText(R.string.med_humor_rating5);
//                break;
//        }
//    }

    public int getHumor() {
        return humor;
    }
}
