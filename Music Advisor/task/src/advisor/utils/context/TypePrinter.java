package advisor.utils.context;

import advisor.utils.strategy.Printer;

public class TypePrinter {
    private Printer printer;

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public void print() {
        printer.print();
    }

    public boolean next() {
        return printer.nextPage();
    }

    public boolean prev() {
        return printer.prevPage();
    }

}
