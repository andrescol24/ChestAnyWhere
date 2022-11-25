package co.andrescol.mc.plugin.chestanywhere.command;

import co.andrescol.mc.library.command.AMainCommand;

/**
 * Superclass that define utils methods
 * 
 * @author andrescol24
 *
 */
public class ChestAnyWhereCommand extends AMainCommand {

    public ChestAnyWhereCommand() {
        this.setDefaultCommand(new OpenChestCommand());
    }
}
