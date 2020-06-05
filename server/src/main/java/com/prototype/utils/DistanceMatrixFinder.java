package main.java.com.prototype.utils;

import main.java.com.prototype.model.Grid;
import main.java.com.prototype.model.Node;

import java.util.List;

public class DistanceMatrixFinder {
    public static double[][] findDistanceMatrix(Grid grid){
        List<Node> customers = grid.getCustomers();
        Node source = grid.getSource();
        int noOfCustomers = customers.size();
        double[][] distanceMatrix = new double[noOfCustomers+1][noOfCustomers+1];

        for(int i=0;i<noOfCustomers;i++){
            Node customerOne = customers.get(i);
            double distanceOne = Math.sqrt(Math.pow(source.getR()-customerOne.getR(),2)+Math.pow(source.getC()-customerOne.getC(),2));
            distanceMatrix[0][i+1] = distanceOne;
            distanceMatrix[i+1][0] = distanceOne;

            for(int j=i+1;j<noOfCustomers;j++){
                Node customerTwo = customers.get(j);
                double distanceTwo = Math.sqrt(Math.pow(customerTwo.getR()-customerOne.getR(),2)+Math.pow(customerTwo.getC()-customerOne.getC(),2));
                distanceMatrix[i+1][j+1] = distanceTwo;
                distanceMatrix[j+1][i+1] = distanceTwo;
            }
        }

//        To print distance matrix
//        for (int i = 0; i <= noOfCustomers; i++) {
//            for (int j = 0; j <= noOfCustomers; j++) {
//                System.out.print(distanceMatrix[i][j] + "  ");
//            }
//            System.out.println();
//        }

        return distanceMatrix;
    }
}
