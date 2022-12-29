package net.jdrosen.advent23;

public class SouthDirection implements Direction {

    @Override
    public boolean isElfInThisDirection(Elf me, Elf otherElf) {
        
        if((otherElf.curPosition.ypos == (me.curPosition.ypos + 1)) &&
            (Math.abs(otherElf.curPosition.xpos - me.curPosition.xpos) < 2)) {
                return true;
            } else {
                return false;
        }  

    }

    @Override
    public ElfPosition proposedMovementInThisDirection(Elf me) {

        ElfPosition newElfPosition = new ElfPosition(me.curPosition.xpos, me.curPosition.ypos);
        newElfPosition.goSouth();

        return(newElfPosition);
    }
    
}
