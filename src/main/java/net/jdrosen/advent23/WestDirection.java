package net.jdrosen.advent23;

public class WestDirection implements Direction {

    @Override
    public boolean isElfInThisDirection(Elf me, Elf otherElf) {
        
        if((otherElf.curPosition.xpos == (me.curPosition.xpos - 1)) &&
            (Math.abs(otherElf.curPosition.ypos - me.curPosition.ypos) < 2)) {
                return true;
            } else {
                return false;
        }  

    }

    @Override
    public ElfPosition proposedMovementInThisDirection(Elf me) {

        ElfPosition newElfPosition = new ElfPosition(me.curPosition.xpos, me.curPosition.ypos);
        newElfPosition.goWest();

        return(newElfPosition);
    }
    
}

