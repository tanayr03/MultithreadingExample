package com.examplefoobar.utils;

import com.examplefoobar.utils.PoorGroup.Member;

public class Run {

	public static void main(String[] args) {
		testHashset();
	}

	// one member single thread.(10 rows)
	private static void fileWriteScenario1() {
		PoorGroup pgt = new PoorGroup(String.valueOf(Math.random()));
		String outputFilePrimary = "out1";
		String outputFileSecondary = "out2";
		Runnable run1 = new Runnable() {
			@Override
			public void run() {
				Member member = pgt.new Member("1", 10);
				try {
					pgt.addMember(member);
					pgt.startLoggingMemberList10Times(outputFilePrimary, outputFileSecondary);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(run1).start();
	}

	// 2 member single thread.(20 rows)
	private static void fileWriteScenario2() {
		PoorGroup pgt = new PoorGroup(String.valueOf(Math.random()));
		String outputFilePrimary = "out1";
		String outputFileSecondary = "out2";
		Runnable run1 = new Runnable() {
			@Override
			public void run() {
				try {
					pgt.addMember(pgt.new Member("1", 10));
					pgt.addMember(pgt.new Member("2", 20));
					pgt.startLoggingMemberList10Times(outputFilePrimary, outputFileSecondary);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(run1).start();
	}

	// 1 member multiple thread.(number of thread*10 rows)
	private static void fileWriteScenario3() {
		PoorGroup pgt = new PoorGroup(String.valueOf(Math.random()));
		String outputFilePrimary = "out1";
		String outputFileSecondary = "out2";
		Runnable run1 = new Runnable() {
			@Override
			public void run() {
				try {
					pgt.addMember(pgt.new Member("1", 10));
					pgt.startLoggingMemberList10Times(outputFilePrimary, outputFileSecondary);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		for (int i = 0; i < 1000; i++) {
			new Thread(run1).start();
		}
	}

	// multiple members multiple thread.(number of thread*10*numberof members= rows)
	private static void fileWriteScenario4() {
		PoorGroup pgt = new PoorGroup(String.valueOf(Math.random()));
		String outputFilePrimary = "out1";
		String outputFileSecondary = "out2";
		for (int i = 0; i < 10; i++) {
			Runnable run = new Runnable() {
				@Override
				public void run() {
					try {
						pgt.addMember(pgt.new Member(String.valueOf(1), 20));
						pgt.startLoggingMemberList10Times(outputFilePrimary, outputFileSecondary);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			new Thread(run).start();
		}
	}

	// 1 member multiple thread.(number of thread*10* number of groups= rows)
	private static void fileWriteStop() {
		PoorGroup pgt = new PoorGroup(String.valueOf(Math.random()));
		String outputFilePrimary = "out1";
		String outputFileSecondary = "out2";
		String outputFilePrimaryNew = "out3";
		String outputFileSecondaryNew = "out4";
		Runnable run1 = new Runnable() {
			@Override
			public void run() {
				Member member = pgt.new Member("1", 10);
				try {
					pgt.addMember(member);
					pgt.addMember(pgt.new Member("2", 20));
					pgt.startLoggingMemberList10Times(outputFilePrimary, outputFileSecondary);
					Thread.sleep(800);
					pgt.stopPrintingMemberList();
					pgt.startLoggingMemberList10Times(outputFilePrimaryNew, outputFileSecondaryNew);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		// this should print 20, 000 rows, but lesser rows will be printed because of
		// stop print .
		for (int i = 0; i < 1000; i++) {
			new Thread(run1).start();
		}

	}

	// should store unique memberIdsonly
	private static void testHashset() {
		PoorGroup pgt = new PoorGroup(String.valueOf(Math.random()));
		String outputFilePrimary = "out1";
		String outputFileSecondary = "out2";
		Runnable run1 = new Runnable() {
			@Override
			public void run() {
				Member member = pgt.new Member("1", 10);
				try {
					pgt.addMember(member);
					pgt.startLoggingMemberList10Times(outputFilePrimary, outputFileSecondary);
					Thread.sleep(800);
					pgt.stopPrintingMemberList();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable run2 = new Runnable() {
			@Override
			public void run() {
				try {
					pgt.addMember(pgt.new Member("1", 20));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pgt.startLoggingMemberList10Times(outputFilePrimary, outputFileSecondary);
			}
		};
		// this should print 20, 000 rows as same meber was added twice
		for (int i = 0; i < 1000; i++) {
			new Thread(run1).start();
		}

	}

}
