package net.ironf.bitsnbytes.blocks.gates.supply;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.ironf.bitsnbytes.blocks.AllBlockEntities;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class supplyBlock extends KineticBlock implements ICogWheel, IBE<supplyBlockEntity> {
    public supplyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<supplyBlockEntity> getBlockEntityClass() {
        return supplyBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends supplyBlockEntity> getBlockEntityType() {
        return AllBlockEntities.SUPPLY.get();
    }
}
