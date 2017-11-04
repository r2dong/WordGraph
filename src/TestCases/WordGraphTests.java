package TestCases;

import standard.Vertex;
import standard.WordGraph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class WordGraphTests {
	
	
	
	@Test
	public void testHasEdge() {
		Vertex v1 = new Vertex("abc");
		Vertex v2 = new Vertex("ab");
		Vertex v3 = new Vertex("bc");
		Vertex v4 = new Vertex("ac");
		Vertex v5 = new Vertex("abcd");
		Vertex v6 = new Vertex("bcd");
		Vertex v7 = new Vertex("abcdefg");
		Vertex v8 = new Vertex("");
		Vertex v9 = new Vertex("aaa");
		Vertex v10 = new Vertex("bbb");
		
		assertEquals(v1.hasEdge(v2), true);
		assertEquals(v1.hasEdge(v3), true);
		assertEquals(v1.hasEdge(v4), true);
		assertEquals(v1.hasEdge(v5), true);
		assertEquals(v1.hasEdge(v6), false);
		assertEquals(v1.hasEdge(v7), false);
		assertEquals(v1.hasEdge(v8), false);
		assertEquals(v1.hasEdge(v9), false);
		assertEquals(v1.hasEdge(v10), false);
		
		assertEquals(v2.hasEdge(v1), true);
		assertEquals(v3.hasEdge(v1), true);
		assertEquals(v4.hasEdge(v1), true);
		assertEquals(v5.hasEdge(v1), true);
		assertEquals(v6.hasEdge(v1), false);
		assertEquals(v7.hasEdge(v1), false);
		assertEquals(v8.hasEdge(v1), false);
		assertEquals(v9.hasEdge(v1), false);
		assertEquals(v10.hasEdge(v1), false);
		
	}
	
	@Test
	public void testShortestPath() {
		ArrayList<String> s = new ArrayList<String>();
		s.add("pain");
		s.add("gain");
		s.add("pan");
		s.add("span");
		s.add("gait");
		s.add("wait");
		WordGraph w = new WordGraph(s);
		ArrayList<String> p1 = w.shortestPath("pain", "gain");
		ArrayList<String> p2 = w.shortestPath("pain", "wait");
		ArrayList<String> p3 = w.shortestPath("wait", "span");
		ArrayList<String> p4 = w.shortestPath("span", "wait");
		
		assertEquals(p1.get(0), "pain");
		assertEquals(p1.get(1), "gain");
		
		assertEquals(p2.get(0), "pain");
		assertEquals(p2.get(1), "gain");
		assertEquals(p2.get(2), "gait");
		assertEquals(p2.get(3), "wait");
		
		assertEquals(p3.get(0), "wait");
		assertEquals(p3.get(1), "gait");
		assertEquals(p3.get(2), "gain");
		assertEquals(p3.get(3), "pain");
		assertEquals(p3.get(4), "pan");
		assertEquals(p3.get(5), "span");
		
		assertEquals(p4.get(5), "wait");
		assertEquals(p4.get(4), "gait");
		assertEquals(p4.get(3), "gain");
		assertEquals(p4.get(2), "pain");
		assertEquals(p4.get(1), "pan");
		assertEquals(p4.get(0), "span");
		
		ArrayList<String> s2 = new ArrayList<String>();
		s2.add("a");
		s2.add("b");
		s2.add("c");
		s2.add("ddddddd");
		s2.add("dddddd");
		s2.add("ddddd");
		s2.add("zzzzz");
		s2.add("zzzz");
		s2.add("zzz");
		WordGraph w2 = new WordGraph(s2);
		ArrayList<String> p5 = w2.shortestPath("a", "c");
		ArrayList<String> p6 = w2.shortestPath("a", "zzz");
		ArrayList<String> p7 = w2.shortestPath("zzz", "zzzzz");
		ArrayList<String> p8 = w2.shortestPath("a", "ddddd");
		
		assertEquals(p5.get(0), "a");
		assertEquals(p5.get(1), "c");
		
		assertEquals(p6.size(), 0);
		
		assertEquals(p7.get(0), "zzz");
		assertEquals(p7.get(1), "zzzz");
		assertEquals(p7.get(2), "zzzzz");
		
		assertEquals(p8.size(), 0);
		
 	}
	
	@Test
	public void testNumberOfComponents() {
		ArrayList<String> s = new ArrayList<String>();
		s.add("pain");
		s.add("gain");
		s.add("pan");
		s.add("span");
		s.add("gait");
		s.add("wait");
		WordGraph w = new WordGraph(s);
		assertEquals(w.numberOfComponents(), 1);
		
		ArrayList<String> s2 = new ArrayList<String>();
		s2.add("a");
		s2.add("b");
		s2.add("c");
		s2.add("ddddddd");
		s2.add("dddddd");
		s2.add("ddddd");
		s2.add("zzzzz");
		s2.add("zzzz");
		s2.add("zzz");
		WordGraph w2 = new WordGraph(s2);
		assertEquals(w2.numberOfComponents(), 3);
	}
}
