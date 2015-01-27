package com.liangjie.testdrawbutton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ResourceUtil {
	private Resources resources;
	private String pkg;
	private static ResourceUtil resourceUtil;

	private ResourceUtil(Context context) {
		pkg = context.getPackageName();
		if (context instanceof Application) {
			resources = context.getResources();
		} else {
			resources = context.getApplicationContext().getResources();
		}
	}

	public static ResourceUtil getInstance(Context context) {
		if (resourceUtil == null) {
			resourceUtil = new ResourceUtil(context);
		}
		return resourceUtil;
	}

	protected int getResourcesId(Context context, String type, String name) {
		try {
			int id = resources.getIdentifier(name, type, pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getStringId(String name) {
		try {
			int id = resources.getIdentifier(name, "string", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getString(String name) {
		String s = "";
		int id = getStringId(name);
		try {
			s = resources.getString(id);
		} catch (Exception e) {
			e.printStackTrace();
			s = "";
		}
		return s;
	}

	public int getColorId(String name) {
		try {
			int id = resources.getIdentifier(name, "color", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getColor(String name) {
		int color = Color.TRANSPARENT;
		int id = getColorId(name);
		try {
			color = resources.getColor(id);
		} catch (Exception e) {
			color = 0;
		}
		return color;
	}

	public int getDimenId(String name) {
		try {
			int id = resources.getIdentifier(name, "dimen", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getStyleId(String name) {
		try {
			int id = resources.getIdentifier(name, "style", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getLayoutId(String name) {
		try {
			int id = resources.getIdentifier(name, "layout", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getViewId(String name) {
		try {
			int id = resources.getIdentifier(name, "id", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getAnimId(String name) {
		try {
			int id = resources.getIdentifier(name, "anim", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getArrayId(String name) {
		try {
			int id = resources.getIdentifier(name, "array", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getDrawableId(String name) {
		try {
			int id = resources.getIdentifier(name, "drawable", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getStyleableId(String name) {
		try {
			int id = resources.getIdentifier(name, "styleable", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Drawable getDrawable(String name) {
		Drawable drawable = null;
		try {
			int id = getDrawableId(name);
			drawable = resources.getDrawable(id);
		} catch (Exception e) {
			drawable = null;
		}
		return drawable;
	}

	public int getRawId(String name) {
		try {
			int id = resources.getIdentifier(name, "raw", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 这是用来 获取raw下文件 String。
	 * 
	 * 比如json。
	 * 
	 * */
	public String readTextFileFromRawResourceId(Context context, int resourceId) {
		StringBuilder builder = new StringBuilder();

		BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(
				resourceId)));

		try {
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				builder.append(line).append("\n");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return builder.toString();
	}

	/**
	 * 这是用来 获取sdcard下文件 String。
	 * 
	 * 比如json。
	 * 
	 * 
	 * */
	@SuppressWarnings("resource")
	public String readTextFileFromSDResourceId(Context context, String path, String Name) {
		StringBuilder builder = new StringBuilder();
		File file = new File(path);
		if (file.exists()) {

			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				for (String line = reader.readLine(); line != null; line = reader.readLine()) {
					builder.append(line).append("\n");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return builder.toString();
		} else {
			return null;
		}
	}

	// 将json 数组转换为Map 对象
	public static Map<String, Object> getMap(String jsonString) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			@SuppressWarnings("unchecked")
			Iterator<String> keyIter = jsonObject.keys();
			String key;
			Object value;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 把json 转换为 ArrayList 形式
	public List<Map<String, Object>> getList(Context context, String path, String Name) {
		List<Map<String, Object>> list = null;
		try {
			String jsonString = readTextFileFromSDResourceId(context, path, Name);
			JSONArray jsonArray = new JSONArray(jsonString);
			JSONObject jsonObject;
			list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				list.add(getMap(jsonObject.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Map<String, Object> readMap(Context context, String path, String Name) {
		String jsonString = readTextFileFromSDResourceId(context, path, Name);
		Map<String, Object> map = new Gson().fromJson(jsonString, new TypeToken<Map<String, Object>>() {
		}.getType());
		return map;
	}
}
