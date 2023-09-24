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

    public BlockPos[] inputs = {null,null,null};
    public BlockPos[] outputs = {null,null,null};

    public Integer getInputCount(){
        return null;
    }

    public Integer getOutputCount(){
        return null;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    public Integer getPush(){
        return null;
    }

    public int[] getPushedValuesAt(BlockPos[] ats){
        int[] toReturn = new int[ats.length];
        for (int i = 0; i < getInputCount(); i++) {
            BlockEntity preGate = this.level.getBlockEntity(ats[i]);
            if (preGate instanceof basicLinkingBlockEntity) {
                toReturn[i] = ((basicLinkingBlockEntity) preGate).getPush();
            }
        }
        return toReturn;
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
            if (bp != null) {
                Optional<basicLinkingBlockEntity> linkAt = getLinkerAt(bp);
                linkAt.ifPresent(linkingBlock -> linkingBlock.terminateConnection(bp));
            }
        }
        for (BlockPos bp : this.inputs){
            if (bp != null) {
                Optional<basicLinkingBlockEntity> linkAt = getLinkerAt(bp);
                linkAt.ifPresent(basicLinkingBlockEntity -> basicLinkingBlockEntity.terminateConnection(bp));
            }
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
        for (int i = 0; i < getInputCount(); i++){
            int[] writingHelper = tag.getIntArray("input" + i);
            if (writingHelper.length != 0) {
                this.inputs[i] = new BlockPos(writingHelper[0], writingHelper[1], writingHelper[2]);
            }
        }
        for (int i = 0; i < getOutputCount(); i++){
            int[] writingHelper = tag.getIntArray("output" + i);
            if (writingHelper.length != 0) {
                this.outputs[i] = new BlockPos(writingHelper[0], writingHelper[1], writingHelper[2]);
            }
        }
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        for (int i = 0; i < getInputCount(); i++){
            if (inputs[i] != null) {
                tag.putIntArray("input" + i, new int[]{inputs[i].getX(), inputs[i].getY(), inputs[i].getZ()});
            }
        }
        for (int i = 0; i < getOutputCount(); i++){
            if (inputs[i] != null) {
                tag.putIntArray("output" + i, new int[]{outputs[i].getX(), outputs[i].getY(), outputs[i].getZ()});
            }
        }

    }


}
