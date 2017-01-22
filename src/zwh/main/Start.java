package zwh.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import zwh.map.MapChecker;
import zwh.map.SokobanMap;
import zwh.solver.ISokobanSolver;
import zwh.solver.ViolenceConcurrentSolver;
import zwh.solver.ViolentSingleSolver;
import zwh.solver.judge.DefaultJudger;

public class Start {

    public static void main(String[] args) {
	run();
//	runWithAverageTime();
    }

    private static void run() {
	SokobanMap map = readMap();
	ISokobanSolver solver = new ViolenceConcurrentSolver(new DefaultJudger());
	// ISokobanSolver solver = new ViolentSingleSolver(new DefaultJudger());
	if (MapChecker.isValidMap(map)) {
	    try {
		solver.solve(map);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    SokobanMap resMap = solver.getSolvedMap();
	    System.out.println("Time: " + solver.getSolvedTime());
	    if (resMap != null) {
		System.out.println(resMap.path);
	    } else {
		System.out.println("can not find the way to solve the SokobanMap");
	    }
	} else {
	    System.out.println("this is not valid sokoban map");
	}
    }

    private static void runWithAverageTime() {
	long time = 0;
	final int count = 100;
	int successCount = 0;
	SokobanMap map = readMap();
	for (int i = 0; i < count; i++) {
	    // ISokobanSolver solver = new ViolenceConcurrentSolver(new
	    // DefaultJudger());
	    ISokobanSolver solver = new ViolentSingleSolver(new DefaultJudger());
	    if (MapChecker.isValidMap(map)) {
		try {
		    solver.solve(map);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		SokobanMap resMap = solver.getSolvedMap();
		System.out.println("Time: " + solver.getSolvedTime());
		if (resMap != null) {
		    time += solver.getSolvedTime();
		    System.out.println(resMap.path);
		    successCount++;
		} else {
		    System.out.println("can not find the way to solve the SokobanMap");
		}
	    } else {
		System.out.println("this is not valid sokoban map");
	    }
	}
	System.out.println("Average: " + time / successCount);
    }

    private static SokobanMap readMap() {
	Scanner scanner = new Scanner(System.in);
	List<String> mapList = new ArrayList<String>();
	while (true) {
	    String temp = scanner.nextLine();
	    if (temp.equals("end")) {
		break;
	    }
	    mapList.add(temp);
	}
	return new SokobanMap(mapList, "");
    }
}
