package net.ironf.bitsnbytes.blocks.gates.adder;

import net.ironf.bitsnbytes.backend.circuitry.gateFunction;

public class adderFunction extends gateFunction {
    @Override
    public int compute(int inputA, int inputB, int mode) {
        return inputA + inputB;
    }
}
