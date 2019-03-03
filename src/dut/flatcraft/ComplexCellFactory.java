package dut.flatcraft;

public class ComplexCellFactory extends ModernCellFactory {

	@Override
	public Cell createSoil() {
		if (RAND.nextInt(10) < 7) {
			return new NormalCell(MineUtils.getImage("dirt"));
		}
		if (RAND.nextInt(100) < 10) {
			return new NormalCell(MineUtils.overlay("stone", "mineral_coal"));
		}
		if (RAND.nextInt(100) < 5) {
			return new NormalCell(MineUtils.overlay("stone", "mineral_gold"));
		}
		if (RAND.nextInt(100) < 5) {
			return new NormalCell(MineUtils.overlay("stone", "mineral_iron"));
		}
		return new NormalCell(MineUtils.getImage("stone"));
	}

}
