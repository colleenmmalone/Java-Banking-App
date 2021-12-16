package com.driver;

public class PrintLogo {
	public final String ANSI_PURPLE = "\u001B[35m"; //trying some new colors
	public final String ANSI_GREEN = "\u001B[32m";
	public final String ANSI_RED = "\u001B[31m";
	public final String ANSI_BLUE = "\u001B[36m";
	public final String ANSI_RESET = "\u001B[0m";
	
	public void printLogo() {
		System.out.println("Hello! And welcome to\n\n");
		System.out.println(ANSI_PURPLE+"The International Bank of"); //fancy logo
		System.out.println("*************************************************************************************************");
		System.out.println("*												*");
		System.out.println("*	| |	===	===	| |	===		===	===	===	| |	===	*");
		System.out.println("*	| |	| |	|	| |	|		| |	| |	|	| |	|	*");
		System.out.println("*	===	| |	|	| |	===		===	| |	|	| |	===	*");
		System.out.println("*	| |	| |	|	| |	  |		| 	| |	|	| |	  |	*");
		System.out.println("*	| |	===	===	===	===		| 	===	===	===	===	*");
		System.out.println("*												*");
		System.out.println("*************************************************************************************************");
		System.out.println(ANSI_RESET+"\n\nThank you for using Bank of Hocus Pocus for all of your magical banking needs!\n\n");
		
	}

}
