package com.todo.controllers;

import com.google.gson.Gson;

public class AbstractController {
  protected static final String UUID_PATTERN =
      "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";
  protected Gson jsonSerializer = new Gson();
}
