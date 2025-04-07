package com.imyifeng.poetrylearn.poetrylearn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Poem {
    private String title;
    private String dynasty;
    private String author;
    private List<Line> lines;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public static class Line {
        @JsonProperty("line_number")
        private int lineNumber;
        
        @JsonProperty("first_part") 
        private String firstPart;
        
        @JsonProperty("second_part")
        private String secondPart;

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getFirstPart() {
            return firstPart;
        }

        public void setFirstPart(String firstPart) {
            this.firstPart = firstPart;
        }

        public String getSecondPart() {
            return secondPart;
        }

        public void setSecondPart(String secondPart) {
            this.secondPart = secondPart;
        }
    }
}