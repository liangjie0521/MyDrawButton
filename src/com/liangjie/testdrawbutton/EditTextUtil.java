package com.liangjie.testdrawbutton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.text.SpannableString;
import android.widget.EditText;

/**
 * @author liangjie
 * @version create time:2014-5-29上午11:11:18
 * @Email liangjie@witmob.com
 * @Description EditText工具
 */
public class EditTextUtil {
	private static String zhengze = "smiley_[0-9]{1,3}";

	/** 获取EditText光标所在的位置 */
	public static int getEditTextCursorIndex(EditText mEditText) {
		return mEditText.getSelectionStart();
	}

	/** 向EditText指定光标位置插入字符串 */
	public static void insertText(EditText mEditText, String mText) {
		mEditText.getText().insert(getEditTextCursorIndex(mEditText), mText);
	}

	/** 向EditText指定光标位置删除字符串 */
	public static void deleteText(EditText mEditText) {
		if (mEditText.getText().toString() != null && !mEditText.getText().toString().equals("")) {
			String body = mEditText.getText().toString();
			int selectionStart = mEditText.getSelectionStart();// 获取光标的位置
			String tempStr = body.substring(0, selectionStart);
			SpannableString spannableString = new SpannableString(tempStr);
			Pattern patten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE); // 通过传入的正则表达式来生成一个pattern
			Matcher matcher = patten.matcher(spannableString);
			List<String> list = new ArrayList<String>();
			while (matcher.find()) {
				String key = matcher.group();
				list.add(key);
				if (matcher.start() < 0) {
					continue;
				}
			}
			int i = tempStr.lastIndexOf("smiley_");// 获取最后一个表情的位置
			if (i != -1) {
				CharSequence cs = tempStr.subSequence(i, selectionStart);
				for (int j = 0; j < list.size(); j++) {
					if (cs.equals(list.get(j))) {// 判断是否是一个表情
						mEditText.getEditableText().delete(i, selectionStart);
						return;
					}

				}
			}
			mEditText.getEditableText().delete(tempStr.length() - 1, selectionStart);
		}
	}

	public static void DeleteText(EditText mEditText) {
		if (mEditText.getText().toString() != null && !mEditText.getText().toString().equals("")) {
			int selectionStart = mEditText.getSelectionStart();// 获取光标的位置
			Editable editable = mEditText.getText();
			if (selectionStart > 0) {
				editable.delete(selectionStart - 1, selectionStart);
			}

		}
	}
}
