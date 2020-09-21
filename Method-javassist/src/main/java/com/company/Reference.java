package com.company;

public class Reference {
    @OneToMany(tableName = "ReferExample")
    int refer;

    public Reference(int refer) {
        this.refer = refer;
    }
}
