package es.ciccancer.apid.cytoscapeapp.actions;

import es.ciccancer.apid.cytoscapeapp.gui.AboutDialog;
import java.awt.event.ActionEvent;
import java.util.Map;
import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.view.model.CyNetworkViewManager;

public class AboutAction extends AbstractCyAction {

    private static final long serialVersionUID = 1L;
    private CySwingApplication application;

    public AboutAction(Map<String, String> properties,
            CySwingApplication application,
            CyApplicationManager applicationManager,
            CyNetworkViewManager viewManager) {

        super(properties, applicationManager, viewManager);
        putValue(NAME, "About");
        this.application = application;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AboutDialog dialog = new AboutDialog(application.getJFrame(), true);
        dialog.setLocationByPlatform(true);
        dialog.setVisible(true);
    }

}
