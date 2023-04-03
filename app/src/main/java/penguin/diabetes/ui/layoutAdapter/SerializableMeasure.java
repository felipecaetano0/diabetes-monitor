package penguin.diabetes.ui.layoutAdapter;

import java.io.Serializable;

public class SerializableMeasure implements Serializable {
    private long id;

    // TODO FIXME EEEEEEEEEEEEE
    private String item;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
