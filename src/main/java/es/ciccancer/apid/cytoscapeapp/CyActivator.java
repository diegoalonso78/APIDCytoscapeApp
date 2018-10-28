package es.ciccancer.apid.cytoscapeapp;

import es.ciccancer.apid.cytoscapeapp.actions.AboutAction;
import es.ciccancer.apid.cytoscapeapp.actions.SearchAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.osgi.framework.BundleContext;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;

public class CyActivator extends AbstractCyActivator {

    public CyActivator() {
        super();
    }

    @Override
    public void start(BundleContext bc) {

        CySwingApplication cySwingApplicationRef = getService(bc, CySwingApplication.class);
        CyApplicationManager cyApplicationManagerRef = getService(bc, CyApplicationManager.class);
        CyNetworkViewManager cyNetworkViewManagerRef = getService(bc, CyNetworkViewManager.class);
        CyServiceRegistrar registrar = getService(bc, CyServiceRegistrar.class);
        CyNetworkFactory cyNetworkFactoryServiceRef = getService(bc,CyNetworkFactory.class);
        CyNetworkManager netMgr = getService(bc,CyNetworkManager.class);
        CyNetworkViewFactory cnvf = getService(bc, CyNetworkViewFactory.class);
        CyNetworkViewManager networkViewManager = getService(bc, CyNetworkViewManager.class);

        Map<String, String> serviceProperties;
        serviceProperties = new HashMap<String, String>();
        serviceProperties.put(PREFERRED_MENU, "Apps.APID");
        serviceProperties.put(IN_MENU_BAR, "true");
        serviceProperties.put(MENU_GRAVITY, "1.0");
        //MainAPIDPanel mainPanel = new MainAPIDPanel();
        SearchAction searchPanel = new SearchAction(serviceProperties, cySwingApplicationRef, cyApplicationManagerRef, cyNetworkViewManagerRef, registrar, cyNetworkFactoryServiceRef, netMgr, cnvf, networkViewManager);

        serviceProperties = new HashMap<String, String>();
        serviceProperties.put(PREFERRED_MENU, "Apps.APID");
        serviceProperties.put(IN_MENU_BAR, "true");
        serviceProperties.put(MENU_GRAVITY, "2.0");
        AboutAction aboutPanel = new AboutAction(serviceProperties, cySwingApplicationRef, cyApplicationManagerRef, cyNetworkViewManagerRef);

        //registerService(bc, mainPanel,CytoPanelComponent.class, new Properties());
        registerService(bc, searchPanel, CyAction.class, new Properties());
        registerService(bc, aboutPanel, CyAction.class, new Properties());

        /*serviceProperties = new HashMap<String, String>();
        serviceProperties.put("inMenuBar", "true");
        serviceProperties.put("preferredMenu", "Apps.GeneMANIA");
        serviceProperties.put(MENU_GRAVITY, "2.0");
        DownloadDataSetAction downloadDataSetAction = new DownloadDataSetAction(
                serviceProperties, cyApplicationManagerRef, geneMania, cyNetworkViewManagerRef);*/
        // MainAPIDPanel mainPanel = new MainAPIDPanel();
        // MainPanelAction controlPanelAction = new MainPanelAction(cySwingApplicationRef, mainPanel);
        // registerService(bc, mainPanel, CytoPanelComponent.class, new Properties());
        // registerService(bc, controlPanelAction, CyAction.class, new Properties());
    }

}
