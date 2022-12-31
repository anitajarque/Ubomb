package fr.ubx.poo.ubomb.go.decor.bonus.bombNumber;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;

public class BombNumberInc extends BombNumber {
    public BombNumberInc(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        if(player.getNumBombs()< player.getBombBagCapacity()){
            super.takenBy(player);
        }
    }
}