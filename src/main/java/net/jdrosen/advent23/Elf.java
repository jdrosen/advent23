package net.jdrosen.advent23;

import java.util.ArrayList;

public class Elf {
    ElfPosition curPosition;
    ElfPosition proposal;

    // Helper function to figure out if two positions are within 1 box of each other
    // Note that this is a symmetric function - f(a,b) = f(b,a)

    public static boolean isOneStepAway(ElfPosition e1, ElfPosition e2) {
        if( ((Math.abs(e1.xpos - e2.xpos)) < 2) &&
            ((Math.abs(e1.ypos - e2.ypos)) < 2)) return true;
        return false;
    }


    public Elf(int xpos, int ypos) {
        this.curPosition = new ElfPosition(xpos, ypos);
    }

    public String toString() {
        return("Elf at x: " + curPosition.xpos + " y: " + curPosition.ypos + "\n");
    }

    public boolean anyoneElseAroundMe(ArrayList<Elf> otherElves) {

        for(Elf e: otherElves) {
            if(isOneStepAway(e.curPosition, this.curPosition)) return true;
        }
        return false;

    }

    public void propose(ArrayList<Elf> otherElves, ArrayList<Direction> directions) {

        //The rules of the game say, if no one else is around me, do nothing.
        //I'm going to model this, as a proposal to move to the same spot. 
        //This means my proposal is my current position
        //THis would not work if it was possible for another elf to propose to 
        //move to this spot. But, its not possible, since an elf never moves
        //in a direction where there is already another elf.

        if(anyoneElseAroundMe(otherElves) == false) {
            proposal = curPosition;
            return;
        }

        // We get here if there were elves around me. So, we look in the directions
        // in order, and get the proposal

        proposal = null;
        for(Direction dir : directions) {

            boolean goThisWway = true;
            for(Elf e: otherElves) {
                if(dir.isElfInThisDirection(this,e) == true) goThisWway = false;
            }

            if(goThisWway == true) {
                proposal = dir.proposedMovementInThisDirection(this);
            } 

        }

        if(proposal == null) {
            // This means that, there were elves in all directions. I believe in this
            // case the elf stays in the same place. So, proposal is current location.

            proposal = curPosition;
        }


    }
    
}
