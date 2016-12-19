package com.yk.demo.myandroid.db.entity;

import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * greendao 用户实体类
 * @author yk
 * @version V1.0.0
 * created at 2016/12/16 9:31
 */
@Entity(
//        schema = "myschema",   // 一个项目中有多个schema时 标明要让这个dao属于哪个schema

        active = true,  // 是标明是否支持实体类之间update，refresh，delete等操作

        nameInDb = "AWESOME_USERS", // 是标明是否支持实体类之间update，refresh，delete等操作

        indexes = {
                @Index(value = "id DESC", unique = true)  // 定义索引，这里可跨越多个列
        },
        createInDb = true   // 如果是有多个实体都关联这个表，可以把多余的实体里面设置为false避免重复创建（默认是true）
)
public class User {
    @Id(autoincrement = true) //主键 Long型，可以通过@Id(autoincrement = true)设置自增长
    @Index(unique = true)
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "time")
    private long time;
    @Property(nameInDb = "name")
    private String name;
    @Transient
    private String sex; //不存储在数据库中
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
    @Generated(hash = 512467890)
    public User(Long id, long time, String name) {
        this.id = id;
        this.time = time;
        this.name = name;
    }
    @Generated(hash = 586692638)
    public User() {
    }
 
}
