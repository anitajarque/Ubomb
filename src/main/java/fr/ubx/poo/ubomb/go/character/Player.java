/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.*;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.go.decor.bonus.bombNumber.BombNumber;
import fr.ubx.poo.ubomb.go.decor.bonus.bombNumber.BombNumberDec;
import fr.ubx.poo.ubomb.go.decor.bonus.bombRange.BombRange;
import fr.ubx.poo.ubomb.go.decor.bonus.bombRange.BombRangeDec;

public class Player extends GameObject implements TakeVisitor {

    private Direction direction;
    private boolean moveRequested = false;
    private int lives; //Delete final (no make sense for me)
    private int keys;
    private int bombRange;
    private int numBombs;
    private int bombBagCapacity;
    private long time; //if there is more than one player

    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    public Player(Game game, Position position, int bombBagCapacity) {
        super(game, position);
        bombRange=1;
        numBombs=0;
        this.bombBagCapacity = bombBagCapacity;
        this.direction = Direction.DOWN;
        this.lives = game.configuration().playerLives();
        keys=1;
    }


    @Override
    public void take(BombRange bombRangeElement) {
        if(bombRangeElement instanceof BombRangeDec){
            bombRange--;
            System.out.println("One less range bomb ...");
        }
        else{
            bombRange++;
            System.out.println("One more range bomb ...");
        }

    }

    @Override
    public void take(BombNumber bomb) {
        if(bomb instanceof BombNumberDec) {
            numBombs--;
            System.out.println("One less range bomb ...");
        }
        else{
            numBombs++;
            System.out.println("One more range bomb ...");
        }
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
        this.setPosition(nextPos);
        if (next instanceof Takeable takeable) {
            takeable.takenBy(this);
        }
        if(next instanceof Movable movable){
            movable.doMove(direction);
        }
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

    public void damage() {
        this.lives--;
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
        if(next instanceof Monster){
            return true;
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
        damage();
    }

    public int getBombRange() {
        return bombRange;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public int getBombBagCapacity() {
        return bombBagCapacity;
    }

    public void checkKey() {
        if(this.keys >0 && this.game.checkNearDoors(this.getPosition())){
            this.keys--;
        }
    }

    public void addBomb() {
        if(bombBagCapacity>numBombs){
            numBombs++;
        }
    }

    public void useBomb() {
        if(numBombs>0){
            numBombs--;
        }
    }

}
