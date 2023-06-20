package advisor.utils.concreteStrat;

import advisor.models.Category;

import java.util.List;

public class CategoryPrinter extends StrategyBase<Category> {

    public CategoryPrinter(List<Category> categoryList, Integer pageSize) {
        super(categoryList, pageSize);
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
