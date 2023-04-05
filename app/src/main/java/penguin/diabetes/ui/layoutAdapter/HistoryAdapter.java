package penguin.diabetes.ui.layoutAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import penguin.diabetes.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private String[] localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.tvListItem);       // ««
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public HistoryAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.measure_history_list  , viewGroup, false);    // ««

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}

/*
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    List<SerializableMeasure> measures;

    public HistoryAdapter(List<SerializableMeasure> list){
        measures = list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // -->> Inflating view <<--
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measure_history_list, parent, false);

        return new HistoryViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {

        SerializableMeasure measure = measures.get(position);

        // TODO DORIMEEE View text is changed here:
        
        holder.listItem.setText(measure.getItem());

    }

    @Override
    public int getItemCount() {
        return measures.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
    TextView listItem;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            // Getting view items
            listItem = (TextView) itemView.findViewById(R.id.tvListItem);


        }
    }
}

*/
