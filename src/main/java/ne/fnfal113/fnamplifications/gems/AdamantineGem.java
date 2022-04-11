package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.OnItemDamageHandler;
import ne.fnfal113.fnamplifications.gems.implementation.Gem;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class AdamantineGem extends AbstractGem implements OnItemDamageHandler {

    @Getter
    private final int chance;

    public AdamantineGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 15);

        this.chance = FNAmplifications.getInstance().getConfigManager().getValueById(this.getId() + "-percent-chance");
    }

    @Override
    public void onDrag(InventoryClickEvent event, Player player) {
        if(event.getCursor() == null){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        SlimefunItem slimefunItem = SlimefunItem.getByItem(event.getCursor());

        if(slimefunItem != null && currentItem != null) {
            if (WeaponArmorEnum.SWORDS.isTagged(currentItem.getType()) || WeaponArmorEnum.PICKAXE.isTagged(currentItem.getType()) ||
                    WeaponArmorEnum.AXES.isTagged(currentItem.getType()) || WeaponArmorEnum.SHOVELS.isTagged(currentItem.getType()) ||
                    WeaponArmorEnum.BOWS.isTagged(currentItem.getType()) || WeaponArmorEnum.HOES.isTagged(currentItem.getType())) {
                new Gem(slimefunItem, currentItem, player).onDrag(event, false);
            } else {
                player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on weapons and tools only"));
            }
        }
    }

    @Override
    public void onDurabilityChange(PlayerItemDamageEvent event) {

        if(ThreadLocalRandom.current().nextInt(100) < getChance()){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.colorTranslator("&6Adamantine gem has taken effect!"));
        }
    }

}