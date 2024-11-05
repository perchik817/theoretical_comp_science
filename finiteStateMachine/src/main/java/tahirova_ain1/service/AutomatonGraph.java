package tahirova_ain1.service;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;

public class AutomatonGraph {
    public void createGraph() {
        System.setProperty("org.graphstream.ui", "swing");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new SingleGraph("Finite Automaton");
        graph.setStrict(false);
        graph.setAutoCreate(true);

        // Add nodes and edges representing your finite automaton
        graph.addNode("X").setAttribute("ui.label", "X");
        graph.addNode("Y").setAttribute("ui.label", "Y");
        graph.addNode("Z").setAttribute("ui.label", "Z");
        graph.addNode("V").setAttribute("ui.label", "V");

        graph.addEdge("X_to_Y", "X", "Y", true).setAttribute("ui.label", "(");
        graph.addEdge("Y_to_Y", "Y", "Y", true).setAttribute("ui.label", "y");
        graph.addEdge("Y_to_Z", "Y", "Z", true).setAttribute("ui.label", "z");
        graph.addEdge("Z_to_V", "Z", "V", true).setAttribute("ui.label", "v");

        // Display the graph
        graph.display();
    }
}
