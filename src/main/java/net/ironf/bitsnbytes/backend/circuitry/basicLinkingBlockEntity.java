package net.ironf.bitsnbytes.backend.circuitry;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
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

    public boolean isNetWorkValid(){
        return testForValidNetwork(this.getBlockPos());
    }
    public boolean testForValidNetwork(BlockPos testingPos){
        boolean toReturn = true;
        //Loop through Block Pos in this Level
        for (BlockPos bp : this.outputs){

            //Return if match is found
            if (bp == testingPos){
                return false;
            }
            //Get Linked Block Pos
            Optional<basicLinkingBlockEntity> linkAt = getLinkerAt(bp);
            //Check Look through the linked blocks outputs
            if (linkAt.isPresent() && linkAt.get().getOutputCount() > 0 && !linkAt.get().testForValidNetwork(testingPos)){
                 return false;
            }
        }
        return true;
    }

    @Override
    public void remove() {
        for (BlockPos bp : this.outputs){
            Optional<basicLinkingBlockEntity> linkAt = getLinkerAt(bp);
            linkAt.ifPresent(linkingBlock -> linkingBlock.terminateConnection(bp));
        }
        for (BlockPos bp : this.inputs){
            Optional<basicLinkingBlockEntity> linkAt = getLinkerAt(bp);
            linkAt.ifPresent(basicLinkingBlockEntity -> basicLinkingBlockEntity.terminateConnection(bp));
        }
    }

    public void terminateConnection(BlockPos toTerminate){
        for (int i = 0; i < getInputCount(); i++){
            if (this.inputs[i] == toTerminate){
                this.inputs[i] = null;
            }
        }
        for (int i = 0; i < getOutputCount(); i++){
            if (this.outputs[i] == toTerminate){
                this.outputs[i] = null;
            }
        }
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
