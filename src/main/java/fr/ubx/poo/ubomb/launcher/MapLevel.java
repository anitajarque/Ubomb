package fr.ubx.poo.ubomb.launcher;

import static fr.ubx.poo.ubomb.launcher.Entity.*;
import static fr.ubx.poo.ubomb.launcher.Entity.Empty;

public class MapLevel {
    private final int width;
    private final int height;
    private final Entity[][] grid;
    public MapLevel(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Entity[height][width];
    }
    public MapLevel() { //class MapLevelDefault delete just not to have one class "for nothing" and having this grid we solve the problem of the default map
        grid = new Entity[][]{
                {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty},
                {Empty, Empty, Stone, Empty, Stone, Empty, Stone, Stone, Stone, Stone, Empty, Empty},
                {Empty, Empty, Empty, Empty, Stone, Empty, Stone, Empty, Empty, Stone, Empty, Empty},
                {Empty, Empty, Empty, Empty, Stone, Empty, Stone, Empty, Empty, Stone, Empty, Empty},
                {Empty, Empty, Empty, Empty, Stone, Stone, Stone, Empty, Empty, Empty, Empty, Empty},
                {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Key, Empty, Stone, Empty, Empty},
                {Empty, Tree, Empty, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Empty, Empty},
                {Empty, Empty, Empty, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Empty, Empty},
                {Empty, Tree, Tree, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Empty, Empty},
                {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty},
                {Stone, Stone, Stone, Stone, Stone, Empty, Empty, Empty, Stone, Stone, Empty, Stone},
                {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty},
                {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Princess}
        };
        this.width = grid.length;
        this.height = grid[0].length;
    }

    public MapLevel(String string){
        //Decompress the string
        String finalString = "";
        int index = 0;
        while(index < string.length()-1){
            if(Character.isDigit(string.charAt(index+1))){
                for(int i=0; i<Character.getNumericValue(string.charAt(index+1)); i++){
                    finalString += string.charAt(index);
                }
                index++;
            }
            else
                finalString += string.charAt(index);
            index++;
        }
        finalString += "x";
        //Define height and width of the map
        string = finalString;
        int x =0, y=0;
        index =0;
        int height = 0;
        for(int i=0; i<string.length(); i++){
            if(string.charAt(i) == 'x')
                height++;
        }
        this.height = height;
        this.width = string.indexOf("x");
        //Populate the grid with the string info
        this.grid = new Entity[this.width][height];
        System.out.println("height " + this.height);
        System.out.println("width " + this.width);
        while(y<this.height() ){
            while (string.charAt(index) != 'x'){
                grid[x][y] = Entity.fromCode(string.charAt(index));
                index++;
                x++;
            }
            x = 0;
            index++;
            y++;
        }
    }
    public int width() {
        return width;    }
    public int height() {
        return height;
    }
    public Entity get(int i, int j) {
        return grid[j][i];
    }
}
