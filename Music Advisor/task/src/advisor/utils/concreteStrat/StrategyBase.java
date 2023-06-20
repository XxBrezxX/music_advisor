package advisor.utils.concreteStrat;

import advisor.utils.strategy.Printer;

import java.util.List;

public abstract class StrategyBase<T> implements Printer {
    private List<T> list;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;

    public StrategyBase(List<T> categoryList, Integer pageSize) {
        this.list = categoryList;
        this.pageSize = pageSize;
        this.getTotal();
        this.page = 1;
    }

    public boolean nextPage() {
        if (isInRangeMax(this.page)) {
            this.page++;
            return true;
        }
        return false;
    }

    public boolean prevPage() {
        if (isInRangeMin(this.page)) {
            this.page--;
            return true;
        }
        return false;
    }

    public boolean isInRangeMin(Integer value) {
        return value > 1;
    }

    public boolean isInRangeMax(Integer value) {
        return value < this.totalPages;
    }

    private void getTotal() {
        int possibleEntries = this.list.size() / this.pageSize;
        if (this.list.size() % this.pageSize != 0) possibleEntries++;
        this.totalPages = possibleEntries;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
