package net.ironf.bitsnbytes.blocks;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.ironf.bitsnbytes.blocks.gates.adder.adderBlockEntity;
import net.ironf.bitsnbytes.blocks.gates.supply.*;

import static net.ironf.bitsnbytes.BitsNBytes.REGISTRATE;

public class AllBlockEntities {

    public static final BlockEntityEntry<adderBlockEntity> ADDER = REGISTRATE
            .blockEntity("adder",adderBlockEntity::new)
            .validBlocks(AllBlocks.ADDER)
            .register();


    public static final BlockEntityEntry<supplyBlockEntity> SUPPLY = REGISTRATE
            .blockEntity("supply", supplyBlockEntity::new)
            .instance(() -> supplyCogInstance::new, true)
            .validBlocks(AllBlocks.SUPPLY)
            .renderer(() -> supplyBlockEntityRenderer::new)
            .register();



    public static void register() {}


}
