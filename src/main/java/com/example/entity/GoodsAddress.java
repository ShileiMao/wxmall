package com.example.entity;

import javax.persistence.*;

@Table(name = "goods_address")
public class GoodsAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    public Long userId;

    @Column(name = "province")
    public String province;

    @Column(name = "city")
    public String city;

    @Column(name = "district")
    public String district;

    @Column(name = "addr")
    public String addr;
}
