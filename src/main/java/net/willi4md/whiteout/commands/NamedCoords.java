package net.willi4md.whiteout.commands;

public class NamedCoords {
    private String name;
    private Coords coords;

    public NamedCoords(String name, Coords coords) {
        this.name = name;
        this.coords = coords;
    }

    public String getName() {
        return name;
    }

    public Coords getCoords() {
        return coords;
    }
}
