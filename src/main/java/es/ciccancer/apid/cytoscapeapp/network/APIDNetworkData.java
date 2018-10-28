/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ciccancer.apid.cytoscapeapp.network;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author miguelangelgutierrezgarcia
 */
public class APIDNetworkData {
    private ArrayList<APIDNodeData> nodeList;
    private ArrayList<APIDEdgeData> edgeList;

    public APIDNetworkData(ArrayList<APIDNodeData> nodeList, ArrayList<APIDEdgeData> edgeList) {
        this.nodeList = nodeList;
        this.edgeList = edgeList;
    }

    class NodeIterator<APIDNodeData> implements Iterator {

        Iterator it = nodeList.iterator();
        
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Object next() {
            return it.next();
        }
        
    }
    
    public Iterator nodeIterator() {
        return new NodeIterator<APIDNodeData>();
    }
    
    class EdgeIterator<APIDEdgeData> implements Iterator {

        Iterator it = edgeList.iterator();
        
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Object next() {
            return it.next();
        }
        
    }
    
    public Iterator edgeIterator() {
        return new EdgeIterator<APIDEdgeData>();
    }
    
}
