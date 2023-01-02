package fr.ubx.poo.ubomb.game;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.Door;
import fr.ubx.poo.ubomb.launcher.MapLevel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {

    private boolean levelChanged = false;
    private final Configuration configuration;
    private final Player player;
    private int level = 0;
    private final ArrayList<Level> levels;


    public Game(Configuration configuration, ArrayList<MapLevel> maps) {
        this.configuration = configuration;
        this.levels = new ArrayList<>();
        int index=0;
        for(MapLevel map : maps){
            this.levels.add(new Level(this, map));
        }
        player = new Player(this, configuration.playerPosition(), configuration().bombBagCapacity());
    }
    public Configuration configuration() {
        return configuration;
    }
    // Returns the player, monsters and bomb at a given position
    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new LinkedList<>();
        Monster monster1 = new Monster(new Position(10, 10));
        gos.add(monster1);
        if (player().getPosition().equals(position))
            gos.add(player);
        return gos;
    }
    public Grid grid() {
        return this.getLevel();
    }
    public Player player() {
        return this.player;
    }
    public Level getLevel(){
        return this.levels.get(this.level);
    }

    public void changeLevel(int level) {
        //Update the level, and search for the connecting door, to
        //position the player properly
        int newLevel = this.level + level;
        if(newLevel < this.levels.size() && newLevel>=0){
            this.level = newLevel;
            this.levelChanged = true;
            boolean found = false;
            Object list[] = this.levels.get(this.level).getElements().values().toArray();
            int index = 0;
            while(!found &&  index< list.length){
                if(list[index] instanceof Door door && door.getLevel()== -1*level){
                    this.player.setPosition(door.getPosition());
                    found = true;
                }
                index++;
            }
        }
    }

    public boolean checkLevelChange(){
        if(this.levelChanged){
            this.levelChanged = false;
            return true;
        }
        return false;
    }

    public boolean checkNearDoors(Position position) {
        //Search for doors sorrounding the Postion position
        Position pos;
        Decor decor;
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                pos = new Position(position.x()+i, position.y()+j);
                decor = this.getLevel().get(pos);
                if(decor instanceof Door door && !door.isOpen()){
                    System.out.println("Closed door found");
                    door.open();
                    return true;
                }
            }
        }
        return false;
    }
}