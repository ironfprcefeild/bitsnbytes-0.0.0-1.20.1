package net.ironf.bitsnbytes.backend.circuitry;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class basicLinkingBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    public basicLinkingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void onRecompute(){
        for (BlockPos bp : outputs){
            if (bp != null) {
                getLinkerAt(bp).ifPresent(basicLinkingBlockEntity::onRecompute);
            }
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
            if (ats[i] == null){
               toReturn[i] = 0;
               continue;
            }
            Optional<basicLinkingBlockEntity> preLinker = getLinkerAt(ats[i]);
            toReturn[i] = preLinker.isPresent() ? preLinker.get().getPush() : 0;
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
            if (bp == null){
                continue;
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
                linkAt.ifPresent(basicLinkingBlockEntity::onRecompute);
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
        this.removeInput(toTerminate);
        this.removeOutput(toTerminate);
    }
    /*
    Returns an Optional containing the position in the I/O array used in which a block pos was modified. If no connections were unused, then it returns an empty optional
     */
    public Optional<Integer> addInput(BlockPos toAdd){
        for (int i = 0; i < getInputCount(); i++){
            if (this.inputs[i] == null){
                this.inputs[i] = toAdd;
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public Optional<Integer> addOutput(BlockPos toAdd){
        for (int i = 0; i < getOutputCount(); i++){
            if (this.outputs[i] == null){
                this.outputs[i] = toAdd;
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public void removeInput(BlockPos toRemove){
        for (int i = 0; i < getInputCount(); i++){
            if (this.outputs[i] == null){
                this.outputs[i] = toRemove;
            }
        }
    }

    public void removeOutput(BlockPos toRemove){
        for (int i = 0; i < getOutputCount(); i++){
            if (this.outputs[i] == toRemove){
                this.outputs[i] = null;
            }
        }
    }



    @Override
    public void onLoad() {
        this.onRecompute();
        super.onLoad();
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
            if (outputs[i] != null) {
                tag.putIntArray("output" + i, new int[]{outputs[i].getX(), outputs[i].getY(), outputs[i].getZ()});
            }
        }

    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        tooltip.add(Component.literal("Pushing:" + this.getPush().toString()));
        return IHaveGoggleInformation.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

}
