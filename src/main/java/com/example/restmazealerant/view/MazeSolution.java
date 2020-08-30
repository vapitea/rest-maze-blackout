package com.example.restmazealerant.view;

import com.example.restmazealerant.model.Cell;
import lombok.Getter;

import java.util.Deque;

@Getter
public class MazeSolution {

    private final String message;
    private CellView[][] cells;


    public MazeSolution(String message, Cell[][] finalMazeState, Deque<Cell> path) {
        this.message = message;
        createCellViews(finalMazeState);
        addPathToViews(path);
    }

    private void addPathToViews(Deque<Cell> path) {
        path.forEach(cell -> cells[cell.getX()][cell.getY()].setPartOfThePath(true));
    }

    private void createCellViews(Cell[][] finalMazeState) {
        cells = new CellView[finalMazeState.length][finalMazeState[0].length];

        for (int i = 0; i < finalMazeState.length; i++) {
            for (int j = 0; j < finalMazeState[0].length; j++) {
                cells[i][j] = from(finalMazeState[i][j]);
            }
        }
    }

    private static CellView from(Cell cell) {
        CellView cellView = new CellView();
        cellView.setHasNorthernBorder(cell.getRouteToNeighbours()[0] == Cell.RouteType.BLOCKED);
        cellView.setHasEasternBorder(cell.getRouteToNeighbours()[1] == Cell.RouteType.BLOCKED);
        cellView.setHasSouthernBorder(cell.getRouteToNeighbours()[2] == Cell.RouteType.BLOCKED);
        cellView.setHasWesternBorder(cell.getRouteToNeighbours()[3] == Cell.RouteType.BLOCKED);
        cellView.setVisited(cell.isVisited() || cell.isPreviouslyVisited());
        cellView.setAtEnd(cell.isAtEnd());
        cellView.setAtBeginning(cell.getX() == 0 && cell.getY() == 0);
        return cellView;
    }

}
