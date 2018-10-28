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
public class APIDEdgeData {
    private String source;
    private String target;
    private Long experiments;
    private Long methods;
    private Long pdb;
    private Long papers;
    private Long curationevents;

    public APIDEdgeData(String source, String target, Long experiments, Long methods, Long pdb, Long papers, Long curationevents) {
        this.source = source;
        this.target = target;
        this.experiments = experiments;
        this.methods = methods;
        this.pdb = pdb;
        this.papers = papers;
        this.curationevents = curationevents;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Long getExperiments() {
        return experiments;
    }

    public void setExperiments(Long experiments) {
        this.experiments = experiments;
    }

    public Long getMethods() {
        return methods;
    }

    public void setMethods(Long methods) {
        this.methods = methods;
    }

    public Long getPdb() {
        return pdb;
    }

    public void setPdb(Long pdb) {
        this.pdb = pdb;
    }

    public Long getPapers() {
        return papers;
    }

    public void setPapers(Long papers) {
        this.papers = papers;
    }

    public Long getCurationevents() {
        return curationevents;
    }

    public void setCurationevents(Long curationevents) {
        this.curationevents = curationevents;
    }
    
}
