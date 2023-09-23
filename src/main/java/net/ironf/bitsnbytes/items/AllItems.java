package net.ironf.bitsnbytes.items;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.Create;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.ironf.bitsnbytes.items.custom.SpoolItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static net.ironf.bitsnbytes.BitsNBytes.REGISTRATE;

public class AllItems {
    static {
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final ItemEntry<SpoolItem> SPOOL = Create.REGISTRATE.item("spool", SpoolItem::new)
            .register();

    public static void register() {}
}
