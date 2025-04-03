package app.catalogs;
import app.auction.Viewing;

import java.util.ArrayList;
import java.util.List;

public class ViewingCatalog {
    private List<Viewing> viewings;

    public ViewingCatalog() {
        this.viewings = new ArrayList<>();
    }

    public void addViewing(Viewing viewing) {
        this.viewings.add(viewing);
    }

    public List<Viewing> getViewings() {
        return this.viewings;
    }
}
