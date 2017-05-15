package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.IntegralDetail;

import java.util.List;

/**
 * 描述：积分详情
 * 作者：黄如辉  时间 2017/5/15.
 */

public class IntegralDetailResponse {
    private int page;
    private int size;
    private int total_pages;
    private int total_result;

    private List<IntegralDetail> rows;

    public IntegralDetailResponse(int page, int size, int total_pages, int total_result, List<IntegralDetail> rows) {
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

    public List<IntegralDetail> getRows() {
        return rows;
    }

    public void setRows(List<IntegralDetail> rows) {
        this.rows = rows;
    }
}
// "page": 1,
//         "size": 2,
//         "total_pages": 2,
//         "total_result": 3,
//         "rows": [
//         {
//         "id": 1,
//         "name": "sample string 3",
//         "create_time": "2017-05-15 22:37:23",
//         "integral": 5
//         },
//         {
//         "id": 1,
//         "name": "sample string 3",
//         "create_time": "2017-05-15 22:37:23",
//         "integral": 5
//         }
//         ]
