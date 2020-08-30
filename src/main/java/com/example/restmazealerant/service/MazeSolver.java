package com.example.restmazealerant.service;

import com.example.restmazealerant.model.Cell;
import com.example.restmazealerant.view.MazeSolution;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class MazeSolver {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private final MazeConnection mazeConnection;
    private final Deque<Cell> jumpBackCandidateStack = new ArrayDeque<>();
    private final Deque<Cell> finalPath = new ArrayDeque<>(); //used only for keeping track on the final path
    private Cell[][] maze;
    private Cell currentCell;
    private boolean isSolved;

    public MazeSolver(MazeConnection mazeConnection) {
        this.mazeConnection = mazeConnection;
        initializeCells();
    }

    public MazeSolution startSolving() {
        currentCell = mazeConnection.init();
        while (!isSolved) {
            executeNextStep();
            sleep();
        }

        String finalMessage = mazeConnection.getFinalMessage();
        System.out.println(finalMessage);
        return new MazeSolution(finalMessage, maze, finalPath);
    }

    /* Not strictly necessary to call this method, used only to ease the load on the rest service. */
    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /* Simple iterative DFS */
    private void executeNextStep() {
        stepOnCell();
        printMaze();
        if (checkForEnding()) {
            return;
        }
        if (currentCell.countUnexploredNeighbours() > 1) {
            jumpBackCandidateStack.push(currentCell);
        }
        moveOrJumpBack();
    }

    private boolean checkForEnding() {
        if (currentCell.isAtEnd()) {
            isSolved = true;
            return true;
        }
        return false;
    }

    private void stepOnCell() {
        currentCell.setVisited(true);
        maze[currentCell.getX()][currentCell.getY()] = currentCell;
        registerCellInformationOnNeighbouringCells();
        finalPath.push(currentCell);
    }


    private void registerCellInformationOnNeighbouringCells() {
        registerCellOnNorthernNeighbour();
        registerCellOnEasternNeighbour();
        registerCellOnSouthernNeighbour();
        registerCellOnWesternNeighbour();
    }

    private void registerCellOnNorthernNeighbour() {
        if (!cellIsOnNorthernEdge()) {
            maze[currentCell.getX()][currentCell.getY() - 1].registerSouthernNeighbour(currentCell);
        }
    }

    private void registerCellOnEasternNeighbour() {
        if (!cellIsOnEasternEdge()) {
            maze[currentCell.getX() + 1][currentCell.getY()].registerWesternNeighbour(currentCell);
        }
    }

    private void registerCellOnSouthernNeighbour() {
        if (!cellIsOnSouthernEdge()) {
            maze[currentCell.getX()][currentCell.getY() + 1].registerNorthernNeighbour(currentCell);

        }
    }

    private void registerCellOnWesternNeighbour() {
        if (!cellIsOnWesternEdge()) {
            maze[currentCell.getX() - 1][currentCell.getY()].registerEasternNeighbour(currentCell);
        }
    }

    private boolean cellIsOnWesternEdge() {
        return currentCell.getX() == 0;
    }

    private boolean cellIsOnSouthernEdge() {
        return currentCell.getY() == HEIGHT - 1;
    }

    private boolean cellIsOnEasternEdge() {
        return currentCell.getX() == WIDTH - 1;
    }

    private boolean cellIsOnNorthernEdge() {
        return currentCell.getY() == 0;
    }

    private void moveOrJumpBack() {
        if (currentCell.countUnexploredNeighbours() == 0) {
            jumpBack();
        } else {
            move();
        }
    }

    private void move() {
        currentCell = mazeConnection.move(getNextMoveDirection());
    }

    private void jumpBack() {
        throwAwayDeadEnds();
        Cell cellToJump = jumpBackCandidateStack.pop();
        throwAwayCellsNotInFinalPath(cellToJump);
        currentCell = mazeConnection.jump(cellToJump.getX(), cellToJump.getY());
    }

    private void throwAwayCellsNotInFinalPath(Cell cellToJump) {
        while (!finalPath.pop().equals(cellToJump)) {
        }
    }

    private void throwAwayDeadEnds() {
        while (jumpBackCandidateStack.peekFirst().countUnexploredNeighbours() == 0) {
            jumpBackCandidateStack.removeFirst();
        }
    }


    private void initializeCells() {
        maze = new Cell[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                maze[i][j] = new Cell();
            }
        }
    }

    private Cell.Direction getNextMoveDirection() {
        Cell.RouteType[] routeTypes = currentCell.getRouteToNeighbours();
        for (int i = 0; i < 4; i++) {
            if (routeTypes[i] == Cell.RouteType.UNEXPLORED) {
                return Cell.Direction.values()[i];
            }
        }
        return null;
    }


    private void printMaze() {
        System.out.println(currentCell);
        for (int i = 0; i < HEIGHT; i++) {
            StringBuilder firstLine = new StringBuilder();
            StringBuilder secondLine = new StringBuilder();
            StringBuilder thirdLine = new StringBuilder();

            for (int j = 0; j < WIDTH; j++) {
                Cell.RouteType north = maze[j][i].getRouteToNeighbours()[0];

                firstLine.append(" ");
                generateLine(firstLine, north);
                firstLine.append(" ");

                Cell.RouteType west = maze[j][i].getRouteToNeighbours()[3];
                generateLine(secondLine, west);

                if (maze[j][i].isVisited() || maze[j][i].isPreviouslyVisited()) {
                    secondLine.append("█");
                } else {
                    secondLine.append("░");
                }

                Cell.RouteType east = maze[j][i].getRouteToNeighbours()[1];
                generateLine(secondLine, east);

                thirdLine.append(" ");
                Cell.RouteType south = maze[j][i].getRouteToNeighbours()[2];
                generateLine(thirdLine, south);
                thirdLine.append(" ");

            }
            System.out.println(firstLine.toString());
            System.out.println(secondLine.toString());
            System.out.println(thirdLine.toString());

        }
    }

    private void generateLine(StringBuilder line, Cell.RouteType route) {
        if (route == null) {
            line.append(" ");
        } else if (route == Cell.RouteType.BLOCKED) {
            line.append("B");
        } else if (route == Cell.RouteType.VISITED) {
            line.append("W");
        } else if (route == Cell.RouteType.UNEXPLORED) {
            line.append("U");
        }
    }

}
