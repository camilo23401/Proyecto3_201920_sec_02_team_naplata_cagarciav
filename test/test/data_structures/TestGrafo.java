package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.Stack;

public class TestGrafo {
	private GrafoNoDirigido<Integer,String> grafo;
	private static int TAMANO=10000;

	@Before
	public void setUp1() {
		grafo=new GrafoNoDirigido<Integer,String>(TAMANO);
	}
	@Test
	public void testAgregarBuscarModificarVertice() {
		grafo.addVertex(0, "Nestor");
		grafo.addVertex(1, "Camilo");
		grafo.addVertex(2, "Alejandro");
		grafo.addVertex(3, "Luis");
		grafo.addVertex(4, "Jesse");
		grafo.addVertex(5, "Nelson");
		assertEquals(6, grafo.V());
		assertEquals("Nestor",grafo.getInfoVertex(0));
		assertEquals("Camilo",grafo.getInfoVertex(1));
		assertEquals("Alejandro",grafo.getInfoVertex(2));
		assertEquals("Luis",grafo.getInfoVertex(3));
		grafo.setInfoVertex(0, "Daniel");
		assertEquals("Daniel",grafo.getInfoVertex(0));
		assertNotEquals("Nestor",grafo.getInfoVertex(0));
		grafo.setInfoVertex(1, "Laura");
		assertEquals("Laura",grafo.getInfoVertex(1));
		assertNotEquals("Camilo",grafo.getInfoVertex(1));
	}

	@Test
	public void testAgregarModificarArco() {
		grafo.addVertex(0, "Nestor");
		grafo.addVertex(1, "Camilo");
		grafo.addVertex(2, "Alejandro");
		grafo.addEdge(0, 1, 20.00);
		grafo.addEdge(0, 2, 30.00);
		grafo.addEdge(2, 1, 40.00);
		assertEquals(3, grafo.E());
		assertEquals(20.00, grafo.getCostArc(0, 1), 1);
		assertEquals(30.00, grafo.getCostArc(0, 2), 1);
		assertEquals(40.00, grafo.getCostArc(2, 1), 1);
		grafo.setCostArc(0, 1, 45.00);
		grafo.setCostArc(2, 1, 266.00);
		assertEquals(45.00, grafo.getCostArc(0, 1), 1);
		assertEquals(266.00, grafo.getCostArc(1, 2), 1);

	}
	@Test
	public void testAdyacente() {
		grafo.addVertex(0, "Nestor");
		grafo.addVertex(1, "Camilo");
		grafo.addVertex(2, "Alejandro");
		grafo.addVertex(3, "Luis");
		grafo.addVertex(4, "Jesse");
		grafo.addVertex(5, "Nelson");
		grafo.addEdge(0, 1, 20.00);
		grafo.addEdge(0, 2, 30.00);
		grafo.addEdge(0, 3, 40.00);
		grafo.addEdge(0, 4, 40.00);
		grafo.addEdge(0, 5, 40.00);
		assertEquals(5, grafo.E());
		ArregloDinamico<Integer>adj=(ArregloDinamico<Integer>) grafo.adje(0);
		for (int i = 0; i < adj.darTamano(); i++) {
			assertEquals(i+1, adj.darElementoPos(i).intValue());
		}

	}

	@Test
	public void testdfs() {
		grafo.addVertex(0, "Nestor");
		grafo.addVertex(1, "Camilo");
		grafo.addVertex(2, "Alejandro");
		grafo.addVertex(3, "Luis");
		grafo.addVertex(4, "Jesse");
		grafo.addVertex(5, "Nelson");
		grafo.addVertex(6, "nut");
		grafo.addVertex(7, "nut");
		grafo.addEdge(0, 1, 20.00);
		grafo.addEdge(0, 6, 40.00);
		grafo.addEdge(0, 2, 30.00);
		grafo.addEdge(0, 3, 40.00);
		grafo.addEdge(0, 4, 40.00);
		grafo.addEdge(0, 5, 40.00);
		assertEquals(6, grafo.E());
	


	}

	@Test
	public void testgetcc() {
		grafo.uncheck();
		grafo.addVertex(0, "Nestor");
		grafo.addVertex(1, "Camilo");
		grafo.addVertex(2, "Alejandro");
		grafo.addVertex(3, "Luis");
		grafo.addVertex(4, "Jesse");
		grafo.addVertex(5, "Nelson");
		grafo.addVertex(6, "nut");
		grafo.addVertex(7, "nut");
		grafo.addVertex(9, "nut");
		grafo.addEdge(0, 1, 20.00);
		grafo.addEdge(0, 6, 40.00);
		grafo.addEdge(0, 2, 30.00);
		grafo.addEdge(0, 3, 40.00);
		grafo.addEdge(0, 4, 40.00);
		grafo.addEdge(0, 5, 40.00);
		grafo.uncheck();
		assertEquals(3, grafo.CC());


	}




}

