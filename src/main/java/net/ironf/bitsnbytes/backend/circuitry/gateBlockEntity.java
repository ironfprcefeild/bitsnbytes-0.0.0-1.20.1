package net.ironf.bitsnbytes.backend.circuitry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class gateBlockEntity extends basicLinkingBlockEntity implements gateSignalProvider {

    public gateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public gateFunction getGateFunction(){
        return null;
    }

    public BlockPos posInputA;
    public BlockPos posInputB;
    public BlockPos posPushTo;

    public int push = 0;
    public int mode = 0;

    @Override
    public void onRecompute(){
        this.push = this.getGateFunction().compute(getPushedValueAt(this.inputs[0]), getPushedValueAt(this.inputs[1]),this.mode);
        super.onRecompute();
    }

    @Override
    public int getPush() {
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



    @Override
    public @NotNull ModelData getModelData() {
        return super.getModelData();
    }
}
