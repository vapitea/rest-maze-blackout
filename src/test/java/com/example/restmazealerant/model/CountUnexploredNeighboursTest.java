package com.example.restmazealerant.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountUnexploredNeighboursTest {

    @Test
    void countUnexploredNeighboursZero() {
        //Arrange
        Cell cell = new Cell();
        cell.setRouteToNeighbours(new Cell.RouteType[]{Cell.RouteType.VISITED, Cell.RouteType.BLOCKED, null, null});

        //Act
        int unexploredNeighbours = cell.countUnexploredNeighbours();

        //Assert
        assertEquals(0, unexploredNeighbours);
    }

    @Test
    void countUnexploredNeighboursTwo() {
        //Arrange
        Cell cell = new Cell();
        cell.setRouteToNeighbours(new Cell.RouteType[]{Cell.RouteType.VISITED, Cell.RouteType.UNEXPLORED, null, Cell.RouteType.UNEXPLORED});

        //Act
        int unexploredNeighbours = cell.countUnexploredNeighbours();

        //Assert
        assertEquals(2, unexploredNeighbours);
    }


}