package net.jdrosen.advent23;

public interface Direction {
    
    boolean isElfInThisDirection(Elf me, Elf otherElf);

    ElfPosition proposedMovementInThisDirection(Elf me);

}
