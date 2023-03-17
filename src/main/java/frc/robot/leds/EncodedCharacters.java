package frc.robot.leds;

public class EncodedCharacters {
    
    private final int WIDTH = 5;
    private final int HEIGHT = 8;
    private final int NUM_OF_CHARS = 26;

    private boolean[][][] characters = new boolean[NUM_OF_CHARS][WIDTH][HEIGHT];
    public boolean[] SPACE = {false, false, false, false, false, false, false, false}; // Empty spaces!


    public EncodedCharacters() {
        init();
    }


    public void init() {
        characters = new boolean[][][]{
            encode(new char[][]{
        {' ','@','@','@','@','@','@',' '},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {' ','@','@','@','@','@','@',' '}}),
        encode(new char[][]{
        {' ',' ',' ',' ',' ',' ',' ',' '},
        {' ','@',' ',' ',' ',' ',' ','@'},
        {'@','@','@','@','@','@','@','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {' ',' ',' ',' ',' ',' ',' ',' '}}),
        encode(new char[][]{
        {'@','@',' ',' ','@','@','@','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@','@','@','@','@',' ','@','@'}}),
        encode(new char[][]{
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@',' ',' ',' '},
        {' ',' ',' ',' ','@',' ',' ',' '},
        {' ',' ',' ',' ','@',' ',' ',' '},
        {' ',' ',' ',' ','@',' ',' ',' '},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ',' ','@','@',' '}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@','@','@','@'}}),
        encode(new char[][]{
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@',' ',' ',' ',' '},
        {'@',' ',' ','@',' ',' ',' ',' '},
        {'@',' ',' ','@',' ',' ',' ',' '},
        {'@',' ',' ','@',' ',' ',' ',' '},
        {'@','@','@','@','@','@','@','@'}}),


        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ','@',' ',' ',' ',' '},
        {'@',' ',' ','@',' ',' ',' ',' '},
        {'@',' ',' ','@',' ',' ',' ',' '},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {' ','@','@','@','@','@','@',' '}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {' ','@','@','@','@','@','@',' '}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ','@',' ',' ',' '},
        {'@',' ',' ',' ','@',' ',' ',' '},
        {'@',' ',' ',' ','@',' ',' ',' '},
        {'@',' ',' ',' ','@',' ',' ',' '}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@',' ',' ',' ','@',' ',' ',' '},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {' ',' ',' ',' ','@',' ',' ',' '},
        {' ',' ',' ',' ','@',' ',' ',' '},
        {' ',' ',' ',' ','@',' ',' ',' '},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'}}),
        encode(new char[][]{
        {'@',' ',' ',' ',' ',' ','@','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@',' ',' ',' ',' ',' ',' ',' '}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {' ',' ',' ',' ','@',' ',' ',' '},
        {' ',' ',' ',' ','@','@',' ',' '},
        {' ',' ','@','@',' ',' ','@',' '},
        {'@','@',' ',' ',' ',' ',' ','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@','@','@','@',' ',' ',' ',' '},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {' ','@',' ',' ',' ',' ',' ',' '},
        {' ',' ','@',' ',' ',' ',' ',' '},
        {' ',' ',' ','@',' ',' ',' ',' '},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@',' ',' ',' ',' ',' ',' ','@'},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ','@',' ',' ',' '},
        {'@',' ',' ',' ','@',' ',' ',' '},
        {'@',' ',' ',' ','@',' ',' ',' '},
        {'@','@','@','@','@',' ',' ',' '}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@',' '},
        {'@',' ',' ',' ',' ',' ','@',' '},
        {'@',' ',' ',' ',' ',' ','@',' '},
        {'@',' ',' ',' ',' ',' ','@',' '},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ','@',' ',' ',' '},
        {'@',' ',' ',' ','@','@',' ',' '},
        {'@',' ',' ',' ','@',' ','@',' '},
        {'@','@','@','@',' ',' ',' ','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@',' ',' ','@'},
        {'@',' ',' ',' ','@','@','@','@'}}),
        encode(new char[][]{
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@','@','@','@','@','@','@','@'},
        {'@',' ',' ',' ',' ',' ',' ',' '},
        {'@',' ',' ',' ',' ',' ',' ',' '}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@','@','@','@',' ',' '},
        {' ',' ',' ',' ',' ',' ','@',' '},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {' ',' ',' ',' ',' ',' ','@',' '},
        {'@','@','@','@','@','@',' ',' '}}),
        encode(new char[][]{
        {'@','@','@','@','@','@','@','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {' ',' ',' ',' ','@','@','@','@'},
        {' ',' ',' ',' ',' ',' ',' ','@'},
        {'@','@','@','@','@','@','@','@'}}),
        encode(new char[][]{
        {'@','@','@',' ',' ',' ','@','@'},
        {' ',' ',' ','@',' ','@',' ',' '},
        {' ',' ',' ',' ','@',' ',' ',' '},
        {' ',' ',' ','@',' ','@',' ',' '},
        {'@','@','@',' ',' ',' ','@','@'}}),
        encode(new char[][]{
        {'@','@','@',' ',' ',' ',' ',' '},
        {' ',' ',' ','@',' ',' ',' ',' '},
        {' ',' ',' ',' ','@','@','@','@'},
        {' ',' ',' ','@',' ',' ',' ',' '},
        {'@','@','@',' ',' ',' ',' ',' '}}),
        encode(new char[][]{
        {'@',' ',' ',' ',' ',' ','@','@'},
        {'@',' ',' ',' ',' ','@',' ','@'},
        {'@',' ',' ','@','@',' ',' ','@'},
        {'@',' ','@',' ',' ',' ',' ','@'},
        {'@','@',' ',' ',' ',' ',' ','@'}}),

        encode(new char[][]{
        {' ',' ','@',' ',' ','@',' ',' '},
        {' ',' ','@',' ',' ','@',' ',' '},
        {' ',' ','@',' ',' ','@',' ',' '},
        {' ',' ','@',' ',' ','@',' ',' '},
        {' ',' ','@',' ',' ','@',' ',' '}})
        };
    }

    private boolean[][] encode(char[][] input) {
        boolean[][] toBuild = new boolean[WIDTH][HEIGHT];
        for (int y = 0; y < HEIGHT; y++) {
            //String s = "";
            for (int x = 0; x < WIDTH; x++) {
                if (input[WIDTH-1-x][y] == '@')
                    toBuild[x][y] = true;
                else
                    toBuild[x][y] = false;
                //s += input[x][y];
            }
            //System.out.println(s);
        }
        //System.out.println();
        return toBuild;
    }

    public boolean[][] getLetter(char character) {
        int ascii = (int) character;
        if (ascii >= 48 && ascii <= 57) {   // Numbers between 0 and 9 (ascii values)
            return characters[ascii-48];
        }
        if (ascii >= 65 && ascii <= 90) {   // Uppercase letters between 'A' and 'Z' (ascii values)
            return characters[ascii-55];
        }
        if (ascii >= 97 && ascii <= 122) {  // Lowercase letters between 'a' and 'z' (ascii values) WARNING DOES NOT ACTUALLY DISPLAY LOWERCASE
            return characters[ascii-87];
        }
        if (ascii == 61) {
            return characters[36];
        }
        
        return new boolean[WIDTH][HEIGHT];    // Couldn't find character
    }
}