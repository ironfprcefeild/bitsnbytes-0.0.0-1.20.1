package net.ironf.bitsnbytes.blocks.gates.supply;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.ironf.bitsnbytes.backend.circuitry.gateSignalProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class supplyBlockEntity extends KineticBlockEntity implements gateSignalProvider {
    public supplyBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public float calculateStressApplied() {
        float impact = 1;
        this.lastStressApplied = impact;
        return impact;
    }

    @Override
    public int getPush() {
        return (int) this.getSpeed();
    }
}
