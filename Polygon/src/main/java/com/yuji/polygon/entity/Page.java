package com.yuji.polygon.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @className: Page
 * @description: 分页类
 * @author: yuji
 * @create: 2020-08-31 21:59
 **/

public class Page<T> implements Serializable {

    //当前页
    private int pageNum;

    //每页显示记录数
    private int pageSize=10;

    //总记录数
    private int totalCount;

    //总页数
    private int totalPageNum;

    //上一页
    private int prePageNum;

    //下一页
    private int nextPageNum;


    //结果集
    private List<T> records;

    public Page(int pageNum, int totalCount, List<T> records){
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        this.totalPageNum = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1;
        this.records = records;
    }

    public int getPrePageNum(){
        prePageNum = pageNum - 1;
        if (prePageNum < 1){
            prePageNum = 1;
        }
        return prePageNum;
    }

    public int getNextPageNum(){
        nextPageNum = pageNum + 1;
        if (nextPageNum > totalPageNum){
            nextPageNum = totalPageNum;
        }
        return nextPageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
