package com.zhou.schedule;

/**
 * this schedule is time schedule
 * that timing minute or hour or day, by minute particle
 *
 * @author 周俊宇
 */
public class TimeSchedule {

	/**
	 * this basic time particle
	 * user the particle what calculate all time slice
	 */
	private static long currentTime;

	private static short[][] timeSign = new short[60][1];

	/**
	 * I want this class to load at System initialization
	 */
	{
		init();
	}

	/**
	 * initialization the time particle
	 * that record time sate of system run
	 */
	private static void init() {
		currentTime = getCurrentTimeOfMinute();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("time-particle-reload-thread");
				while(true){
					long newCurrentTime = System.currentTimeMillis() / 1000 / 60;
					if (newCurrentTime - currentTime > 1){
						timeSign[(int) (currentTime % 60)][0] = 1;
						currentTime = newCurrentTime;
						if (currentTime % 60 == 0){
							timeSign = new short[60][1];
							timeSign[59][0] = 1;
						}
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}


	/**
	 * Determine if there data in the timeParticle;
	 * @param index
	 * @return
	 */
	public boolean isTimeBucketData(int index){
		if (timeSign[index][0] == 1){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * because use minute particle
	 * so this method let millisecond to minute
	 * @return long
	 */
	private static long getCurrentTimeOfMinute(){
		long res = System.currentTimeMillis() / 1000 / 60;
		return res;
	}
}
