package dut.flatcraft;

public interface CellFactory {

	Cell createSky();
	
	Cell createGrass();
	
	Cell createSoil();
	
	Cell createTree();
}
