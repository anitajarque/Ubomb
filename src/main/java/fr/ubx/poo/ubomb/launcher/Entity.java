package fr.ubx.poo.ubomb.launcher;

public enum Entity {
    Empty('_'),
    Box('B'),
    Stone('S'),
    Tree('T'),

    BombRangeInc('>'),
    BombRangeDec('<'),
    BombNumberInc('+'),
    BombNumberDec('-'),
    Heart('H'),
    Key('K'),
    DoorPrevOpened('V'),
    DoorNextOpened('N'),
    DoorNextClosed('n'),
    Monster('M'),
    Princess('W');

    private final char code;

    Entity(char c) {
        this.code = c;
    }

    public char getCode() { return this.code; }

    public static Entity fromCode(char c) {
        for (Entity entity : values()) {
            if (entity.code == c)
                return entity;
        }
        System.out.println("Invalid character " + c);
        throw new MapException("Invalid character " + c);
    }
    public boolean isAccessible(){
        if(fromCode(code)==Heart && fromCode(code)== Key && fromCode(code)==Monster && fromCode(code)==Princess){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return Character.toString(code);
    }

}
