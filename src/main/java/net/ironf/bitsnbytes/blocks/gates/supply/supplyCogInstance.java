package net.ironf.bitsnbytes.blocks.gates.supply;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;

public class supplyCogInstance extends SingleRotatingInstance<supplyBlockEntity> {
    public supplyCogInstance(MaterialManager materialManager, supplyBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }
}
