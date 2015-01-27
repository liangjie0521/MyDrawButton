package com.liangjie.testdrawbutton;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.liangjie.testdrawbutton.ToolAdapter.ViewHolder;

/**
 * @author liangjie
 * @version create time:2014-6-25上午11:01:14
 * @Email liangjie@witmob.com
 * @Description 聊天底部View
 */
@SuppressLint({ "HandlerLeak", "NewApi" })
public class IMBottomView extends RelativeLayout {
	// 底部View
	private View recordLayout, toolLayout, bottom, expreesionLayout;
	private LinearLayout layout;
	private GridView gridView;
	private ToolAdapter toolAdapter;
	private ImageView keybordAudio, expression, add;
	private Button sendMsg;
	private EditText edit;
	private Button pressed;
	private ViewPager viewPager;
	private CirclePageIndicator indicator;
	private ExpreesionListModel expreesionListModel;
	private ExpreesionViewPageAdapter viewPageAdapter;

	private Activity context;
	boolean addState = true;
	private ResourceUtil resourceUtil;
	public boolean state;

	private int bottomHeight;
	// 语音播放
	public boolean isClick = false;
	public boolean isDo = false;
	private ImageButton expression1;
	private String currentPath;

	private boolean isChange = true;

	private View deleteLayout;

	private Button delet, forward;

	public boolean isShowDelete = false;

	public boolean toolState;
	public boolean clickVocie = false;
	private MyDrawButton drawButton;
	private FrameLayout frameLayout;

	public IMBottomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		initWidgetAction();
	}

	public IMBottomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		initWidgetAction();
	}

	public IMBottomView(Context context) {
		super(context);
		initView(context);
		initWidgetAction();
	}

	/**
	 * @param context
	 */
	private void initView(Context context) {
		this.context = (Activity) context;
		LayoutInflater.from(context).inflate(R.layout.im_bottom_view, this, true);
		layout = (LinearLayout) findViewById(R.id.layout);
		keybordAudio = (ImageView) findViewById(R.id.input);
		add = (ImageView) findViewById(R.id.add_tool);
		sendMsg = (Button) findViewById(R.id.send_msg);
		expression = (ImageView) findViewById(R.id.expression);
		edit = (EditText) findViewById(R.id.edit);
		pressed = (Button) findViewById(R.id.pressed);
		bottom = new ChatUtil().creatLayout(context, (int) (300 * PhoneUtil.getDisplayDensity(context)));
		toolLayout = bottom.findViewById(R.id.tool);
		recordLayout = bottom.findViewById(R.id.record_layout);
		deleteLayout = findViewById(R.id.delete_layout);
		delet = (Button) findViewById(R.id.delete);
		forward = (Button) findViewById(R.id.forward);
		// 工具
		gridView = (GridView) bottom.findViewById(R.id.gridview);
		// 表情
		expreesionLayout = bottom.findViewById(R.id.expression_layout);
		expression1 = (ImageButton) bottom.findViewById(R.id.expression1);
		viewPager = (ViewPager) bottom.findViewById(R.id.expressionViewpage);
		indicator = (CirclePageIndicator) bottom.findViewById(R.id.circle);
		toolAdapter = new ToolAdapter(context);
		gridView.setAdapter(toolAdapter);
		layout.addView(bottom);
		bottom.setVisibility(View.GONE);
		resourceUtil = ResourceUtil.getInstance(context);
		drawButton = (MyDrawButton) findViewById(R.id.button);
		frameLayout = (FrameLayout) findViewById(R.id.frame);
	}

	public void update(int height) {
		if (bottomHeight != height && height > 100) {
			this.bottomHeight = height;
		}
	}

	public void setDefaultText(String defaultText) {
		String zhengze = "smiley_[0-9]{1,3}";
		SpannableString spannableString = ExpressionUtil.getExpressionString(context, defaultText, zhengze);
		edit.setText(spannableString);
		edit.setSelection(edit.getText().length());
	}

	public void setButtonClick(boolean clickable) {
		delet.setClickable(clickable);
		forward.setClickable(clickable);
		if (!clickable) {
			delet.setBackgroundResource(R.drawable.delete_unclikable_img);
			forward.setBackgroundResource(R.drawable.forward_unclickble_img);
		} else {
			delet.setBackgroundResource(R.drawable.message_delete_sel);
			forward.setBackgroundResource(R.drawable.message_forward_sel);
		}
	}

	public void show(boolean ic) {
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bottom.getLayoutParams();
		params.width = LayoutParams.FILL_PARENT;
		if (ic) {
			if (bottomHeight < 240 * PhoneUtil.getDisplayDensity(context)) {
				params.height = (int) (240 * PhoneUtil.getDisplayDensity(context));
			} else {
				params.height = bottomHeight;
			}
		} else {
			params.height = bottomHeight;
		}
		bottom.setLayoutParams(params);
	}

	public void hide() {
		bottom.setVisibility(View.GONE);
		toolState = false;
		add.setImageResource(R.drawable.type_select_btn_sel);
	}

	public void isShowWhat(boolean isShow) {
		if (isShow) {
			layout.bringToFront();
			isShowDelete = false;
			layout.setVisibility(View.VISIBLE);
			deleteLayout.setVisibility(View.GONE);
		} else {
			isShowDelete = true;
			deleteLayout.bringToFront();
			layout.setVisibility(View.GONE);
			deleteLayout.setVisibility(View.VISIBLE);
		}
	}

	public void Visible() {
		show(false);
		bottom.setVisibility(View.VISIBLE);
	}

	public void setInvisible() {
		if (toolState) {
			if (isChange) {
				show(false);
			}
			bottom.setVisibility(View.INVISIBLE);
			isChange = true;
		}
	}

	public int bottomVisibility() {
		return bottom.getVisibility();
	}

	private View anrch;

	public void setParentView(View anrch) {
		this.anrch = anrch;
	}

	protected void initWidgetAction() {
		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int length = s.length();
				if (length != 0) {
					addState = false;
					// 改变发送按钮
					sendMsg.setVisibility(View.VISIBLE);
					add.setVisibility(View.GONE);
					sendMsg.setText("发送");
				} else {
					addState = true;
					// 改成增加工具
					sendMsg.setVisibility(View.GONE);
					add.setVisibility(View.VISIBLE);
				}
				drawButton.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) drawButton
								.getLayoutParams();
						layoutParams.width = android.widget.FrameLayout.LayoutParams.FILL_PARENT;
						layoutParams.height = frameLayout.getHeight();
						drawButton.setLayoutParams(layoutParams);
						drawButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}
				});
			}
		});
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edit.requestFocus();
				bottom.setVisibility(View.INVISIBLE);
				isClick = true;
				isDo = false;
				expression.setImageResource(R.drawable.chatting_biaoqing_btn_sel);
				if (bottomViewInterface != null) {
					bottomViewInterface.refreshListView();
				}
				toolState = true;
				add.setImageResource(R.drawable.chat_down);
			}
		});
		keybordAudio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 切换输入或语音
				if (!clickVocie) {
					isClick = true;
					if (!state) {
						// 切换到语音
						isDo = false;
						edit.setVisibility(View.GONE);
						bottom.setVisibility(View.GONE);
						if (KeybordState) {
							KeyBoardUtil.hideSoftKeyboard(context);
						}
						pressed.setVisibility(View.VISIBLE);
						keybordAudio.setImageResource(R.drawable.chatting_setmode_keybord_sel);
						toolState = false;
						add.setImageResource(R.drawable.type_select_btn_sel);
					} else {
						// 切换到输入
						edit.setVisibility(View.VISIBLE);
						edit.requestFocus();
						expression.setVisibility(View.VISIBLE);
						pressed.setVisibility(View.GONE);
						bottom.setVisibility(View.INVISIBLE);
						KeyBoardUtil.showSoftKeyboard(context);
						isClick = true;
						isDo = false;
						keybordAudio.setImageResource(R.drawable.chatting_setmode_voice_btn_sel);
						toolState = true;
						add.setImageResource(R.drawable.chat_down);
					}
					state = !state;
				}
			}
		});
		pressed.setOnTouchListener(new OnTouchListener() {
			private int startY;
			private int Y;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				isClick = true;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					clickVocie = true;
					pressed.setBackgroundResource(R.drawable.pressed_press);
					if (bottomViewInterface != null) {
						bottomViewInterface.startRecord();
					}
					Y = (int) event.getY();
					startY = Y;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					pressed.setBackgroundResource(R.drawable.press_speak_sel);
					pressed.setText(context.getResources().getString(R.string.pressed_speak));
					bottom.setVisibility(View.GONE);
					toolState = false;
					add.setImageResource(R.drawable.type_select_btn_sel);
					recordLayout.setVisibility(View.GONE);
					clickVocie = false;
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (Math.abs(Y - event.getY()) < 200) {
						pressed.setText(context.getResources().getString(R.string.loosen_end));
						return false;
					}
					startY = (int) event.getY();
				}
				return false;
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!clickVocie) {
					// 增加工具、发送
					isClick = true;
					isDo = true;
					isChange = false;
					if (toolState) {
						bottom.setVisibility(View.INVISIBLE);
						if (KeybordState) {
							KeyBoardUtil.hideSoftKeyboard(context);
						}
						handler.sendEmptyMessageDelayed(5, 100);
						add.setImageResource(R.drawable.type_select_btn_sel);
					} else {
						if (KeybordState) {
							KeyBoardUtil.hideSoftKeyboard(context);
						}
						showTool();
						add.setImageResource(R.drawable.chat_down);
					}
					toolState = !toolState;
				}
			}
		});
		sendMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// isClick = true;
				String msgID = System.currentTimeMillis() + "";
				if (edit.getText() != null && !edit.getText().toString().equals("")) {
					bottomViewInterface.SendMsg("text", edit.getText().toString(), msgID);
					edit.setText("");
				}
				bottomViewInterface.refreshListView();
			}
		});
		expression.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 表情
				isDo = true;
				isChange = false;
				showExprees();
				if (KeybordState) {
					KeyBoardUtil.hideSoftKeyboard(context);
				}
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// 底部工具点击事件
				isClick = true;
				ViewHolder holder = (ViewHolder) arg1.getTag();
				String id = holder.model.getId();
				if (id.equals("audio")) {
					// 录音
					toolLayout.setVisibility(View.GONE);
					recordLayout.setVisibility(View.VISIBLE);
					expreesionLayout.setVisibility(View.GONE);
					recordLayout.bringToFront();
				} else if (id.equals("card")) {
					// 发送名片
					sendCard();
					bottom.setVisibility(View.GONE);
					toolState = false;
					add.setImageResource(R.drawable.type_select_btn_sel);
				} else if (id.equals("pic")) {
					// 发送图片
					openGallery();
					bottom.setVisibility(View.GONE);
					toolState = false;
					add.setImageResource(R.drawable.type_select_btn_sel);
				} else if (id.equals("camera")) {
					// 拍照发送
					takePicture();
					bottom.setVisibility(View.GONE);
					toolState = false;
					add.setImageResource(R.drawable.type_select_btn_sel);
				} else if (id.equals("location")) {
					// 发送位置
					// 修改为弹层
					bottom.setVisibility(View.VISIBLE);
					toolLayout.setVisibility(View.VISIBLE);
					expreesionLayout.setVisibility(View.GONE);
					recordLayout.setVisibility(View.GONE);
				} else if (id.equals("burn")) {
					// TODO 阅后即焚
					openBurnGallery();
					bottom.setVisibility(View.GONE);
					toolState = false;
					add.setImageResource(R.drawable.type_select_btn_sel);
				}
			}
		});
		readJson();
		recordLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isClick = true;
				bottom.setVisibility(View.GONE);
				toolState = false;
				add.setImageResource(R.drawable.type_select_btn_sel);
				recordLayout.setVisibility(View.GONE);
			}
		});
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				isClick = true;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				isClick = true;
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				isClick = true;
			}
		});
		expression1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPageAdapter = new ExpreesionViewPageAdapter(((FragmentActivity) context)
						.getSupportFragmentManager(), expreesionListModel.getList());
				viewPager.setAdapter(viewPageAdapter);
				indicator.setViewPager(viewPager);
				viewPager.setVisibility(View.VISIBLE);
				indicator.setVisibility(View.VISIBLE);
			}
		});
		forward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 删除
				bottomViewInterface.onClickForward();
			}
		});
		delet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 转发
				bottomViewInterface.onClickDelete();
			}
		});
	}

	private boolean KeybordState = false;

	public void setKeybordState(boolean KeybordState) {
		this.KeybordState = KeybordState;
	}

	protected void sendCard() {
		isClick = true;
	}

	private void openGallery() {
	}

	private void openBurnGallery() {

	}

	private void takePicture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			Uri mImageCaptureUri = null;
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				mImageCaptureUri = Uri.fromFile(new File(currentPath));
			} else {
				/*
				 * The solution is taken from here:
				 * http://stackoverflow.com/questions
				 * /10042695/how-to-get-camera-result-as-a-uri-in-data-folder
				 */
				// InternalStorageContentProvider.CONTENT_URI;
				mImageCaptureUri = Uri.parse("content://eu.janmuller.android.simplecropimage.example/");
			}
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
			intent.putExtra("return-data", true);
		} catch (ActivityNotFoundException e) {
			Log.d("TAG", "cannot take picture", e);
		}
	}

	public boolean bottomIsShown() {
		if (bottom.getVisibility() == View.GONE) {
			return false;
		}
		return true;
	}

	public void setExpression() {
		expression.setImageResource(R.drawable.chatting_biaoqing_btn_sel);
	}

	private ToolListModel toolListModel;

	protected void readJson() {
		String result = resourceUtil.readTextFileFromRawResourceId(context, R.raw.chat_bottom_json);
		toolListModel = new Gson().fromJson(result, ToolListModel.class);
		String expressionStr = resourceUtil.readTextFileFromRawResourceId(context, R.raw.expression_list);
		expreesionListModel = new Gson().fromJson(expressionStr, ExpreesionListModel.class);
		viewPageAdapter = new ExpreesionViewPageAdapter(((FragmentActivity) context).getSupportFragmentManager(),
				expreesionListModel.getList());
		viewPager.setAdapter(viewPageAdapter);
		indicator.setViewPager(viewPager);
		expression1.setBackgroundResource(R.drawable.emoj_select_color);
		expression1.setClickable(false);
	}

	public void setToolData(boolean isGroup) {
		if (!isGroup) {
			toolAdapter.refreshData(toolListModel.getList());
		} else {
			List<ToolModel> list = toolListModel.getList();
			list.remove(list.size() - 1);
			toolAdapter.refreshData(list);
		}
	}

	private void showExprees() {
		isClick = true;
		state = false;
		edit.setVisibility(View.VISIBLE);
		edit.requestFocus();
		if (KeybordState) {
			KeyBoardUtil.hideSoftKeyboard(context);
		}
		pressed.setVisibility(View.GONE);
		keybordAudio.setImageResource(R.drawable.chatting_setmode_voice_btn_sel);
		show(true);
		bottom.setVisibility(View.VISIBLE);
		expreesionLayout.setVisibility(View.VISIBLE);
		expreesionLayout.bringToFront();
		toolLayout.setVisibility(View.GONE);
		recordLayout.setVisibility(View.GONE);
		handler.sendEmptyMessageDelayed(3, 100);
		toolState = true;
		add.setImageResource(R.drawable.chat_down);
	}

	public void Editappend(SpannableString string) {
		edit.append(string);
	}

	public void EditDelete() {
		EditTextUtil.deleteText(edit);
	}

	protected void showTool() {
		isClick = true;
		state = false;
		edit.setVisibility(View.VISIBLE);
		pressed.setVisibility(View.GONE);
		keybordAudio.setImageResource(R.drawable.chatting_setmode_voice_btn_sel);
		show(true);
		bottom.setVisibility(View.VISIBLE);
		toolLayout.setVisibility(View.VISIBLE);
		toolLayout.bringToFront();
		handler.sendEmptyMessageDelayed(4, 100);
		expression.setImageResource(R.drawable.chatting_biaoqing_btn_sel);
	}

	private IMBottomViewInterface bottomViewInterface;

	public void setIMBottomViewInterface(IMBottomViewInterface bottomViewInterface) {
		this.bottomViewInterface = bottomViewInterface;
	}

	public interface IMBottomViewInterface {
		public void RecordVoicListener(String type, String path, String msgId, int audioTime);

		public void refreshListView();

		public void SendMsg(String type, String msg, String msgId);

		public void startRecord();

		public void onClickDelete();

		public void onClickForward();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				break;
			case 3:
				expression.setImageResource(R.drawable.chatting_biaoqing_btn_normal);
				if (bottomViewInterface != null) {
					bottomViewInterface.refreshListView();
				}
				break;
			case 4:
				if (bottomViewInterface != null) {
					bottomViewInterface.refreshListView();
				}

				break;
			case 5:
				bottom.setVisibility(View.GONE);
				break;
			case 6:
				show(true);
				break;
			default:
				break;
			}
		};
	};
}
