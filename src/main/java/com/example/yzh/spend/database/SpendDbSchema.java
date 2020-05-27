package com.example.yzh.spend.database;

public class SpendDbSchema {
    public static final class SpendTable{
        public static final String NAME = "spends";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String COST = "cost";
            public static final String DETAIL = "detail";
            public static final String ADDRESS = "address";
        }
    }
}
