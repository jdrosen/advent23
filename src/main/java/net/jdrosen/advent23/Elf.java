package net.jdrosen.advent23;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public void propose(ArrayList<Elf> otherElves, LinkedList<Direction> directions) {

        //The rules of the game say, if no one else is around me, do nothing.
        //I'm going to model this, as a proposal to move to the same spot. 
        //This means my proposal is my current position
        //THis would not work if it was possible for another elf to propose to 
        //move to this spot. But, its not possible, since an elf never moves
        //in a direction where there is already another elf.

        System.out.println("Considering elf: " + this);

        if(anyoneElseAroundMe(otherElves) == false) {
            proposal = curPosition;
            System.out.println("  No else around me!. Not moving.");
            return;
        }

        // We get here if there were elves around me. So, we look in the directions
        // in order, and get the proposal

        System.out.println("  Someone else around me.");
        proposal = null;
        for(Direction dir : directions) {

            System.out.println("  Considering going " + dir);

            

            boolean goThisWway = true;
            for(Elf e: otherElves) {

                if(dir.isElfInThisDirection(this,e) == true) goThisWway = false;

            }



            if(goThisWway == true) {
                System.out.println(" Decided to go " + dir);
                proposal = dir.proposedMovementInThisDirection(this);
                return;
            } 

        }

        if(proposal == null) {
            // This means that, there were elves in all directions. I believe in this
            // case the elf stays in the same place. So, proposal is current location.

            proposal = curPosition;
        }


    }


    public void reviseProposal(ArrayList<Elf> otherElfs) {

        ArrayList<Elf> competingElves = new ArrayList<Elf>();

        for(Elf e : otherElfs) {

            // The rules say - if two elves are moving to the same spot,
            // then neither moves.
            // Note that we are modifying the proposals in place, which means
            // other elves will compare their proposals to what is actually
            // the current position of the elf. I think this is OK. Its
            // OK because, the rules prohibit an elf from proposing to move
            // in the direction where a current elf already is. 

            if(this.proposal.equals(e.proposal)) competingElves.add(e);

        }

        // The rules say, for all the elves moving to this position, they all
        // dont move. 

        if(!competingElves.isEmpty()) {

            for(Elf e : competingElves) e.proposal = e.curPosition;
            this.proposal = this.curPosition;

        }

    }

    // Executing the proposal, just means making it the current position.

    public void executeProposal() {
        this.curPosition = this.proposal;

    }
    
}
