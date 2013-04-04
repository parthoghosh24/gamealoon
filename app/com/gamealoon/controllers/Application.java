package com.gamealoon.controllers;


import play.mvc.*;

public class Application extends Controller {
  
  public static Result index() {
    return ok(com.gamealoon.views.html.index.render("Gamealoon is coming..."));	  
  }
  
}