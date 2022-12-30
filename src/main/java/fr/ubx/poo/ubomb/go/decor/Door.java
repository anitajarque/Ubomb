package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.Walkable;
import fr.ubx.poo.ubomb.go.character.Player;

public class Door extends Decor implements Walkable, Takeable {

    private boolean open;
    private int level;

    public Door(Game game, Position position, int level, boolean open) {

        super(game, position);
        this.open = open;
        this.level = level;
    }

    @Override
    public boolean walkableBy(Player player) {
        return this.open;
    }

    @Override
    public void takenBy(Player player) {
        this.game.changeLevel(this.level);
    }

    public boolean isOpen() {
        return open;
    }

    public int getLevel() {
        return level;
    }

    public void open() {
        this.open = true;
        System.out.println("Door opened");
        this.changeSprite(true);
    }
}
