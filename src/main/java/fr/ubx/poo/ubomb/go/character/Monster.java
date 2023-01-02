package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

public class Monster extends GameObject{

    private Direction direction;
    private boolean moveRequested = false;

    public Monster(Game game, Position position) {
        super(game, position);
        this.direction = Direction.DOWN;
    }

    public Monster(Position position) {
        super(position);
        this.direction = Direction.DOWN;
    }

    public boolean canMove(Direction direction){
        GameObject next = game.grid().get(direction.nextPosition(getPosition()));

        if(direction.nextPosition(getPosition()).x()<0 || direction.nextPosition(getPosition()).y()<0 || game.grid().height()<=direction.nextPosition(getPosition()).y() || game.grid().width()<=direction.nextPosition(getPosition()).x()){
            return false;
        }
        if(next instanceof Decor && !(next instanceof Bonus))
            return false;
        return true;
    }

    public void move(Direction direction){
        //The moster has to be removed from the level elements map in order to
        // update his position on the <Position, GameObjet> map
        Position nextPos = direction.nextPosition(getPosition());
        game.getLevel().getMonsters().remove(getPosition());
        this.setPosition(nextPos);
        game.getLevel().getMonsters().put(this.getPosition(), this);
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }
}