package net.ironf.bitsnbytes.blocks.gates.adder;

import net.ironf.bitsnbytes.backend.circuitry.gateFunction;

public class adderFunction extends gateFunction {
    @Override
    public int compute(int[] inputs, int mode) {
        return inputs[0] + inputs[1];
    }
}
