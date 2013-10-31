package com.gamealoon.models;

public enum Genre {

	Action("Action"),
	Adventure("Adventure"),
	ActionAdventure("Action-Adventure"),
	Rpg("RPG"),
	Fps("First Person Shooter"),
	Tps("Third Person Shooter"),
	SurvivalHorror("Survival Horror"),
	Sports("Sports"),
	Strategy("Strategy"),
	Casual("Casual"),
	Puzzle("Puzzle"),
	Racing("Racing"),			
	Simulation("Simulation");
	
	private String text;
	Genre(String text)
	{
		this.text=text;
	}
	
	public String toString()
	{
		return text;
	}
}
