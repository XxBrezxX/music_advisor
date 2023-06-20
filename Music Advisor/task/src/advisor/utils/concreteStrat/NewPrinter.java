package advisor.utils.concreteStrat;

import advisor.models.Category;
import advisor.models.New;
import advisor.utils.strategy.Printer;

import java.util.List;

public class NewPrinter extends StrategyBase<New> {

    public NewPrinter(List<New> newList, Integer pageSize) {
        super(newList, pageSize);
    }

    @Override
    public void print() {
        List<New> newList = getList();
        for (
                int elem = (getPage() - 1) * getPageSize();
                elem <= (getPage() * getPageSize()) - 1;
                elem++
        ) {
            System.out.println(newList.get(elem));
        }
        System.out.println(
                "---PAGE ".concat(getPage().toString())
                        .concat(" OF ")
                        .concat(getTotalPages().toString())
                        .concat("---")
        );
    }

}
