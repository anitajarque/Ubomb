/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.TakeVisitor;
import fr.ubx.poo.ubomb.go.decor.Princess;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import javafx.application.Platform;

public class Player extends GameObject implements Movable, TakeVisitor {

    private Direction direction;
    private boolean moveRequested = false;
    private int lives; //Delete final (no make sense for me)
    private int keys;

    public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = game.configuration().playerLives();
        keys=0;

    }

    @Override
    public void take(Heart heart) {
        System.out.println("One more live ...");
        lives++;
    }

    @Override
    public void take(Key key) {
        keys++;
        System.out.println("Take the key ...");
    }

    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        GameObject next = game.grid().get(nextPos);
        if (next instanceof Bonus bonus) {
                bonus.takenBy(this);
        }
        setPosition(nextPos);
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    public int getLives() {
        return lives;
    }

    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }

    public final boolean canMove(Direction direction) {
        // Need to be updated ;-)
        GameObject next = game.grid().get(direction.nextPosition(getPosition()));
        if( next!=null && !next.walkableBy(this) ){
            return false;
        }

        if(direction.nextPosition(getPosition()).x()<0 || direction.nextPosition(getPosition()).y()<0 || game.grid().height()<=direction.nextPosition(getPosition()).y() || game.grid().width()<=direction.nextPosition(getPosition()).x()){
            return false;
        }
        if(next instanceof Movable obj ){
            if(obj.canMove(direction)){
                obj.doMove(direction);
                return true;
            }
            else{
                return false;
            }
        }
        //When the player is at the princess position we exit of the game
        if(next instanceof Princess){ //
            Platform.exit();
            System.out.println("I've the princess I win");
            System.exit(0);
        }
        return true;
    }



    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    @Override
    public void explode() {
        // TODO
    }

}
