package net.ironf.bitsnbytes.blocks.gates.adder;

import com.simibubi.create.foundation.block.IBE;
import net.ironf.bitsnbytes.blocks.AllBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class adderBlock extends BaseEntityBlock implements IBE<adderBlockEntity> {
    public adderBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public Class<adderBlockEntity> getBlockEntityClass() {
        return adderBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends adderBlockEntity> getBlockEntityType() {
        return AllBlockEntities.ADDER.get();
    }


}
