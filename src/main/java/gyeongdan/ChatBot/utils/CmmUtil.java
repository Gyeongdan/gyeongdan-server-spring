package gyeongdan.ChatBot.utils;

public class CmmUtil {

    /**
     * 문자열 값이 null이면 빈 문자열("")을 반환하고, 그렇지 않으면 원래 문자열을 반환합니다.
     * @param str null 값을 검사할 문자열
     * @return null이 아니면 원래 문자열, null이면 빈 문자열
     */
    public static String nvl(String str) {
        return (str == null) ? "" : str;
    }

    /**
     * 문자열 값이 null이면 기본값을 반환하고, 그렇지 않으면 원래 문자열을 반환합니다.
     * @param str null 값을 검사할 문자열
     * @param defaultValue str이 null일 때 반환할 기본값
     * @return null이 아니면 원래 문자열, null이면 기본값
     */
    public static String nvl(String str, String defaultValue) {
        return (str == null) ? defaultValue : str;
    }
}
