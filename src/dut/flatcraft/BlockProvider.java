package dut.flatcraft;


@FunctionalInterface
public interface BlockProvider {
    Resource consumeCurrentBlock();
}
