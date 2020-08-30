package com.example.restmazealerant.model;

import lombok.Data;

import java.util.Arrays;

@Data
public class Cell {
    private int x, y;
    private boolean previouslyVisited;
    private RouteType[] routeToNeighbours = new RouteType[4]; //Important: directions in clockwise order: north, east, south, west
    private boolean atEnd;
    private boolean visited;

    public int countUnexploredNeighbours() {
        return (int) Arrays.stream(routeToNeighbours).filter(route -> route == RouteType.UNEXPLORED).count();
    }

    public void registerSouthernNeighbour(Cell neighbour) {
        if (neighbour.getRouteToNeighbours()[0] == RouteType.BLOCKED) {
            routeToNeighbours[2] = RouteType.BLOCKED;
        } else {
            routeToNeighbours[2] = RouteType.VISITED;
        }
    }

    public void registerNorthernNeighbour(Cell neighbour) {
        if (neighbour.getRouteToNeighbours()[2] == RouteType.BLOCKED) {
            routeToNeighbours[0] = RouteType.BLOCKED;
        } else {
            routeToNeighbours[0] = RouteType.VISITED;
        }
    }

    public void registerEasternNeighbour(Cell neighbour) {
        if (neighbour.getRouteToNeighbours()[3] == RouteType.BLOCKED) {
            routeToNeighbours[1] = RouteType.BLOCKED;
        } else {
            routeToNeighbours[1] = RouteType.VISITED;
        }
    }

    public void registerWesternNeighbour(Cell neighbour) {
        if (neighbour.getRouteToNeighbours()[1] == RouteType.BLOCKED) {
            routeToNeighbours[3] = RouteType.BLOCKED;
        } else {
            routeToNeighbours[3] = RouteType.VISITED;
        }
    }

    public enum Direction {
        NORTH,  //0
        EAST,   //1
        SOUTH,  //2
        WEST    //3
    }

    public enum RouteType {
        BLOCKED,
        VISITED,
        UNEXPLORED
    }
}
