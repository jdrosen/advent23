package net.jdrosen.advent23;

public class ElfPosition {

    int xpos;
    int ypos;

    public ElfPosition(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
    }
    
    public void goNorth() {
        ypos = ypos - 1;
    }

    public void goSouth() {
        ypos = ypos + 1;
    }

    public void goEast() {
        xpos = xpos + 1;
    }

    public void goWest() {
        xpos = xpos - 1;
    }

    public boolean equals(ElfPosition e) {

        return((this.xpos == e.xpos) && (this.ypos == e.ypos));

    }

}
