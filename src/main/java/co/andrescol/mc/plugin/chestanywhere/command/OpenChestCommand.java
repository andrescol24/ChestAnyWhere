package co.andrescol.mc.plugin.chestanywhere.command;

import co.andrescol.mc.library.command.ASubCommand;
import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.chestanywhere.ChestAnyWhereHolder;
import co.andrescol.mc.plugin.chestanywhere.configuration.Message;
import co.andrescol.mc.plugin.chestanywhere.data.ChestAnyWhere;
import co.andrescol.mc.plugin.chestanywhere.data.YamlDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * This command executor make that the player open the inventory. This command
 * can be executed by players and console
 *
 * @author andrescol24
 */
public class OpenChestCommand extends ASubCommand {

    protected OpenChestCommand() {
        super("default", "chestanywhere.cmd");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        int chestSize = this.getChestSizeByPermission(player);

        if (chestSize == 0) {
            AMessage.sendMessage(sender, Message.NOT_PERMISSION_OPEN);
            return true;
        }

        // Read the playerStorage (content)
        YamlDataManager dataManager = YamlDataManager.getInstance();
        ChestAnyWhere content = dataManager.get(player);
        Inventory inventory = Bukkit.createInventory(new ChestAnyWhereHolder(), chestSize, AMessage.getMessage(Message.STORAGE_NAME));
        if(inventory.getSize() >= content.getContent().length) {
            inventory.setContents(content.getContent());
        } else {
            for (int i = 0; i < content.getContent().length; i++) {
                if(content.getContent()[i] != null) {
                    Map<Integer, ItemStack> itemsDidNotFit = inventory.addItem(content.getContent()[i]);
                    itemsDidNotFit.forEach((position, item) -> player.getWorld().dropItemNaturally(player.getLocation(), item));
                }
            }
            dataManager.update(content, inventory);
        }
        player.openInventory(inventory);

        return true;
    }

    private int getChestSizeByPermission(Player player) {
        List<Integer> sizes = Arrays.asList(9, 18, 27, 36, 45, 54);
        Optional<Integer> size = sizes.stream()
                .filter(x -> player.hasPermission("chestanywhere.inventory.size." + x))
                .max(Integer::compareTo);
        return size.orElse(0);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new LinkedList<>();
    }

    @Override
    public boolean goodUsage(CommandSender sender, Command command, String label, String[] args) {
        return sender instanceof Player;
    }
}
