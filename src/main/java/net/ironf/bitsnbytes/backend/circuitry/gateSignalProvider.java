package net.ironf.bitsnbytes.backend.circuitry;

public interface gateSignalProvider {
    default int getPush(){
        return 0;
    }
}
