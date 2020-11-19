package com.soprabanking.ips.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class responsible for mapping Strings to Json Objects using methods of {@link com.fasterxml.jackson.databind.ObjectMapper}
 * @author araghav
 *
 */
public class JsonUtil {
	/**
	 * Returns a JsonNode object that denotes a Json object corresponding to the specified parameter Request String received from Controllers
	 * <p> This method processes the given String containing Request parameters and their values and maps it to a JsonNode object, which can then be used for retrieving the request parameters and their values by the Service methods. </p>
	 * @param str String object containing Request parameters and values
	 * @return JsonNode object represnting the resulting Json object
	 * @throws JsonMappingException if the parameter String is not in a required format for mapping purpose
	 * @throws JsonProcessingException if the parameter String is invalid
	 */
    public static JsonNode stringToJson(String str) throws JsonMappingException, JsonProcessingException {

        ObjectMapper obj = new ObjectMapper();

        return obj.readTree(str);
    }

}
