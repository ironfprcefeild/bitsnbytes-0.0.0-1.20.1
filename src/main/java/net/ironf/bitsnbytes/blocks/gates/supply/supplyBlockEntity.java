package net.ironf.bitsnbytes.blocks.gates.supply;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.ironf.bitsnbytes.backend.circuitry.basicLinkingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class supplyBlockEntity extends basicLinkingBlockEntity {
    public supplyBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }


    @Override
    public Integer getPush() {
        BlockEntity preCog = this.level.getBlockEntity(this.getBlockPos().below());
        if (preCog instanceof KineticBlockEntity){
            return (int) ((KineticBlockEntity) preCog).getSpeed();
        }
        return 0;
    }

}
