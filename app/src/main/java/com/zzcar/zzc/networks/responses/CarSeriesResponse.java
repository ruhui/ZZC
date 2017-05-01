package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.CarfactoryDto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.zzcar.greendao.DaoSession;
import com.zzcar.greendao.CarSeriesResponseDao;
import com.zzcar.greendao.CarfactoryDtoDao;

/**
 * Created by asus-pc on 2017/4/30.
 */

@Entity
public class CarSeriesResponse {
    @Id
    private long id;
    private long bland_id;
    private String name;
    private String first_letter;
    @ToMany(referencedJoinProperty = "factory_id")
    private List<CarfactoryDto> series;
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 690907782)
    public synchronized void resetSeries() {
        series = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1812827373)
    public List<CarfactoryDto> getSeries() {
        if (series == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CarfactoryDtoDao targetDao = daoSession.getCarfactoryDtoDao();
            List<CarfactoryDto> seriesNew = targetDao._queryCarSeriesResponse_Series(id);
            synchronized (this) {
                if(series == null) {
                    series = seriesNew;
                }
            }
        }
        return series;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 86725869)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCarSeriesResponseDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 743927935)
    private transient CarSeriesResponseDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
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
    public long getBland_id() {
        return this.bland_id;
    }
    public void setBland_id(long bland_id) {
        this.bland_id = bland_id;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 979998679)
    public CarSeriesResponse(long id, long bland_id, String name,
            String first_letter) {
        this.id = id;
        this.bland_id = bland_id;
        this.name = name;
        this.first_letter = first_letter;
    }
    @Generated(hash = 2069428799)
    public CarSeriesResponse() {
    }
}
//id   车厂id，用于排序
//bland_id   品牌id
//name  名称
//first_letter 首字母
//series  车系集合