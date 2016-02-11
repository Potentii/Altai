package util;

import model.Host;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An url pattern must have just one {@code <TITLE>} tag, and 0 or more {@code <IGNORE>} tags.
 * <p>
 * All characters that doesn't change must be in the pattern.
 * @author Guilherme Reginaldo
 * @since 09/02/2016
 */
public class UrlPatternUtil {
    public final static String TITLE_TAG = "<TITLE>";
    public final static String IGNORE_TAG = "<IGNORE>";


    @Nullable
    public Host getHost(List<Host> hostList, final String url){
        for (Host host : hostList) {
            String urlPattern = host.getUrlPattern();

            // *Getting the groups with IGNORE_TAG or TITLE_TAG on the urlPattern:
            Matcher m1 = Pattern.compile("(" + IGNORE_TAG + "|" + TITLE_TAG + ")").matcher(urlPattern);
            // *Then transforming it in a regular expression by replacing the tags with '(.*)':
            String f1 = m1.replaceAll("(.*)");
            // *Applying the generated regex on the actual URL:
            Matcher m2 = Pattern.compile(f1).matcher(url);

            // *If the regex matches, then return the current host:
            if(m2.matches()){
                return host;
            }
        }
        return null;
    }

    public String getTitle(@Nullable Host host, String url){
        if(host == null){
            return "";
        }

        String title = "";
        String urlPattern = host.getUrlPattern();
        int scanLastId = 0;

        String[] splitPattern = urlPattern.split("(" + IGNORE_TAG + ")");
        for (String split : splitPattern) {
            if(!split.isEmpty()) {
                if(!split.contains(TITLE_TAG)){
                    scanLastId = url.indexOf(split, scanLastId) + split.length();
                } else{
                    String[] titleSplitArray = split.split(TITLE_TAG, 2);
                    int titleStart = url.indexOf(titleSplitArray[0], scanLastId) + titleSplitArray[0].length();
                    int titleEnd = titleSplitArray[1].isEmpty()
                            ? url.length()
                            : url.indexOf(titleSplitArray[1], titleStart);

                    title = url.substring(titleStart, titleEnd).replace("_", " ").trim();

                }
            }
        }
        return title;
    }
}
