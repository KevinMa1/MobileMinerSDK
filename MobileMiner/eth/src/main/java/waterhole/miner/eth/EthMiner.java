package waterhole.miner.eth;

import java.io.ObjectStreamException;

import waterhole.miner.core.AbstractMiner;

public final class EthMiner extends AbstractMiner {

    private EthMiner() {
    }

    public static EthMiner instance() {
        return Holder.instance;
    }

    private static class Holder {
        static EthMiner instance = new EthMiner();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance();
    }

    @Override
    public void startMine() {

    }

    @Override
    public void stopMine() {

    }
}