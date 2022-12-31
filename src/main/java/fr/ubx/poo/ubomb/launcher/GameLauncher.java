package fr.ubx.poo.ubomb.launcher;

import fr.ubx.poo.ubomb.game.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Properties;

public class GameLauncher {
    public static Game load(Reader in) {
        int x, y, bombBagCapacity, playerLives, playerInvisibulityTime, mosterVelocity, monsterInvisibilyTime, numLevels;
        String[] pos;
        Position position;
        ArrayList<MapLevel> levels = new ArrayList<>();

        String level;
        try {
            Properties config = new Properties();
            config.load(in);
            System.out.println("File loaded");
            numLevels = Integer.valueOf(config.getProperty("levels", "1"));
            bombBagCapacity = Integer.valueOf(config.getProperty("bomBagCapacity", "3"));
            playerLives = Integer.valueOf(config.getProperty("playerLives", "5"));
            playerInvisibulityTime = Integer.valueOf(config.getProperty("playerInvisibilityTime", "4000"));
            mosterVelocity = Integer.valueOf(config.getProperty("mosterVelocity", "5"));
            monsterInvisibilyTime = Integer.valueOf(config.getProperty("mosterInvisibilityTime", "1000"));
            pos = config.getProperty("player").split("x");
            position = new Position(Integer.valueOf(pos[0]), Integer.valueOf(pos[1]));
            for(int i=0; i<numLevels; i++){
                String stringLevel = config.getProperty("level" + Integer.toString(i+1));
                levels.add((new MapLevel(stringLevel)));
            }
            System.out.println("Num of levels: " + numLevels);
            System.out.println("Num of levels: " + levels.size());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Configuration configuration = new Configuration(position, bombBagCapacity, playerLives, playerInvisibulityTime, mosterVelocity, monsterInvisibilyTime);
        //Configuration configuration = new Configuration(new Position(0, 0), 3, 5, 4000, 5, 1000);
        return new Game(configuration, levels);
    }

    public static Game laodDefault(){
        Configuration configuration = new Configuration(new Position(0, 0), 3, 5, 4000, 5, 1000);
        ArrayList<MapLevel> levels = new ArrayList<>();
        levels.add(new MapLevel());
        return new Game(configuration,levels);
    }

}