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
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;

/**
 *
 * @author miguelangelgutierrezgarcia
 */
class SearchPanel extends javax.swing.JPanel {
    
    private final CyNetworkFactory cyNetworkFactoryServiceRef;
    private final CyNetworkManager netMgr;
    private final CyNetworkViewManager networkViewManager;
    private final CyNetworkViewFactory cnvf;
    private final CyLayoutAlgorithmManager layoutManager;
    private final CyServiceRegistrar registrar;
    
    private final Map<String, CyNode> idCyNodeList = new HashMap<String, CyNode>();
    private final Map<Long, APIDNodeData> suidAPIDNodeDataList = new HashMap<Long, APIDNodeData>();
    private final Map<Long, APIDEdgeData> suidAPIDEdgeDataList = new HashMap<Long, APIDEdgeData>();

    /**
     * Creates new form SearchPanel
     */
    public SearchPanel(CyNetworkFactory cyNetworkFactoryServiceRef, CyNetworkManager netMgr, CyNetworkViewFactory cnvf, 
            CyNetworkViewManager networkViewManager, CyLayoutAlgorithmManager layoutManager, CyServiceRegistrar registrar) {
        initComponents();
        this.cyNetworkFactoryServiceRef = cyNetworkFactoryServiceRef;
        this.netMgr = netMgr;
        this.networkViewManager = networkViewManager;
        this.cnvf = cnvf;
        this.layoutManager = layoutManager;
        this.registrar = registrar;
    }
    
    private CyNetwork createNet(APIDNetworkData data) {
        CyNetwork myNet = cyNetworkFactoryServiceRef.createNetwork();
        myNet.getRow(myNet).set(CyNetwork.NAME, searchName.getText());
        
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
            suidAPIDNodeDataList.put(node.getSUID(), nodeData);
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
            suidAPIDEdgeDataList.put(edge.getSUID(), edgeData);
        }
        
        return myNet;
    }
    
    private CyNetworkView formatNet(CyNetwork net) {
        CyNetworkView nv = cnvf.createNetworkView(net);
        for (View<CyNode> view : nv.getNodeViews()) {
            view.setLockedValue(BasicVisualLexicon.NODE_LABEL, suidAPIDNodeDataList.get(view.getModel().getSUID()).getName());
        }
        for (View<CyEdge> view : nv.getEdgeViews()) {
            view.setLockedValue(BasicVisualLexicon.EDGE_WIDTH, suidAPIDEdgeDataList.get(view.getModel().getSUID()).getCurationevents().doubleValue()/10);
        }        
        CyLayoutAlgorithm layout = layoutManager.getDefaultLayout();
        TaskIterator itr = layout.createTaskIterator(nv, layout.getDefaultLayoutContext(), CyLayoutAlgorithm.ALL_NODE_VIEWS,"");
        SynchronousTaskManager<?> synTaskMan = registrar.getService(SynchronousTaskManager.class);
        synTaskMan.execute(itr); 
        nv.updateView();
        return nv;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchLabel = new javax.swing.JLabel();
        searchName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder("Protein search"), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5))));
        setLayout(new java.awt.BorderLayout());

        searchLabel.setText("Protein");
        add(searchLabel, java.awt.BorderLayout.LINE_START);
        add(searchName, java.awt.BorderLayout.CENTER);

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
            APIDNetworkData networkData = APIDQuery.getInteractions(searchName.getText());
            CyNetwork net = createNet(networkData);
            netMgr.addNetwork(net);
            CyNetworkView nv = formatNet(net);
            networkViewManager.addNetworkView(nv);  
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchName;
    // End of variables declaration//GEN-END:variables
}
