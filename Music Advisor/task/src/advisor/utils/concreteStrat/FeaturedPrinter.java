package advisor.utils.concreteStrat;

import advisor.models.Featured;

import java.util.List;

public class FeaturedPrinter extends StrategyBase<Featured> {

    public FeaturedPrinter(List<Featured> featuredList, Integer pageSize) {
        super(featuredList, pageSize);
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
