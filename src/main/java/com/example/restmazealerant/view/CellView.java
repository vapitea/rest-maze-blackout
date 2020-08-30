package com.example.restmazealerant.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellView {
    private boolean hasNorthernBorder;
    private boolean hasEasternBorder;
    private boolean hasSouthernBorder;
    private boolean hasWesternBorder;
    private boolean visited;
    private boolean atEnd;
    private boolean atBeginning;
    private boolean partOfThePath;


    public String getCellText() {
        if (atBeginning) {
            return "O";
        }
        if (atEnd) {
            return "X";
        }
        return "";
    }

    public String getCssClasses() {
        StringBuilder CssClasses = new StringBuilder();

        if (hasNorthernBorder) {
            CssClasses.append(" blocked-from-north");
        }
        if (hasEasternBorder) {
            CssClasses.append(" blocked-from-east");
        }
        if (hasSouthernBorder) {
            CssClasses.append(" blocked-from-south");
        }
        if (hasWesternBorder) {
            CssClasses.append(" blocked-from-west");
        }
        if (visited) {
            CssClasses.append(" visited");
        }
        if (atEnd) {
            CssClasses.append(" at-end");
        }
        if (partOfThePath) {
            CssClasses.append(" part-of-the-path");
        }
        return CssClasses.toString();
    }
}
