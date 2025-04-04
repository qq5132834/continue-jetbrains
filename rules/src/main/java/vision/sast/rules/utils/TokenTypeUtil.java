package vision.sast.rules.utils;

import org.fife.ui.rsyntaxtextarea.TokenTypes;

import java.util.HashMap;
import java.util.Map;

public class TokenTypeUtil {
    public static Map<Integer, String> tokenMap = new HashMap<>();
    static {
        tokenMap.put(TokenTypes.NULL, "NULL");
        tokenMap.put(TokenTypes.COMMENT_EOL, "NULL");
        tokenMap.put(TokenTypes.COMMENT_MULTILINE, "NULL");
        tokenMap.put(TokenTypes.COMMENT_DOCUMENTATION, "NULL");
        tokenMap.put(TokenTypes.COMMENT_KEYWORD, "NULL");

        tokenMap.put(TokenTypes.COMMENT_MARKUP, "NULL");
        tokenMap.put(TokenTypes.RESERVED_WORD, "NULL");
        tokenMap.put(TokenTypes.RESERVED_WORD_2, "NULL");
        tokenMap.put(TokenTypes.FUNCTION, "NULL");
        tokenMap.put(TokenTypes.LITERAL_BOOLEAN, "NULL");

        tokenMap.put(TokenTypes.LITERAL_NUMBER_DECIMAL_INT, "NULL");
        tokenMap.put(TokenTypes.LITERAL_NUMBER_FLOAT, "NULL");
        tokenMap.put(TokenTypes.LITERAL_NUMBER_HEXADECIMAL, "NULL");
        tokenMap.put(TokenTypes.LITERAL_STRING_DOUBLE_QUOTE, "NULL");
        tokenMap.put(TokenTypes.LITERAL_CHAR, "NULL");

        tokenMap.put(TokenTypes.LITERAL_BACKQUOTE, "NULL");
        tokenMap.put(TokenTypes.DATA_TYPE, "NULL");
        tokenMap.put(TokenTypes.VARIABLE, "NULL");
        tokenMap.put(TokenTypes.REGEX, "NULL");
        tokenMap.put(TokenTypes.ANNOTATION, "NULL");

        tokenMap.put(TokenTypes.IDENTIFIER, "NULL");
        tokenMap.put(TokenTypes.WHITESPACE, "NULL");
        tokenMap.put(TokenTypes.SEPARATOR, "NULL");
        tokenMap.put(TokenTypes.OPERATOR, "NULL");
        tokenMap.put(TokenTypes.PREPROCESSOR, "NULL");

        tokenMap.put(TokenTypes.MARKUP_TAG_DELIMITER, "NULL");
        tokenMap.put(TokenTypes.MARKUP_TAG_NAME, "NULL");
        tokenMap.put(TokenTypes.MARKUP_TAG_ATTRIBUTE, "NULL");
        tokenMap.put(TokenTypes.MARKUP_TAG_ATTRIBUTE_VALUE, "NULL");
        tokenMap.put(TokenTypes.MARKUP_COMMENT, "NULL");

        tokenMap.put(TokenTypes.MARKUP_DTD, "NULL");
        tokenMap.put(TokenTypes.MARKUP_PROCESSING_INSTRUCTION, "NULL");
        tokenMap.put(TokenTypes.MARKUP_CDATA_DELIMITER, "NULL");
        tokenMap.put(TokenTypes.MARKUP_CDATA, "NULL");
        tokenMap.put(TokenTypes.MARKUP_ENTITY_REFERENCE, "NULL");

        tokenMap.put(TokenTypes.ERROR_IDENTIFIER, "NULL");
        tokenMap.put(TokenTypes.ERROR_NUMBER_FORMAT, "NULL");
        tokenMap.put(TokenTypes.ERROR_STRING_DOUBLE, "NULL");
        tokenMap.put(TokenTypes.ERROR_CHAR, "NULL");
        tokenMap.put(TokenTypes.DEFAULT_NUM_TOKEN_TYPES, "NULL");

    }
}
