package fr.ubx.poo.ubomb.launcher;

public class MapLevel {
    private final int width;
    private final int height;
    private final Entity[][] grid;
    public MapLevel(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Entity[height][width];
    }

    public MapLevel(String string){
        System.out.println("Compresed string " + string);
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
        System.out.println("Compresed string " + finalString);
        string = finalString;
        if (string == null){
            System.out.println("null string");
        }
        int x =0, y=0;
        index =0;
        int height = 0;
        for(int i=0; i<string.length(); i++){
            if(string.charAt(i) == 'x')
                height++;
        }
        this.height = height;
        this.width = string.indexOf("x");
        this.grid = new Entity[this.width][height];

        while(index<string.length()){
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
    public void set(int i, int j, Entity entity) {
        grid[j][i] = entity;
    }
}
