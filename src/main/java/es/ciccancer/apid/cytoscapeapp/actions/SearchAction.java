package es.ciccancer.apid.cytoscapeapp.actions;

import es.ciccancer.apid.cytoscapeapp.gui.MainAPIDPanel;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;
import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;

public class SearchAction extends AbstractCyAction {

    private static final long serialVersionUID = 1L;
    private final CySwingApplication application;
    private final MainAPIDPanel mainPanel;
    private final CyServiceRegistrar registrar;

    public SearchAction(Map<String, String> properties,
            CySwingApplication application,
            CyApplicationManager applicationManager,
            CyNetworkViewManager viewManager,
            CyServiceRegistrar registrar,
            CyNetworkFactory cyNetworkFactoryServiceRef,
            CyNetworkManager netMgr,
            CyNetworkViewFactory cnvf,
            CyNetworkViewManager networkViewManager) {

        super(properties, applicationManager, viewManager);
        putValue(NAME, "Search");
        this.application = application;
        this.registrar = registrar;
        this.mainPanel = new MainAPIDPanel(cyNetworkFactoryServiceRef, netMgr, cnvf, networkViewManager);        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CytoPanel cytoPanelWest = application.getCytoPanel(CytoPanelName.WEST);
        // If the state of the cytoPanelWest is HIDE, show it
        if (cytoPanelWest.getState() == CytoPanelState.HIDE) {
            cytoPanelWest.setState(CytoPanelState.DOCK);
        }

        // Select my panel
        int index = cytoPanelWest.indexOfComponent(mainPanel);
        if (index == -1) {
            registrar.registerService(mainPanel, CytoPanelComponent.class, new Properties());
            index = cytoPanelWest.indexOfComponent(mainPanel);
        }
        cytoPanelWest.setSelectedIndex(index);
    }

}
