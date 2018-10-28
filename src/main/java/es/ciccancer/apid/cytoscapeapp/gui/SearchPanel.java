/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ciccancer.apid.cytoscapeapp.gui;

import es.ciccancer.apid.cytoscapeapp.APIDQuery;
import es.ciccancer.apid.cytoscapeapp.network.APIDEdgeData;
import es.ciccancer.apid.cytoscapeapp.network.APIDNetworkData;
import es.ciccancer.apid.cytoscapeapp.network.APIDNodeData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;

/**
 *
 * @author miguelangelgutierrezgarcia
 */
class SearchPanel extends javax.swing.JPanel {

    private final CyNetworkFactory cyNetworkFactoryServiceRef;
    private final CyNetworkManager netMgr;
    private final CyNetworkViewManager networkViewManager;
    private final CyNetworkViewFactory cnvf;

    /**
     * Creates new form SearchPanel
     */
    public SearchPanel(CyNetworkFactory cyNetworkFactoryServiceRef, CyNetworkManager netMgr, CyNetworkViewFactory cnvf, CyNetworkViewManager networkViewManager) {
        initComponents();
        this.cyNetworkFactoryServiceRef = cyNetworkFactoryServiceRef;
        this.netMgr = netMgr;
        this.networkViewManager = networkViewManager;
        this.cnvf = cnvf;
    }

    private CyNetwork createNet(APIDNetworkData data) {
        CyNetwork myNet = cyNetworkFactoryServiceRef.createNetwork();
        myNet.getRow(myNet).set(CyNetwork.NAME, "My great new Network");

        // Map id <-> cynode
        Map<String, CyNode> idCyNodeList = new HashMap<String, CyNode>();

        myNet.getDefaultNodeTable().createColumn("id", String.class, true);
        myNet.getDefaultNodeTable().createColumn("uniprotname", String.class, true);
        myNet.getDefaultNodeTable().createColumn("description", String.class, true);
        myNet.getDefaultNodeTable().createColumn("ints", Long.class, true);
        myNet.getDefaultNodeTable().createColumn("border", String.class, true);

        Iterator<APIDNodeData> itNode = data.nodeIterator();
        while (itNode.hasNext()) {
            CyNode node = myNet.addNode();
            APIDNodeData nodeData = itNode.next();
            myNet.getDefaultNodeTable().getRow(node.getSUID()).set("id", nodeData.getId());
            myNet.getDefaultNodeTable().getRow(node.getSUID()).set("name", nodeData.getName());
            myNet.getDefaultNodeTable().getRow(node.getSUID()).set("uniprotname", nodeData.getUniprotname());
            myNet.getDefaultNodeTable().getRow(node.getSUID()).set("description", nodeData.getDescription());
            myNet.getDefaultNodeTable().getRow(node.getSUID()).set("ints", nodeData.getInts());
            myNet.getDefaultNodeTable().getRow(node.getSUID()).set("border", nodeData.getBorder());
            idCyNodeList.put(nodeData.getId(), node);
        }

        myNet.getDefaultEdgeTable().createColumn("source", String.class, true);
        myNet.getDefaultEdgeTable().createColumn("target", String.class, true);
        myNet.getDefaultEdgeTable().createColumn("experiments", Long.class, true);
        myNet.getDefaultEdgeTable().createColumn("methods", Long.class, true);
        myNet.getDefaultEdgeTable().createColumn("pdb", Long.class, true);
        myNet.getDefaultEdgeTable().createColumn("papers", Long.class, true);
        myNet.getDefaultEdgeTable().createColumn("curationevents", Long.class, true);

        Iterator<APIDEdgeData> itEdge = data.edgeIterator();
        while (itEdge.hasNext()) {
            APIDEdgeData edgeData = itEdge.next();
            CyEdge edge = myNet.addEdge(idCyNodeList.get(edgeData.getSource()), idCyNodeList.get(edgeData.getTarget()), true);
            myNet.getDefaultEdgeTable().getRow(edge.getSUID()).set("source", edgeData.getSource());
            myNet.getDefaultEdgeTable().getRow(edge.getSUID()).set("target", edgeData.getTarget());
            myNet.getDefaultEdgeTable().getRow(edge.getSUID()).set("experiments", edgeData.getExperiments());
            myNet.getDefaultEdgeTable().getRow(edge.getSUID()).set("methods", edgeData.getMethods());
            myNet.getDefaultEdgeTable().getRow(edge.getSUID()).set("pdb", edgeData.getPdb());
            myNet.getDefaultEdgeTable().getRow(edge.getSUID()).set("papers", edgeData.getPapers());
            myNet.getDefaultEdgeTable().getRow(edge.getSUID()).set("curationevents", edgeData.getCurationevents());

        }

        return myNet;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder("Protein search"), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5))));
        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Protein");
        add(jLabel1, java.awt.BorderLayout.LINE_START);
        add(jTextField1, java.awt.BorderLayout.CENTER);

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            APIDNetworkData networkData = APIDQuery.getInteractions(jTextField1.getText());
            CyNetwork net = createNet(networkData);
            netMgr.addNetwork(net);
            CyNetworkView nv = cnvf.createNetworkView(net);
            networkViewManager.addNetworkView(nv);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
