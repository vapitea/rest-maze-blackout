package com.example.restmazealerant.service;

import com.example.restmazealerant.model.Cell;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MazeConnection {
    private static final String BASE_URL = "https://www.epdeveloperchallenge.com";
    private final WebClient webClient = WebClient.create();
    private String guid;

    public Cell init() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/api")
                .path("/init");

        CurrentCellMessage currentCellMessage = webClient.post().uri(builder.toUriString()).retrieve().bodyToMono(CurrentCellMessage.class).block();
        CellDTO cellDTO = currentCellMessage.getCellDTO();
        guid = cellDTO.getMazeGuid();
        Cell startingCell = CellMapper.from(cellDTO);
        return startingCell;
    }

    public Cell move(Cell.Direction nextMoveDirection) {
        WebClient webClient = WebClient.builder().build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/api")
                .path("/move")
                .queryParam("mazeGuid", guid)
                .queryParam("direction", nextMoveDirection);

        CurrentCellMessage currentCellMessage = webClient.post().uri(builder.toUriString()).retrieve().bodyToMono(CurrentCellMessage.class).block();
        return CellMapper.from(currentCellMessage.getCellDTO());
    }

    public Cell jump(int x, int y) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/api")
                .path("/jump")
                .queryParam("mazeGuid", guid)
                .queryParam("x", x)
                .queryParam("y", y);

        CurrentCellMessage currentCellMessage = webClient.post().uri(builder.toUriString()).retrieve().bodyToMono(CurrentCellMessage.class).block();
        return CellMapper.from(currentCellMessage.getCellDTO());
    }

    public String getFinalMessage() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/api")
                .path("/currentCell")
                .queryParam("mazeGuid", guid);
        CurrentCellMessage currentCellMessage = webClient.get().uri(builder.toUriString()).retrieve().bodyToMono(CurrentCellMessage.class).block();
        return currentCellMessage.getCellDTO().getNote();
    }

    @Data
    public static class CurrentCellMessage {
        @JsonProperty("currentCell")
        private CellDTO cellDTO;
    }

    @Data
    public static class CellDTO {
        private String note;
        private String mazeGuid;
        private boolean atEnd;
        private Cell.RouteType north;
        private Cell.RouteType east;
        private Cell.RouteType south;
        private Cell.RouteType west;
        private int x;
        private int y;
        private boolean previouslyVisited;
    }

    public static class CellMapper {

        public static Cell from(CellDTO cellDTO) {
            Cell cell = new Cell();
            cell.setAtEnd(cellDTO.isAtEnd());
            cell.setPreviouslyVisited(cellDTO.isPreviouslyVisited());
            cell.setX(cellDTO.getX());
            cell.setY(cellDTO.getY());

            Cell.RouteType[] routeTypes = new Cell.RouteType[4];
            routeTypes[0] = cellDTO.getNorth();
            routeTypes[1] = cellDTO.getEast();
            routeTypes[2] = cellDTO.getSouth();
            routeTypes[3] = cellDTO.getWest();

            cell.setRouteToNeighbours(routeTypes);

            return cell;
        }
    }
}