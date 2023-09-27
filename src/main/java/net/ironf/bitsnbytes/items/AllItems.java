package net.ironf.bitsnbytes.items;

import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.ironf.bitsnbytes.items.custom.connectorItem;

import static net.ironf.bitsnbytes.BitsNBytes.REGISTRATE;

public class AllItems {
    static {
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final ItemEntry<connectorItem> SPOOL = REGISTRATE.item("spool", connectorItem::new)
            .properties(p -> p.stacksTo(1).durability(100).defaultDurability(100))
            .register();

    public static void register() {}
}
