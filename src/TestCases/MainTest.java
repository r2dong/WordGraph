package TestCases;

import java.util.ArrayList;
import standard.WordGraph;

public class MainTest {

	public static void main(String[] args) {
		ArrayList<String> s = new ArrayList<String>();
		s.add("pain");
		s.add("gain");
		s.add("pan");
		s.add("span");
		s.add("gait");
		s.add("wait");
		WordGraph w = new WordGraph(s);
		ArrayList<String> p = w.shortestPath("pain", "span");
		for (int i = 0; i < p.size(); i++)
			System.out.println(p.get(i));
	}

}
