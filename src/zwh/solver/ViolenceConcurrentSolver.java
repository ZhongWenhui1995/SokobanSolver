package zwh.solver;

import java.util.Date;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import zwh.map.MapDirection;
import zwh.map.SokobanMap;
import zwh.move.ManMover;
import zwh.solver.judge.IJudger;

/**
 * 多线程求解器
 * @author zwh
 *
 */
public class ViolenceConcurrentSolver implements ISokobanSolver {

    private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();
    private final int POOL_SIZE = CPU_NUM;
    private ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
    private Semaphore aliveThread = new Semaphore(POOL_SIZE);
    private Lock lock = new ReentrantLock();
    private SokobanMap solvedMap = null;
    private IJudger judger;
    private long time = 0;

    /**
     * 清空缓存
     */
    public void clear(){
	solvedMap = null;
	pool = Executors.newFixedThreadPool(POOL_SIZE);
	aliveThread = new Semaphore(POOL_SIZE);
    }
    
    @Override
    public long getSolvedTime(){
	return this.time;
    }
    
    public ViolenceConcurrentSolver(IJudger judger) {
	this.judger = judger;
    }

    /**
     * 终止所有线程的执行
     */
    public void shutdown() {
	try{
	    pool.shutdownNow();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    @Override
    public void solve(final SokobanMap map) throws Exception{
	this.clear();
	String startTime = String.valueOf(new Date().getTime());
	System.out.println("ViolenceConcurrentSolver: START TIME>" + startTime);
	if (this.judger.isSolved(map)) {
	    this.solvedMap = map;
	    return;
	}
	if (this.solvedMap == null) {
	    // executeRecursiveFindPath(map);
	    executeIterateFindPath(map);
	    synchronized (lock) {
		try {
		    lock.wait();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}
	String endTime = String.valueOf(new Date().getTime());
	System.out.println("ViolenceConcurrentSolver: END TIME>" + endTime);
	this.time = Long.valueOf(endTime) - Long.valueOf(startTime);
	pool.shutdownNow();
	this.judger.clear();
    }

    /**
     * 迭代版
     * @param map
     */
    private void iterateFindPath(SokobanMap map) {
	Stack<SokobanMap> maps = new Stack<SokobanMap>();
	maps.add(map);
	while (!maps.isEmpty()) {
	    map = maps.pop();
	    SokobanMap nextMap = this.getNextMap(map);
	    while (nextMap != null && this.solvedMap == null) {
		if (this.judger.isSolved(nextMap)) {
		    this.solvedMap = nextMap;
		    synchronized (lock) {
			lock.notify();
		    }
		    return;
		}
		if (!executeIterateFindPath(nextMap)) {
		    maps.add(nextMap);
		}
		nextMap = this.getNextMap(map);
	    }
	}
    }

    /**
     * 迭代版，用于决定是否创建新线程执行
     * @param map
     * @return true（创建新线程）
     */
    private boolean executeIterateFindPath(final SokobanMap map) {
	if (aliveThread.tryAcquire()) {
	    pool.execute(new Runnable() {
		@Override
		public void run() {
		    iterateFindPath(map);
		    aliveThread.release();
		}
	    });
	    return true;
	}
	return false;
    }

    /**
     * 递归版
     * @param map
     */
    private void recursiveFindPath(SokobanMap map) {
	SokobanMap nextMap = this.getNextMap(map);
	while (nextMap != null && this.solvedMap == null) {
	    if (this.judger.isSolved(nextMap)) {
		this.solvedMap = nextMap;
		synchronized (lock) {
		    lock.notify();
		}
		return;
	    }
	    executeRecursiveFindPath(nextMap);
	    nextMap = this.getNextMap(map);
	}
    }

    /**
     * 用于决定是创建新线程还是在原来线程中执行
     * @param map
     */
    private void executeRecursiveFindPath(final SokobanMap map) {
	if (aliveThread.tryAcquire()) {
	    pool.execute(new Runnable() {
		@Override
		public void run() {
		    recursiveFindPath(map);
		    aliveThread.release();
		}
	    });
	} else {
	    recursiveFindPath(map);
	}
    }

    /**
     * 返回下一个可行的地图，如果没有则返回null
     * 
     * @param map
     * @return
     */
    public SokobanMap getNextMap(SokobanMap map) {
	for (int direction : MapDirection.directions) {
	    SokobanMap nextMap = ManMover.moveOneStep(map, direction);
	    if (!this.judger.isPathed(nextMap)) {
		return nextMap;
	    }
	}
	return null;
    }

    @Override
    public SokobanMap getSolvedMap() {
	return this.solvedMap;
    }
}
