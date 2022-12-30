package net.jdrosen.advent23;

public class BoardView {
    

    public void printBoard(Board b) {

        int width, height;

        width = b.maxX - b.minX + 1;
        height = b.maxY - b.minY+ 1;

        char printBoard[][] = new char[width][height];

        // Initialize board with . characters everywhere

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                printBoard[x][y] = '.';
            }
        }

        // Everywhere there is an elf, change to a #
        for(Elf e : b.elves) {

            int xp = e.curPosition.xpos - b.minX;
            int yp = e.curPosition.ypos - b.minY;

            printBoard[xp][yp] = '#';

        }

        // Now finally output

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                System.out.print(printBoard[x][y]);
            }
            System.out.print('\n');
        }


        System.out.println();

    }

}
