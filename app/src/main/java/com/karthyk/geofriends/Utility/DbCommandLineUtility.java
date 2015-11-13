package com.karthyk.geofriends.Utility;

import java.util.HashMap;

public class DbCommandLineUtility {

  public static String createTable(String TableName, HashMap<String, String> columnNames) {
    StringBuilder builder = new StringBuilder();
    builder.append("CREATE TABLE ");
    builder.append(TableName);
    builder.append("(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL");
    for (String key : columnNames.keySet()) {
      builder.append(",");
      builder.append(" ");
      builder.append(key);
      builder.append(" ");
      builder.append(columnNames.get(key));
    }
    builder.append(")");
    return builder.toString();
  }
}
