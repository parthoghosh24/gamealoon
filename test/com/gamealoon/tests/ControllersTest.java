package com.gamealoon.tests;

import java.util.Date;
import org.junit.Test;

import com.gamealoon.controllers.GloonAPI;
import com.gamealoon.utility.Utility;

import play.mvc.Result;
import static org.fest.assertions.Assertions.*;

public class ControllersTest {


	/**
	 * This method tests all Utility methods
	 * 
	 */
	@Test
	public void testUtilityMethods() {
		System.out.println(Utility.convertDateToString(new Date()));
//		assertThat(Utility.convertDateToString(new Date())).isEqualTo("2013-04-04");

		System.out.println(Utility.generateHexaString(201303302));

	}

	@Test
	public void testGetGame()
	{
		
		Result result= GloonAPI.getGame("Max Payne 3");
		assertThat(result).isNotNull();
	}
}
