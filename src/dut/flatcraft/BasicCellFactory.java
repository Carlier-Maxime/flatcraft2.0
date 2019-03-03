package dut.flatcraft;

public class BasicCellFactory implements CellFactory {

	@Override
	public Cell createSky() {
		return new EmptyCell(MineUtils.getImage("ice"));
	}

	@Override
	public Cell createGrass() {
		return new NormalCell(MineUtils.getImage("grass"));
	}

	@Override
	public Cell createSoil() {
		return new NormalCell(MineUtils.getImage("dirt"));
	}

	@Override
	public Cell createTree() {
		return new NormalCell(MineUtils.getImage("tree"));
	}

	@Override
	public Cell createLeaves() {
		throw new UnsupportedOperationException();
	}

}
