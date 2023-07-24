
package mst;

public class Edge {
    int vertexOrigin; // Origin
    int vertexDest; // Destination
    double weight; // Bobot

    public Edge(int vertexOrigin, int vertexDest, double weight) {
        this.vertexOrigin = vertexOrigin;
        this.vertexDest = vertexDest;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "["+vertexOrigin+"-"+vertexDest+" = "+weight+']';
    }
}
