package tech.hongjian.spider.recommend.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * JSON工具类（json库使用的是jackson）
 * 
 * 
 * @author xiahongjian 
 * @time   2018-05-25 16:30:51
 *
 */
public class JSONUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtil.class);
	private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();
	
	static {
		DEFAULT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		DEFAULT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		DEFAULT_MAPPER.registerModule(new JavaTimeModule());
	}
	
	private JSONUtil() {}
	
	public static <T> String toJSON(T obj) {
		try {
			return DEFAULT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.warn("Failed to serilize the object to JSON.", e);
		}
		return "";
	}
	
	public static <T> String toJSON(T obj, String dateFormat) {
	    try {
            return DEFAULT_MAPPER.writer(new SimpleDateFormat(dateFormat)).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Failed to serilize the object to JSON.", e);
        }
        return "";
	}
	
	public static <T> T toBean(String json, Class<T> clazz) {
		try {
			return DEFAULT_MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			LOGGER.warn("Failed to parse the JSON string to an object, JSON: {}", json, e);
		}
		return null;
	}
	
	public static <T> T toBean(String json, Class<T> clazz, String dateFormat) {
        try {
            return new ObjectMapper().setDateFormat(new SimpleDateFormat(dateFormat)).readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.warn("Failed to parse the JSON string to an object, JSON: {}", json, e);
        }
        return null;
    }
	
	public static <T> List<T> toList(String json, Class<T> clazz) {
		JavaType type = DEFAULT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
		try {
			return DEFAULT_MAPPER.readValue(json, type);
		} catch (IOException e) {
			LOGGER.warn("Failed to parse the JSON string to a List object, JSON: {}", json, e);
		}
		return null;
	}
	
	public static <T> List<T> toList(String json, Class<T> clazz, String dateFormat) {
	    ObjectMapper mapper = new ObjectMapper().setDateFormat(new SimpleDateFormat(dateFormat));
        JavaType type = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            LOGGER.warn("Failed to parse the JSON string to a List object, JSON: {}", json, e);
        }
        return null;
    }
	
	
	public static <T> Map<String, T> toMap(String json, Class<T> clazz) {
		JavaType type = DEFAULT_MAPPER.getTypeFactory().constructMapLikeType(HashMap.class, String.class, clazz);
		try {
			return DEFAULT_MAPPER.readValue(json, type);
		} catch (IOException e) {
			LOGGER.warn("Failed to parse the JSON string to a Map object, JSON: {}", json, e);
		}
		return null;
	}
	
	public static <T> Map<String, T> toMap(String json, Class<T> clazz, String dateFormat) {
	    ObjectMapper mapper = new ObjectMapper().setDateFormat(new SimpleDateFormat(dateFormat));
        JavaType type = mapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, clazz);
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            LOGGER.warn("Failed to parse the JSON string to a Map object, JSON: {}", json, e);
        }
        return null;
    }
}
