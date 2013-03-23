package com.gamealoon.tests;

import models.Platform;

import org.junit.Test;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.assertThat;

public class PlatformTest {

	@Test
	public void create(){
		running(fakeApplication(),new Runnable() {
			
			@Override
			public void run() {
				Platform playstation3= new Platform();				
				playstation3.id=1;
				playstation3.title="Playstation 3";
				playstation3.manufacturer="Sony";
				playstation3.description="Sony's third generation in Playstation series powered by cell";
				playstation3.save();
				assertThat(playstation3.id).isNotNull();
			}
		});
	}
}
