package top.liyf.mywebstore.util;

import java.util.List;

public class Page<T> {

    private List<T> pageList;
    private int totalRecordsNum;
    private int currentPageNum;
    private int totalPageNum;
    private int prevPageNum;
    private int nextPageNum;

    public void setPage(Page<T> page, int totalRecordNumber, int currentPageNum, int limit, List<T> pageList) {
        page.setTotalRecordsNum(totalRecordNumber);
        int totalPageNum = totalRecordNumber / limit + (totalRecordNumber % limit == 0 ? 0 : 1);
        page.setTotalPageNum(totalPageNum);
        page.setCurrentPageNum(currentPageNum);
        page.setPageList(pageList);
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageList=" + pageList +
                ", totalRecordsNum=" + totalRecordsNum +
                ", currentPageNum=" + currentPageNum +
                ", totalPageNum=" + totalPageNum +
                ", prevPageNum=" + prevPageNum +
                ", nextPageNum=" + nextPageNum +
                '}';
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        prevPageNum = (currentPageNum - 1) == 0 ? currentPageNum : currentPageNum - 1;
//        System.out.println("currentPageNum " + currentPageNum + " totalPageNum " + totalPageNum);
        nextPageNum = currentPageNum == totalPageNum ? currentPageNum : currentPageNum + 1;
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getPrevPageNum() {
        return prevPageNum;
    }

    public void setPrevPageNum(int prevPageNum) {
        this.prevPageNum = prevPageNum;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }
}
