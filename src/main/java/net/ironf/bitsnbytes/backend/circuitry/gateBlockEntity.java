package net.ironf.bitsnbytes.backend.circuitry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class gateBlockEntity extends basicLinkingBlockEntity {

    public gateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public gateFunction getGateFunction(){
        return null;
    }

    public int push = 0;
    public int mode = 0;

    @Override
    public void onRecompute(){
        this.push = this.getGateFunction().compute(getPushedValuesAt(this.inputs),this.mode);
        super.onRecompute();
    }

    @Override
    public Integer getPush() {
        return push;
    }

    @Override
    public Integer getInputCount() {
        return 2;
    }

    @Override
    public Integer getOutputCount() {
        return 1;
    }


}
