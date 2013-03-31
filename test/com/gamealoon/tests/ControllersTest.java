package com.gamealoon.tests;

import java.util.Date;
import org.junit.Test;

import play.mvc.Result;
import controllers.GloonAPI;
import utility.Utility;
import static org.fest.assertions.Assertions.*;

public class ControllersTest {


	/**
	 * This method tests all Utility methods
	 * 
	 */
	@Test
	public void testUtilityMethods() {
		System.out.println(Utility.convertDateToString(new Date()));
		assertThat(Utility.convertDateToString(new Date())).isEqualTo(
				"2013-03-30");

		System.out.println(Utility.generateHexaString(201303302));

	}

	@Test
	public void testGetGame()
	{
		
		Result result= GloonAPI.getGame("Max Payne 3");
		assertThat(result).isNotNull();
	}
}
