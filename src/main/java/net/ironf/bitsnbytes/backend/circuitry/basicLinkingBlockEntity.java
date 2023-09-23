package net.ironf.bitsnbytes.backend.circuitry;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class basicLinkingBlockEntity extends SmartBlockEntity {
    public basicLinkingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void onRecompute(){
        for (BlockPos bp : outputs){
            getLinkerAt(bp).ifPresent(basicLinkingBlockEntity::onRecompute);
        }
    }

    public BlockPos[] inputs;
    public BlockPos[] outputs;

    public Integer getInputCount(){
        return null;
    }

    public Integer getOutputCount(){
        return null;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    public int getPushedValueAt(BlockPos at){
        BlockEntity preGate = this.level.getBlockEntity(at);
        if (preGate instanceof gateSignalProvider){
            return ((gateBlockEntity) preGate).getPush();
        }
        return 0;
    }
    public Optional<basicLinkingBlockEntity> getLinkerAt(BlockPos at){
        BlockEntity preGate = this.level.getBlockEntity(at);
        if (preGate instanceof basicLinkingBlockEntity){
            return Optional.of((basicLinkingBlockEntity) preGate);
        }
        return Optional.empty();
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        int[] writingHelper;
        for (int i = 0; i < getInputCount(); i++){
            writingHelper = tag.getIntArray("input" + i);
            this.inputs[i] = new BlockPos(writingHelper[0],writingHelper[1],writingHelper[2]);
        }
        for (int i = 0; i < getOutputCount(); i++){
            writingHelper = tag.getIntArray("output" + i);
            this.outputs[i] = new BlockPos(writingHelper[0],writingHelper[1],writingHelper[2]);
        }
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        for (int i = 0; i < getInputCount(); i++){
            tag.putIntArray("input" + i,new int[]{inputs[i].getX(),inputs[i].getY(),inputs[i].getZ()});
        }
        for (int i = 0; i < getOutputCount(); i++){
            tag.putIntArray("output" + i,new int[]{outputs[i].getX(),outputs[i].getY(),outputs[i].getZ()});
        }

    }
}
