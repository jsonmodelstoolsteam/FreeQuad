package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LangUtil {

    public static String currentLang = "ru_RU";

    private static Map<String, Map<String, String>> langMaps = new HashMap<>();

    private static Map<String, String> langMap() {
        return langMaps.computeIfAbsent(currentLang, langFile -> {
            Map<String, String> r = new HashMap<>();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(Main.instance.helper.getResource("freequad/lang/" + langFile + ".lang")))) {
                br.lines().forEach(line -> {
                    int separatorIndex = line.indexOf("=");
                    if (separatorIndex != -1)
                        r.put(line.substring(0, separatorIndex), line.substring(separatorIndex + 1));
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return r;
        });
    }

    public static String translateToLocal(String label) {
        return langMap().getOrDefault(label, "");
    }
}
