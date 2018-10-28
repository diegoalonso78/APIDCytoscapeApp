/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ciccancer.apid.cytoscapeapp;

import es.ciccancer.apid.cytoscapeapp.network.APIDEdgeData;
import es.ciccancer.apid.cytoscapeapp.network.APIDNetworkData;
import es.ciccancer.apid.cytoscapeapp.network.APIDNodeData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author miguelangelgutierrezgarcia
 */
public class APIDQuery {

    public static APIDNetworkData getInteractions(String protein) throws IOException, ParseException {
        URL ficheroUrl = new URL("http://cicblade.dep.usal.es:8080/APIDdev/InteractionsNetworkCytoscape.action?protein=" + protein);
        BufferedReader in = new BufferedReader(new InputStreamReader(ficheroUrl.openStream()));
        String lineaURL;
        String interactions = "";
        while ((lineaURL = in.readLine()) != null) {
            String linea = lineaURL.trim();
            interactions += linea;
        }
        in.close();

        ArrayList<APIDNodeData> nodeList = new ArrayList<APIDNodeData>();
        ArrayList<APIDEdgeData> edgeList = new ArrayList<APIDEdgeData>();
        JSONParser helper = new JSONParser();
        JSONArray data = (JSONArray) helper.parse(interactions.replaceAll("&quot;", "\""));        //validJSON);     //interactions);
        JSONObject nodes = (JSONObject) data.get(0);
        JSONObject edges = (JSONObject) data.get(1);
        JSONArray nodesArray = (JSONArray) nodes.get("nodes");
        Iterator it = nodesArray.iterator();
        while (it.hasNext()) {
            JSONObject element = (JSONObject) it.next();
            JSONObject properties = (JSONObject) element.get("data");
            String id = (String) properties.get("id");
            String name = (String) properties.get("name");
            String uniprotname = (String) properties.get("uniprotname");
            String description = (String) properties.get("description");
            Long ints = (Long) properties.get("ints");
            String border = (String) properties.get("border");
            APIDNodeData node = new APIDNodeData(id, name, uniprotname, description, ints, border);
            nodeList.add(node);
        }
        JSONArray edgesArray = (JSONArray) edges.get("edges");
        it = edgesArray.iterator();
        while (it.hasNext()) {
            JSONObject element = (JSONObject) it.next();
            JSONObject properties = (JSONObject) element.get("data");
            String source = (String) properties.get("source");
            String target = (String) properties.get("target");
            Long experiments = (Long) properties.get("experiments");
            Long methods = (Long) properties.get("methods");
            Long pdb = (Long) properties.get("pdb");
            Long papers = (Long) properties.get("papers");
            Long curationevents = (Long) properties.get("curationevents");
            APIDEdgeData node = new APIDEdgeData(source, target, experiments, methods, pdb, papers, curationevents);
            edgeList.add(node);
        }
        return new APIDNetworkData(nodeList, edgeList);

    }
}
