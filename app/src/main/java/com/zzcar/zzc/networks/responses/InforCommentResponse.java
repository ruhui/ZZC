package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.InforComment;

import java.util.List;

/**
 * 创建时间： 2017/7/31.
 * 作者：黄如辉
 * 功能描述：
 */

public class InforCommentResponse {
    private int page;
    private int size;
    private int total_pages;
    private int total_result;
    private List<InforComment> rows;

    public InforCommentResponse(int page, int size, int total_pages, int total_result, List<InforComment> rows) {
        this.page = page;
        this.size = size;
        this.total_pages = total_pages;
        this.total_result = total_result;
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_result() {
        return total_result;
    }

    public void setTotal_result(int total_result) {
        this.total_result = total_result;
    }

    public List<InforComment> getRows() {
        return rows;
    }

    public void setRows(List<InforComment> rows) {
        this.rows = rows;
    }
}

//"page": 1,
//        "size": 2,
//        "total_pages": 2,
//        "total_result": 3,
//"page": 1,
//        "size": 2,
//        "total_pages": 2,
//        "total_result": 3,
//        "rows":