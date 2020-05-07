package de.pioneo.pioutils;

/**
 * This Class defines the regular expressions
 */
public class PioRegExpUtils {

    public static String REGEXP_SALUTATION_HEADER = "(^Herr[a-z]*|^Frau)((( )(und)( )(Herr[a-z]*|Frau))?)";
    public static String REGEXP_SALUTATION_NAME = "(([A-ZßÖÄÜ]{1}[a-zßöäü]+)(.)?( )?){1,}";
    public static String REGEXP_SALUTATION_STREET = "((([A-ZßÖÄÜ][a-zßöäü]+)(-?| ))+)(\\.?)( ?)([0-9]{1,4})([a-z]?)";
    public static String REGEXP_SALUTATION_PLACE = "([0-9]{1,5})( )((([A-ZßÖÄÜ][a-zßöäü]+)(.?)( ?))+)";
    public static String REGEXP_KWH = "([0-9]{0,3}(\\.|\\,)?)+((\\,|\\.)?([0-9][0-9]))( ?)([kK][.]?[wW][.]?[hH].?)";
    public static String REGEXP_PRICE = "([0-9]{0,3}(\\.|\\,)?)+((\\,|\\.)?([0-9][0-9]?))( ?)([€]|[Ee][.]?[Uu][.]?[Rr]?[.]?[Oo]?[.]?)";
    public static String REGEXP_DATE = "([0-9]{1,2}(.?)([0-9]{1,2}|.?[A-ZÜÖÄß][a-züöäß]+)(.?)([0-9]{4})(.?)(bis(.?)|\\-.?)[0-9]{1,2}(.?)([0-9]{2}|.?[A-ZÜÖÄß][a-züöäß]+)(.?)([0-9]{4})){1}";

    //Expressions for business cards
    public static String REGEXP_BC_COMPANY_STREET = "((([A-ZßÖÄÜ][a-zßöäü]+)(-?| ))+)(\\.?)( ?)([0-9]{1,4})([a-z]?)";
    public static String REGEXP_BC_COMPANY_PLACE = "([0-9]{4,5})( )((([A-ZßÖÄÜ][a-zßöäü]+)(.?)( ?))+)";
    public static String REGEXP_BC_SALUTATION_NAME = "^([A-Z]([a-z]*|\\\\.) *){1,2}([A-Z][a-z]+-?)+$";
    public static String REGEXP_BC_MOBIL = "( ?\\+?49 ?(\\(0\\))? ?1 ?[5-7] ?[1-9] ?)| ?(0 ?1 ?[5-7] ?[1-9] ?)";
    public static String REGEXP_BC_PHONE = "((\\+ ?|[00ooOO])[1-9]\\d{0,3}|([0oO] ?[1-9])|(\\([00ooOO]? ?[1-9][\\d ]*\\)))[\\d\\-/ ]*";
    public static String REGEXP_BC_HOMEPAGE = "(www.)?([a-z]|\\-|\\.)+(\\.)([a-z]+)";
    public static String REGEXP_BC_EMAIL = "([A-Za-z0-9]|\\-|\\.|\\,)+(\\@)([A-Za-z0-9]|\\-|\\.|\\,)+(\\.)([a-z]+)";

}
