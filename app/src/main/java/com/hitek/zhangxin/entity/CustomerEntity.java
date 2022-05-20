package com.hitek.zhangxin.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author zzj
 * @date 2018/5/31
 */
@Entity
public class CustomerEntity {
    @Id
    private Long phone;
    private double balance;
    private int points;
    private String date;
    private double oldMoney;
    private String name;
    private double coupon;
    @Generated(hash = 149564670)
    public CustomerEntity(Long phone, double balance, int points, String date,
            double oldMoney, String name, double coupon) {
        this.phone = phone;
        this.balance = balance;
        this.points = points;
        this.date = date;
        this.oldMoney = oldMoney;
        this.name = name;
        this.coupon = coupon;
    }
    @Generated(hash = 457785240)
    public CustomerEntity() {
    }
    public Long getPhone() {
        return this.phone;
    }
    public void setPhone(Long phone) {
        this.phone = phone;
    }
    public double getBalance() {
        return this.balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public int getPoints() {
        return this.points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public double getOldMoney() {
        return this.oldMoney;
    }
    public void setOldMoney(double oldMoney) {
        this.oldMoney = oldMoney;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getCoupon() {
        return this.coupon;
    }
    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }
    



}
