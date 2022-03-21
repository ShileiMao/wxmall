package com.example.entity;

import javax.persistence.*;

@Table(name = "user_addr")
public class UserAddr {
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
