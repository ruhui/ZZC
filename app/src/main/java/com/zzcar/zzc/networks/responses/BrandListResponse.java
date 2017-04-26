package com.zzcar.zzc.networks.responses;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by asus-pc on 2017/4/26.
 */

@Entity
public class BrandListResponse {
    private int id;
    private String name;
    private String first_letter;
    private String logo;
    public String getLogo() {
        return this.logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getFirst_letter() {
        return this.first_letter;
    }
    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Generated(hash = 333550232)
    public BrandListResponse(int id, String name, String first_letter, String logo) {
        this.id = id;
        this.name = name;
        this.first_letter = first_letter;
        this.logo = logo;
    }
    @Generated(hash = 372817689)
    public BrandListResponse() {
    }

}


//  "id": 1,
//          "name": "sample string 2",
//          "first_letter": "sample string 3",
//          "logo": "sample string 4"