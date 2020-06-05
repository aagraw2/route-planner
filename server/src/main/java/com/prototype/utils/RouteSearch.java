package main.java.com.prototype.utils;

import com.google.gson.Gson;
import main.java.com.prototype.model.Node;
import main.java.com.prototype.model.Vehicle;
import main.java.com.prototype.model.findRouteResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RouteSearch {
    int NoOfVehicles;
    int NoOfCustomers;
    Vehicle[] Vehicles;
    double Cost;

    public Vehicle[] bestSolutionVehicles;
    double bestSolutionCost;

    public RouteSearch(int noOfVehicles , int capacity, Node source, List<Node> customers)
    {
        this.NoOfVehicles = noOfVehicles;
        this.NoOfCustomers = customers.size();
        this.Cost = 0;
        Vehicles = new Vehicle[NoOfVehicles];
        bestSolutionVehicles =  new Vehicle[NoOfVehicles];

        for (int i = 0 ; i < NoOfVehicles; i++)
        {
            Vehicles[i] = new Vehicle(i,capacity,source);
            bestSolutionVehicles[i] = new Vehicle(i,capacity,source);
        }

        for (int i = 0 ; i < NoOfCustomers; i++)
        {
            customers.get(i).setNodeId(i+1);
        }

    }

    public boolean UnassignedCustomerExists(List<Node> customers){
        for (Node customer : customers) {
            if (!customer.isRouted()) {
                return true;
            }
        }
        return false;
    }

    public findRouteResponse GreedySolution(Node source, List<Node> customers , double[][] CostMatrix) throws Exception {

        double candidateCost,endCost;
        int vehicleIndex = 0;

        while (UnassignedCustomerExists(customers)) {

            int customerIndex = 0;
            Node Candidate = null;
            double minCost = (float) Double.MAX_VALUE;

            if (Vehicles[vehicleIndex].getRoute().isEmpty())
            {
                Vehicles[vehicleIndex].addNodeToRoute(source);
            }

            for (int i = 0; i < NoOfCustomers; i++) {
                if (!customers.get(i).isRouted()) {
                    if (Vehicles[vehicleIndex].isAllowed(customers.get(i).getDemand())) {
                        candidateCost = CostMatrix[Vehicles[vehicleIndex].getCurrentLocation()][i+1];
                        if (minCost > candidateCost) {
                            minCost = candidateCost;
                            customerIndex = i;
                            Candidate = customers.get(i);
                        }
                    }
                }
            }

            if ( Candidate  == null)
            {
                if ( vehicleIndex+1 < Vehicles.length )
                {
                    if (Vehicles[vehicleIndex].getCurrentLocation() != 0) {
                        endCost = CostMatrix[Vehicles[vehicleIndex].getCurrentLocation()][0];
                        Vehicles[vehicleIndex].addNodeToRoute(source);
                        this.Cost +=  endCost;
                    }
                    vehicleIndex = vehicleIndex+1;
                }
                else
                {
                    //ToDo : define custom exception
                    throw new Exception("Solution not feasible under given constrains");
                }
            }
            else
            {
                Vehicles[vehicleIndex].addNodeToRoute(Candidate);
                customers.get(customerIndex).setRouted(true);
                this.Cost += minCost;
            }
        }

        endCost = CostMatrix[Vehicles[vehicleIndex].getCurrentLocation()][0];
        Vehicles[vehicleIndex].addNodeToRoute(source);
        this.Cost +=  endCost;

        return new findRouteResponse(Vehicles, Cost);
    }

    public findRouteResponse IntraRouteLocalSearch(Node source, List<Node> customers , double[][] CostMatrix, int MAX_ITERATIONS) throws Exception {

        this.GreedySolution(source, customers, CostMatrix);

        ArrayList<Node> rt;
        double bestNCost,neighborCost;

        int swapIndexA = -1, swapIndexB = -1, SwapRoute =-1;

        int iteration_number= 0;

        boolean termination = false;

        while (!termination)
        {
            iteration_number++;
            bestNCost = Double.MAX_VALUE;

            for (int vehicleIndex = 0; vehicleIndex < this.Vehicles.length; vehicleIndex++) {
                rt = this.Vehicles[vehicleIndex].getRoute();
                int routeLength = rt.size();

                for (int i = 1; i < routeLength - 1; i++) {

                    for (int j =  0 ; (j < routeLength-1); j++) {

                        if ( ( j != i ) && (j != i-1) ) {

                            double MinusCost1 = CostMatrix[rt.get(i-1).getNodeId()][rt.get(i).getNodeId()];
                            double MinusCost2 =  CostMatrix[rt.get(i).getNodeId()][rt.get(i+1).getNodeId()];
                            double MinusCost3 =  CostMatrix[rt.get(j).getNodeId()][rt.get(j+1).getNodeId()];

                            double AddedCost1 = CostMatrix[rt.get(i-1).getNodeId()][rt.get(i+1).getNodeId()];
                            double AddedCost2 = CostMatrix[rt.get(j).getNodeId()][rt.get(i).getNodeId()];
                            double AddedCost3 = CostMatrix[rt.get(i).getNodeId()][rt.get(j+1).getNodeId()];

                            neighborCost = AddedCost1 + AddedCost2 + AddedCost3
                                    - MinusCost1 - MinusCost2 - MinusCost3;

                            if (neighborCost < bestNCost) {
                                bestNCost = neighborCost;
                                swapIndexA  = i;
                                swapIndexB  = j;
                                SwapRoute = vehicleIndex;

                            }
                        }
                    }
                }
            }

            if (bestNCost < 0) {

                rt = this.Vehicles[SwapRoute].getRoute();

                Node swapNode = rt.get(swapIndexA);

                rt.remove(swapIndexA);

                if (swapIndexA < swapIndexB)
                { rt.add(swapIndexB, swapNode); }
                else
                { rt.add(swapIndexB+1, swapNode); }

                this.Cost  += bestNCost;
            }
            else{
                termination = true;
            }

            if (iteration_number == MAX_ITERATIONS)
            {
                termination = true;
            }
        }
        return new findRouteResponse(Vehicles, Cost);
    }

    public findRouteResponse InterRouteLocalSearch(Node source, List<Node> customers, double[][] CostMatrix, int MAX_ITERATIONS) throws Exception {

        this.GreedySolution(source, customers, CostMatrix);

        ArrayList<Node> routeFrom;
        ArrayList<Node> routeTo;

        int movingNodeDemand = 0;

        int vehicleIndexFrom, vehicleIndexTo;
        double bestNCost, neighborCost;

        int swapIndexA = -1, swapIndexB = -1, swapRouteFrom = -1, swapRouteTo = -1;

        int iteration_number = 0;

        boolean termination = false;

        while (!termination) {
            iteration_number++;
            bestNCost = Double.MAX_VALUE;

            for (vehicleIndexFrom = 0; vehicleIndexFrom < this.Vehicles.length; vehicleIndexFrom++) {
                routeFrom = this.Vehicles[vehicleIndexFrom].getRoute();
                int RouteFromLength = routeFrom.size();
                for (int i = 1; i < RouteFromLength - 1; i++) {

                    for (vehicleIndexTo = 0; vehicleIndexTo < this.Vehicles.length; vehicleIndexTo++) {
                        routeTo = this.Vehicles[vehicleIndexTo].getRoute();
                        int routeToLength = routeTo.size();
                        for (int j = 0; (j < routeToLength - 1); j++) {

                            movingNodeDemand = routeFrom.get(i).getDemand();
                            if ((vehicleIndexFrom == vehicleIndexTo) || this.Vehicles[vehicleIndexTo].isAllowed(movingNodeDemand)) {
                                if (!((vehicleIndexFrom == vehicleIndexTo) && ((j == i) || (j == i - 1))))
                                {
                                    double MinusCost1 = CostMatrix[routeFrom.get(i - 1).getNodeId()][routeFrom.get(i).getNodeId()];
                                    double MinusCost2 = CostMatrix[routeFrom.get(i).getNodeId()][routeFrom.get(i + 1).getNodeId()];
                                    double MinusCost3 = CostMatrix[routeTo.get(j).getNodeId()][routeTo.get(j + 1).getNodeId()];

                                    double AddedCost1 = CostMatrix[routeFrom.get(i - 1).getNodeId()][routeFrom.get(i + 1).getNodeId()];
                                    double AddedCost2 = CostMatrix[routeTo.get(j).getNodeId()][routeFrom.get(i).getNodeId()];
                                    double AddedCost3 = CostMatrix[routeFrom.get(i).getNodeId()][routeTo.get(j + 1).getNodeId()];

                                    neighborCost = AddedCost1 + AddedCost2 + AddedCost3
                                            - MinusCost1 - MinusCost2 - MinusCost3;

                                    if (neighborCost < bestNCost) {
                                        bestNCost = neighborCost;
                                        swapIndexA = i;
                                        swapIndexB = j;
                                        swapRouteFrom = vehicleIndexFrom;
                                        swapRouteTo = vehicleIndexTo;

                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (bestNCost < 0) {

                routeFrom = this.Vehicles[swapRouteFrom].getRoute();
                routeTo = this.Vehicles[swapRouteTo].getRoute();
                this.Vehicles[swapRouteFrom].setRoute(null);
                this.Vehicles[swapRouteTo].setRoute(null);

                Node swapNode = routeFrom.get(swapIndexA);

                routeFrom.remove(swapIndexA);

                if (swapRouteFrom == swapRouteTo) {
                    if (swapIndexA < swapIndexB) {
                        routeTo.add(swapIndexB, swapNode);
                    } else {
                        routeTo.add(swapIndexB + 1, swapNode);
                    }
                } else {
                    routeTo.add(swapIndexB + 1, swapNode);
                }

                this.Vehicles[swapRouteFrom].setRoute(routeFrom);
                this.Vehicles[swapRouteFrom].setLoad(this.Vehicles[swapRouteFrom].getLoad() - movingNodeDemand);

                this.Vehicles[swapRouteTo].setRoute(routeTo);
                this.Vehicles[swapRouteTo].setLoad(this.Vehicles[swapRouteTo].getLoad() + movingNodeDemand);

                this.Cost += bestNCost;
            } else {
                termination = true;
            }

            if (iteration_number == MAX_ITERATIONS) {
                termination = true;
            }
        }

        return new findRouteResponse(Vehicles, Cost);
    }


    public findRouteResponse TabuSearch(Node source, List<Node> customers, double[][] CostMatrix, int MAX_ITERATIONS, int TABU_Horizon) throws Exception {

        this.GreedySolution(source, customers, CostMatrix);

        ArrayList<Node> routeFrom;
        ArrayList<Node> routeTo;

        int movingNodeDemand = 0;

        int vehicleIndexFrom,vehicleIndexTo;
        double bestNCost,neighborCost;

        int swapIndexA = -1, swapIndexB = -1, swapRouteFrom =-1, swapRouteTo=-1;

        int iteration_number= 0;

        int DimensionCustomer = CostMatrix[1].length;
        int[][] TABU_Matrix = new int[DimensionCustomer+1][DimensionCustomer+1];

        bestSolutionCost = this.Cost;

        boolean termination = false;

        while (!termination)
        {
            iteration_number++;
            bestNCost = Double.MAX_VALUE;

            for (vehicleIndexFrom = 0;  vehicleIndexFrom <  this.Vehicles.length;  vehicleIndexFrom++) {
                routeFrom =  this.Vehicles[vehicleIndexFrom].getRoute();
                int RouteFromLength = routeFrom.size();
                for (int i = 1; i < RouteFromLength - 1; i++) {

                    for (vehicleIndexTo = 0; vehicleIndexTo <  this.Vehicles.length; vehicleIndexTo++) {
                        routeTo =   this.Vehicles[vehicleIndexTo].getRoute();
                        int routeToLength = routeTo.size();
                        for (int j = 0; (j < routeToLength - 1); j++) {

                            movingNodeDemand = routeFrom.get(i).getDemand();

                            if ((vehicleIndexFrom == vehicleIndexTo) ||  this.Vehicles[vehicleIndexTo].isAllowed(movingNodeDemand)) {


                                if (!((vehicleIndexFrom == vehicleIndexTo) && ((j == i) || (j == i - 1))))
                                {
                                    double MinusCost1 = CostMatrix[routeFrom.get(i - 1).getNodeId()][routeFrom.get(i).getNodeId()];
                                    double MinusCost2 = CostMatrix[routeFrom.get(i).getNodeId()][routeFrom.get(i + 1).getNodeId()];
                                    double MinusCost3 = CostMatrix[routeTo.get(j).getNodeId()][routeTo.get(j + 1).getNodeId()];

                                    double AddedCost1 = CostMatrix[routeFrom.get(i - 1).getNodeId()][routeFrom.get(i + 1).getNodeId()];
                                    double AddedCost2 = CostMatrix[routeTo.get(j).getNodeId()][routeFrom.get(i).getNodeId()];
                                    double AddedCost3 = CostMatrix[routeFrom.get(i).getNodeId()][routeTo.get(j + 1).getNodeId()];


                                    if ((TABU_Matrix[routeFrom.get(i - 1).getNodeId()][routeFrom.get(i+1).getNodeId()] != 0)
                                            || (TABU_Matrix[routeTo.get(j).getNodeId()][routeFrom.get(i).getNodeId()] != 0)
                                            || (TABU_Matrix[routeFrom.get(i).getNodeId()][routeTo.get(j+1).getNodeId()] != 0)) {
                                        break;
                                    }

                                    neighborCost = AddedCost1 + AddedCost2 + AddedCost3
                                            - MinusCost1 - MinusCost2 - MinusCost3;

                                    if (neighborCost < bestNCost) {
                                        bestNCost = neighborCost;
                                        swapIndexA = i;
                                        swapIndexB = j;
                                        swapRouteFrom = vehicleIndexFrom;
                                        swapRouteTo = vehicleIndexTo;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (int o = 0; o < TABU_Matrix[0].length;  o++) {
                for (int p = 0; p < TABU_Matrix[0].length ; p++) {
                    if (TABU_Matrix[o][p] > 0)
                    { TABU_Matrix[o][p]--; }
                }
            }

            routeFrom =  this.Vehicles[swapRouteFrom].getRoute();
            routeTo =  this.Vehicles[swapRouteTo].getRoute();
            this.Vehicles[swapRouteFrom].setRoute(null);
            this.Vehicles[swapRouteTo].setRoute(null);

            Node swapNode = routeFrom.get(swapIndexA);

            int NodeIDBefore = routeFrom.get(swapIndexA-1).getNodeId();
            int NodeIDAfter = routeFrom.get(swapIndexA+1).getNodeId();
            int NodeID_F = routeTo.get(swapIndexB).getNodeId();
            int NodeID_G = routeTo.get(swapIndexB+1).getNodeId();

            Random TabuRan = new Random();
            int randomDelay1 = TabuRan.nextInt(5);
            int randomDelay2 = TabuRan.nextInt(5);
            int randomDelay3 = TabuRan.nextInt(5);

            TABU_Matrix[NodeIDBefore][swapNode.getNodeId()] = TABU_Horizon + randomDelay1;
            TABU_Matrix[swapNode.getNodeId()][NodeIDAfter]  = TABU_Horizon + randomDelay2 ;
            TABU_Matrix[NodeID_F][NodeID_G] = TABU_Horizon + randomDelay3;

            routeFrom.remove(swapIndexA);

            if (swapRouteFrom == swapRouteTo) {
                if (swapIndexA < swapIndexB) {
                    routeTo.add(swapIndexB, swapNode);
                } else {
                    routeTo.add(swapIndexB + 1, swapNode);
                }
            }
            else
            {
                routeTo.add(swapIndexB+1, swapNode);
            }


            this.Vehicles[swapRouteFrom].setRoute(routeFrom);
            this.Vehicles[swapRouteFrom].setLoad(this.Vehicles[swapRouteFrom].getLoad() - movingNodeDemand);

            this.Vehicles[swapRouteTo].setRoute(routeTo);
            this.Vehicles[swapRouteTo].setLoad(this.Vehicles[swapRouteTo].getLoad() + movingNodeDemand);

            this.Cost  += bestNCost;

            if (this.Cost <   bestSolutionCost)
            {
                SaveBestSolution();
            }

            if (iteration_number == MAX_ITERATIONS)
            {
                termination = true;
            }
        }

        this.Vehicles = bestSolutionVehicles;
        this.Cost = bestSolutionCost;

        return new findRouteResponse(Vehicles, Cost);
    }

    public void SaveBestSolution()
    {
        bestSolutionCost = Cost;
        for (int j=0 ; j < NoOfVehicles ; j++)
        {
            bestSolutionVehicles[j].getRoute().clear();
            if (! Vehicles[j].getRoute().isEmpty())
            {
                int RoutSize = Vehicles[j].getRoute().size();
                for (int k = 0; k < RoutSize ; k++) {
                    Node n = Vehicles[j].getRoute().get(k);
                    bestSolutionVehicles[j].addNodeToRoute(n);
                }
            }
        }
    }

}
