package org.example;

public enum CallType {
    OUTGOING("Исходящий","01"),
    INCOMING("Входящий","02");
    String name;
    String code;
    CallType(String name, String code){
        this.code = code;
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public String getCode(){
        return code;
    }
}
