package dut.flatcraft;

public class BasicCellFactory implements CellFactory {

	@Override
	public Cell createSky() {
		return new EmptyCell(MineUtils.ICE);
	}

	@Override
	public Cell createGrass() {
		return new NormalCell(MineUtils.GRASS);
	}

	@Override
	public Cell createSoil() {
		return new NormalCell(MineUtils.DIRT);
	}

	@Override
	public Cell createTree() {
		return new NormalCell(MineUtils.TREE);
	}

	@Override
	public Cell createLeaves() {
		throw new UnsupportedOperationException();
	}

}
