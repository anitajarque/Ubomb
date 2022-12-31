package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;

public class Bomb extends GameObject {


    int phases;
    boolean isExploded;   
    long initialTime;

    public int getPhases() {
        return phases;
    }

    public int addPhase() {
        return phases--;
    }


    public long getInitialTime() {
        return initialTime;
    }

    public Bomb(Position position, long initialTime) {
        super(position);
        isExploded = false;
        phases = 3;
        this.initialTime=initialTime;

    }

    @Override
    public void explode() {
        isExploded=true;
    }

    public boolean getIsExploded(){
        return isExploded;
    }
}
