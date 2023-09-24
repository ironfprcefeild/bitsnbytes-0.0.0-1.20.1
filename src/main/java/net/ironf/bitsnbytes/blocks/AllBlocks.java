package net.ironf.bitsnbytes.blocks;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.ironf.bitsnbytes.blocks.gates.adder.adderBlock;
import net.ironf.bitsnbytes.blocks.gates.supply.supplyBlock;
import net.minecraft.world.level.material.MapColor;

import static net.ironf.bitsnbytes.BitsNBytes.REGISTRATE;

public class AllBlocks {

    static {
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final BlockEntry<adderBlock> ADDER = REGISTRATE.block("adder", adderBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.METAL).noOcclusion())
            .simpleItem()
            .register();


    public static final BlockEntry<supplyBlock> SUPPLY = REGISTRATE.block("supply", supplyBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.METAL).noOcclusion())
            .simpleItem()
            .register();



    public static void register() {}
}
