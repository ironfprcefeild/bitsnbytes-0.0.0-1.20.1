package net.ironf.bitsnbytes.items.custom;

import com.mojang.logging.LogUtils;
import net.ironf.bitsnbytes.backend.circuitry.basicLinkingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeItem;
import org.slf4j.Logger;

import java.util.Optional;


public class connectorItem extends Item implements IForgeItem {
    public connectorItem(Properties p_41383_) {
        super(p_41383_);
    }

    private static final Logger LOGGER = LogUtils.getLogger();


    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        //Check for proper contexts
        Level level = context.getLevel();
        if (level.isClientSide || context.getHand() == InteractionHand.OFF_HAND){
            return InteractionResult.PASS;
        }

        //Get The Clicked Pos
        BlockPos clickedPos = context.getClickedPos();

        //Decided what to do with that (is the source/to block found?)
        if (stack.getOrCreateTag().getBoolean("tofound")){
            //If it is, create the connection
            Optional<String> statusString = createConnection(readBP(stack,"savedpos"),clickedPos,context.getLevel());

            //If the string is present it indicates an error message, for now im printing it to logs
            statusString.ifPresent(LOGGER::info);

            //Reset the toFound Booolean.
            stack.getOrCreateTag().putBoolean("tofound",false);
        } else {
            writeBP(clickedPos,stack,"savedpos");
            stack.getOrCreateTag().putBoolean("tofound",true);
        }

        return InteractionResult.PASS;

    }
    /*
        An Empty Optional is returned if the connection was successful.
        Otherwise, an Optional Containing a string with the error message is returned.
     */

    public Optional<String> createConnection(BlockPos toPos, BlockPos fromPos, Level level){

        //Getting the links
        Optional<basicLinkingBlockEntity> toOp = getLinkerAt(toPos,level);
        Optional<basicLinkingBlockEntity> fromOp = getLinkerAt(fromPos,level);
        if (toOp.isEmpty() || fromOp.isEmpty()){
            return Optional.of("One of the selections no longer exists");
        }

        //Getting the Link BE from the optional
        basicLinkingBlockEntity to = toOp.get();
        basicLinkingBlockEntity from = fromOp.get();

        //Make Connections, if either connection is bad (returned optional is empty, meaning no ID was found) terminate connections and end
        if (from.addOutput(toPos).isEmpty() || to.addInput(fromPos).isEmpty()){
            from.terminateConnection(toPos);
            to.terminateConnection(fromPos);
            return Optional.of("One of the connections does not have any open nodes");
        }

        //Test for Network Validity, terminate connections if invalid
        if (!from.isNetWorkValid()){
            from.terminateConnection(toPos);
            to.terminateConnection(fromPos);
            return Optional.of("The Connections causes a loop in the network, which is invalid");
        }

        to.onRecompute();

        return Optional.empty();
    }
    public Optional<basicLinkingBlockEntity> getLinkerAt(BlockPos at, Level level){
        BlockEntity preGate = level.getBlockEntity(at);
        if (preGate instanceof basicLinkingBlockEntity){
            return Optional.of((basicLinkingBlockEntity) preGate);
        }
        return Optional.empty();
    }
    public void writeBP(BlockPos toWrite, ItemStack writeOnto, String key){
        writeOnto.getOrCreateTag().putIntArray(key,new int[]{toWrite.getX(), toWrite.getY(), toWrite.getZ()});
    }

    public BlockPos readBP(ItemStack readFrom, String key){
        int[] readArray = readFrom.getOrCreateTag().getIntArray(key);
        return new BlockPos(readArray[0],readArray[1],readArray[2]);
    }

    //ILL get to this trust me


}
