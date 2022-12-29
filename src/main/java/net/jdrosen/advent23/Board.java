package net.jdrosen.advent23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Board {

    ArrayList<Elf> elves;
    ArrayList<Direction> directions;
    int round;

    public static ArrayList<String> parseFile(String filename) {

        ArrayList<String> result = new ArrayList<String>();

        try {
            File myFile = new File(filename);
            Scanner myReader = new Scanner(myFile);
            while(myReader.hasNextLine()) {
                String nextln = myReader.nextLine();
                System.out.println(nextln);
                result.add(nextln);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not read file");
        }

        return(result);


    }

    /** Using streams to get some experience with it! */

    public String toString() {

        return(elves.stream().
            map(e -> e.toString()).
            collect(Collectors.joining()));
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

        // Initiaitze directions. The instructions say we start with north, then south, 
        // then west, then east. After each round, we take the first direction and move it
        // to the end.

        this.directions = new ArrayList<Direction>();
        directions.add(0, new NorthDirection());
        directions.add(1, new SouthDirection());
        directions.add(2, new WestDirection());
        directions.add(3, new EastDirection());

        // Start at round 0

        round = 0;

    }

    // At the end of each round, we are supposed to rotate the directions.
    // This seems kind of messy - uses fixed array indexes. 

    public void rotateDirections() {

        Direction temp;

        temp = directions.get(0);
        directions.add(0, directions.get(3));
        directions.add(3,temp);

    }


    // Perform a round. This will gather proposals, identify conflicts,
    // and then update positions

    public void executeRound() {

        round++;

        // Gather proposals

        for(Elf e: elves) {

            ArrayList<Elf> otherElfs = new ArrayList<Elf>();


            e.propose(elves, directions);
        }

    }

    public static void main(String[] args) {

        ArrayList<String> boardInput = parseFile("initboard.txt");

        Board board = new Board(boardInput);
        System.out.println(board);


    }

}
