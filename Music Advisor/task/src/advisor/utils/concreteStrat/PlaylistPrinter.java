package advisor.utils.concreteStrat;

import advisor.models.Category;
import advisor.models.Playlist;
import advisor.utils.strategy.Printer;

import java.util.List;

public class PlaylistPrinter extends StrategyBase<Playlist> {
    public PlaylistPrinter(List<Playlist> playlists, Integer pageSize) {
        super(playlists, pageSize);
    }

    @Override
    public void print() {
        for (
                int elem = (getPage() - 1) * getPageSize();
                elem <= (getPage() * getPageSize()) - 1;
                elem++
        ) {
            System.out.println(getList().get(elem));
        }
        System.out.println(
                "---PAGE ".concat(getPage().toString())
                        .concat(" OF ")
                        .concat(getTotalPages().toString())
                        .concat("---")
        );
    }
}
