package dut.flatcraft;

public class ComplexCellFactory extends ModernCellFactory {

	@Override
	public Cell createSoil() {
		if (RAND.nextInt(10) < 7) {
			return new NormalCell(MineUtils.DIRT);
		}
		if (RAND.nextInt(100) < 10) {
			return new NormalCell(MineUtils.COAL);
		}
		if (RAND.nextInt(100) < 5) {
			return new NormalCell(MineUtils.GOLD);
		}
		if (RAND.nextInt(100) < 5) {
			return new NormalCell(MineUtils.IRON);
		}
		return new NormalCell(MineUtils.STONE);
	}

}
