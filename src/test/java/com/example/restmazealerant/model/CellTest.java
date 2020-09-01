package com.example.restmazealerant.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CellTest {

    Cell neighbour;
    Cell cell;

    @BeforeEach
    void setUp() {
        neighbour = new Cell();
        cell = new Cell();
    }


    @Test
    void registerSouthernNeighbour() {
        //Arrange
        neighbour.setRouteToNeighbours(new Cell.RouteType[]{Cell.RouteType.UNEXPLORED, null, null, null});

        //Act
        cell.registerSouthernNeighbour(neighbour);

        //Assert
        assertEquals(Cell.RouteType.VISITED, cell.getRouteToNeighbours()[2]);
    }

    @Test
    void registerNorthernNeighbour() {
        //Arrange
        neighbour.setRouteToNeighbours(new Cell.RouteType[]{null, null, Cell.RouteType.VISITED, null});

        //Act
        cell.registerNorthernNeighbour(neighbour);


        //Assert
        assertEquals(Cell.RouteType.VISITED, cell.getRouteToNeighbours()[0]);
    }

    @Test
    void registerEasternNeighbour() {
        //Arrange
        neighbour.setRouteToNeighbours(new Cell.RouteType[]{null, null, null, Cell.RouteType.UNEXPLORED});

        //Act
        cell.registerEasternNeighbour(neighbour);


        //Assert
        assertEquals(Cell.RouteType.VISITED, cell.getRouteToNeighbours()[1]);
    }

    @Test
    void registerWesternNeighbour() {
        //Arrange
        neighbour.setRouteToNeighbours(new Cell.RouteType[]{null, Cell.RouteType.VISITED, null, null});

        //Act
        cell.registerWesternNeighbour(neighbour);


        //Assert
        assertEquals(Cell.RouteType.VISITED, cell.getRouteToNeighbours()[3]);
    }

    @Test
    void registerBlockedSouthernNeighbour() {
        //Arrange
        neighbour.setRouteToNeighbours(new Cell.RouteType[]{Cell.RouteType.BLOCKED, null, null, null});

        //Act
        cell.registerSouthernNeighbour(neighbour);

        //Assert
        assertEquals(Cell.RouteType.BLOCKED, cell.getRouteToNeighbours()[2]);
    }

    @Test
    void registerBlockedNorthernNeighbour() {
        //Arrange
        neighbour.setRouteToNeighbours(new Cell.RouteType[]{null, null, Cell.RouteType.BLOCKED, null});

        //Act
        cell.registerNorthernNeighbour(neighbour);


        //Assert
        assertEquals(Cell.RouteType.BLOCKED, cell.getRouteToNeighbours()[0]);
    }

    @Test
    void registerBlockedEasternNeighbour() {
        //Arrange
        neighbour.setRouteToNeighbours(new Cell.RouteType[]{null, null, null, Cell.RouteType.BLOCKED});

        //Act
        cell.registerEasternNeighbour(neighbour);


        //Assert
        assertEquals(Cell.RouteType.BLOCKED, cell.getRouteToNeighbours()[1]);
    }

    @Test
    void registerBlockedWesternNeighbour() {
        //Arrange
        neighbour.setRouteToNeighbours(new Cell.RouteType[]{null, Cell.RouteType.VISITED, null, null});

        //Act
        cell.registerWesternNeighbour(neighbour);


        //Assert
        assertEquals(Cell.RouteType.VISITED, cell.getRouteToNeighbours()[3]);
    }
}