package ru.itis.hauntedo.simbirtest.utils.consts;

public class ApiConst {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

    public static final String PHONE_REGEX = "^(\\+7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$";

    public static final String BEARER = "Bearer ";
    public static final String ROLE = "ROLE";
}
