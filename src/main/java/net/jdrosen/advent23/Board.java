package net.jdrosen.advent23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Board {

    ArrayList<Elf> elves;
    LinkedList<Direction> directions;
    int round;

    int minX, minY, maxX, maxY;

    public static ArrayList<String> parseFile(String filename) {

        ArrayList<String> result = new ArrayList<String>();

        try {
            File myFile = new File(filename);
            Scanner myReader = new Scanner(myFile);
            while(myReader.hasNextLine()) {
                String nextln = myReader.nextLine();
                result.add(nextln);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not read file");
        }

        return(result);


    }


    

    /** 
     * Iniitalize the board. Will create the elves.
     */
    
    public Board(ArrayList<String> initialBoard) {

        this.elves = new ArrayList<Elf>();

        /* Iterate through each line. Use ypos as a counter for the line number. We're basically going
         * to number so that the top left of the input is (0,0). Within each line, find the elves - which
         * are marked with a hash. Its position is the index of the string where the # sign is located.
         */
        int ypos = 0;


        for(String line : initialBoard) {

            for(int i = 0; i < line.length(); i++) {
                if(line.charAt(i) == '#') {
                    elves.add(new Elf(i,ypos));
                }
            }
            ypos++;

        }



        // This will set the min amd max values

        setDimensions();

        // Initiaitze directions. The instructions say we start with north, then south, 
        // then west, then east. After each round, we take the first direction and move it
        // to the end.

        this.directions = new LinkedList<Direction>();
        directions.addLast(new NorthDirection());
        directions.addLast(new SouthDirection());
        directions.addLast(new WestDirection());
        directions.addLast(new EastDirection());

        // Start at round 0

        round = 0;

    }

    // At the end of each round, we are supposed to rotate the directions.

    public void rotateDirections() {

        Direction temp;

        temp = directions.removeFirst();
        directions.addLast(temp);


    }


    // Perform a round. This will gather proposals, identify conflicts,
    // and then update positions

    public void executeRound() {

        round++;

        // Gather proposals

        for(Elf e: elves) {

            // We need the list of OTHER elves. So, we create a shallow copy
            // and remove myself.
            // NOTE this is really inefficient, we recreate this shallow copy on every iteration

            ArrayList<Elf> otherElfs = new ArrayList<Elf>(elves);
            otherElfs.remove(e);


            e.propose(otherElfs, directions);
        }

        // Update proposals. Again, inefficient shallow copies.

        for(Elf e: elves) {


            ArrayList<Elf> otherElfs = new ArrayList<Elf>(elves);
            otherElfs.remove(e);


            e.reviseProposal(otherElfs);
        }


        // Execute the updated proposals

        for(Elf e: elves) e.executeProposal();

        // Update the dimensions of the board

        setDimensions();

        // Rotate the directions for the next round

        rotateDirections();

    }

    // Update dimensions of thhe board, by taking the min and max of the X and Y positions
    // across all elves

    private void setDimensions() {

        Elf firstElf = elves.get(0);
        maxX = minX = firstElf.curPosition.xpos;
        maxY = minY = firstElf.curPosition.ypos;

        for(Elf e: elves) {

            if(e.curPosition.xpos > maxX) maxX = e.curPosition.xpos;
            if(e.curPosition.ypos > maxY) maxY = e.curPosition.ypos;
            if(e.curPosition.xpos < minX) minX = e.curPosition.xpos;
            if(e.curPosition.ypos < minY) minY = e.curPosition.ypos;
        }
    }

    public int numUnoccupiedSpaces() {

        int numtiles, numelves;

        numelves = elves.size();
        numtiles = (maxX - minX + 1) * (maxY - minY + 1);

        return(numtiles - numelves);

    }

    public static void main(String[] args) {

        ArrayList<String> boardInput = parseFile("initboard.txt");

        Board board = new Board(boardInput);
        BoardView viewer = new BoardView();
        viewer.printBoard(board);

        int round = 0;
        while(round < 10) {
            board.executeRound();
            viewer.printBoard(board);
            System.out.println("Number Unoccupied: " + board.numUnoccupiedSpaces());

            round++;
        }

    }

}
