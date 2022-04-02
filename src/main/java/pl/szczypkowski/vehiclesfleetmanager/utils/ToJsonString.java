package pl.szczypkowski.vehiclesfleetmanager.utils;

public abstract class ToJsonString
{
    public static String toJsonString(String value)
    {
        return "\"" + value + "\"";
    }
}
