/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ciccancer.apid.cytoscapeapp.network;

/**
 *
 * @author miguelangelgutierrezgarcia
 */
public class APIDNodeData {
    private String id;
    private String name;
    private String uniprotname;
    private String description;
    private Long ints;
    private String border;

    public APIDNodeData(String id, String name, String uniprotname, String description, Long ints, String border) {
        this.id = id;
        this.name = name;
        this.uniprotname = uniprotname;
        this.description = description;
        this.ints = ints;
        this.border = border;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniprotname() {
        return uniprotname;
    }

    public void setUniprotname(String uniprotname) {
        this.uniprotname = uniprotname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInts() {
        return ints;
    }

    public void setInts(Long ints) {
        this.ints = ints;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }
    
}
