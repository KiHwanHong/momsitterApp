package momsitter.support.dto;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.owasp.encoder.Encode;

public class XssEncoder {

  private static final String[] DEFAULT_ENCODING_TAGS = Lists
      .newArrayList("script", "iframe", "object", "embed", "applet", "eval")
      .toArray(new String[]{});

  private final String rawContents;
  private final boolean removeComment;

  public XssEncoder(@Nullable final String rawContents) {
    this(rawContents, true);
  }

  public XssEncoder(@Nullable final String rawContents, boolean removeComment) {
    this.rawContents = rawContents == null ? "" : rawContents;
    this.removeComment = removeComment;
  }

  public String encodeAll() {
    String result = Encode.forHtml(rawContents);
    return getReturnValue(result);
  }

  public String encodeDefaults(String... excludes) {
    List<String> removeTags = new ArrayList<>(Arrays.asList(DEFAULT_ENCODING_TAGS));
    Arrays.asList(excludes).forEach(removeTags::remove);

    String result = encodeTagsOf(removeTags.toArray(new String[]{}));
    return getReturnValue(result);
  }

  public String encodeTagsOf(String... tags) {
    String temporaryString = rawContents;
    for (String tag : tags) {
      String startTag = "<" + tag;
      String endTag = "</" + tag;

      temporaryString =
          temporaryString.replaceAll("(?i)" + startTag, Encode.forHtml(startTag))
              .replaceAll("(?i)" + endTag, Encode.forHtml(endTag));
    }

    return getReturnValue(temporaryString);
  }

  public String encodeComment() {
    return getReturnValue(this.rawContents);
  }

  private String getReturnValue(String contents) {
    return removeComment ? removeComment(contents) : contents;
  }

  private static String removeComment(String str) {
    final String empty = "";

    return str.replaceAll("<!--[^>]*-->", empty)
        .replaceAll("(?s)/\\*.*?\\*/", empty);
  }
}
