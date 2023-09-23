package net.ironf.bitsnbytes.blocks.gates.adder;

import com.simibubi.create.foundation.block.IBE;
import net.ironf.bitsnbytes.backend.circuitry.gateBlockEntity;
import net.ironf.bitsnbytes.backend.circuitry.gateFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class adderBlockEntity extends gateBlockEntity {
    public adderBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public gateFunction getGateFunction() {
        return new adderFunction();
    }

}
