package com.trek.interview.assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrekInterviewAssignment {

	private static final String URL = "https://trekhiringassignments.blob.core.windows.net/interview/bikes.json";
	
	public static void main(String[] args) throws JSONException, IOException {
		
		// get json
		JSONArray json = new JSONArray(readUrl(URL));

		// iterate over elements of json, create list
		List<String> responses = getListOfResponses(json);

		// iterate over list, create HashMap<String, Integer> of responses (key) and frequencies (values)
		HashMap map = createMapOfRespFreq(responses);
		
		// sort HashMap by values descending
		Map<String, Integer> sortedMap = sortMapByValuesDesc(map);
		
		// print output
		printTopTwentyResponses(sortedMap);
		
	}

	/**
	 * Prints the first 20 elements of given map.
	 * 
	 * @param map Map<K, V> map object.
	 */
	private static <K, V> void printTopTwentyResponses(Map<K, V> map) {
		
		int count = 1;
		for (Map.Entry<K, V> entry : map.entrySet()) {
           
			System.out.println("Combination: " + entry.getKey() + ", Responses:" + entry.getValue());
			
			count ++;
			
			if (count > 20) {
				break;
			}
			
        }
		
	}

	/**
	 * Sorts given map by values in reverse natural order.
	 * 
	 * @param unsortMap Map<String, Integer> of unsorted elements
	 * @return Map<String, Integer> sorted by values reverse natural order
	 */
	private static Map<String, Integer> sortMapByValuesDesc(Map<String, Integer> unsortMap) {
		
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

				return (o2.getValue()).compareTo(o1.getValue());

			}

		});

		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
		
	}

	/**
	 * Creates a Map of keys of elements from given list and values of the frequency of each unique element on the list.
	 * 
	 * @param responses
	 * @return HashMap of Response, Frequency of Response pairs
	 */
	private static HashMap<String, Integer> createMapOfRespFreq(List<String> responses) {
		
		HashMap<String, Integer> map = new HashMap<>();

		for (String resp : responses) {
			
			map.put(resp, Collections.frequency(responses, resp));
			
		}
		
		return map;
		
	}

	/**
	 * Returns a List<String> of elements from given JSONArray.
	 * 
	 * @param json JSONArray object
	 * @return list of elements in JSONArray
	 */
	private static List<String> getListOfResponses(JSONArray json) {
		
		List<String> responses = new ArrayList<>();
		
		for (int a = 0; a < json.length(); a++) {

			JSONObject obj = json.getJSONObject(a);
			
			JSONArray array = obj.getJSONArray("bikes");
			
			ArrayList<String> tempList = getListOfBikes(array);

			responses.add(getResponseString(tempList));
			
		}
		
		return responses;
		
	}
	
	/**
	 * Creates an ArrayList<String>, the elements of which are equivalent to "bikes" array name in given array.
	 * @param array
	 * @return
	 */
	private static ArrayList<String> getListOfBikes(JSONArray array) {
		
		ArrayList<String> list = new ArrayList<>();
		
		for (int a = 0; a < array.length(); a++) {

			String str = array.getString(a);
			list.add(str);

		}
		
		return list;
		
	}
	
	/**
	 * Creates a String object that is a comma separated list of bikes in each response, in alphabetical order
	 * @param list
	 * @return
	 */
	private static String getResponseString(ArrayList<String> list) {
		
		Collections.sort(list);
		StringBuilder respStr = new StringBuilder();

		for (String resp : list) {

			respStr.append(resp + ", ");

		}

		respStr.deleteCharAt(respStr.lastIndexOf(" "));
		respStr.deleteCharAt(respStr.lastIndexOf(","));
		
		return respStr.toString();
		
	}

	/**
	 * Reads JSON from given URL and returns a String representation.
	 * 
	 * @param urlString - String representation of the URL.
	 * @return - JSON String
	 * @throws IOException - exception during connection
	 */
	private static String readUrl(String urlString) throws IOException {

		BufferedReader reader = null;
		
		try {
			
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
			
		} finally {
			
			if (reader != null)
				reader.close();
			
		}
		
	}

}
