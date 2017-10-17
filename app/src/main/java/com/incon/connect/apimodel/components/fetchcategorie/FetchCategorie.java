package com.incon.connect.apimodel.components.fetchcategorie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.connect.apimodel.components.search.Division;

import java.util.List;

/**
 * Created by PC on 10/17/2017.
 */

public class FetchCategorie {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("divisions")
    @Expose
    private Division division;
    private Brand brand;
    private transient String divisionName;

    private List<Division> divisions = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

}

