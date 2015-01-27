package com.liangjie.testdrawbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author liangjie
 * @version create time:2014-11-17下午3:02:09
 * @Email 296663133@qq.com
 * @Description 滑动切换模式
 */
@SuppressLint({ "DrawAllocation", "HandlerLeak" })
public class MyDrawButton extends View {
	private Bitmap leftBitmap, rightBitmap;
	private String modeFirStr = "阅后即焚";// 模式一文字
	private String modeSecStr = "普通模式";// 模式二文字
	private int mode = 1;
	private Resources resources;
	private int width, height;
	private float offset = 0;
	/**
	 * 画笔对象的引用
	 */
	private Paint paint;
	/**
	 * 手机的屏幕密度
	 */
	private static float density;
	/**
	 * 字体大小
	 */
	private static final int TEXT_SIZE = 16;

	/***
	 * 
	 * 手势方向 0:左 1:右
	 */
	private int direction;

	/***
	 * 
	 * 默认速度
	 * 
	 */
	private static int defaultDurtion = 300;

	/***
	 * 
	 * 监听
	 */
	private MyDrawButtonListener listener;
	/***
	 * 滑动速度
	 */
	private static int speed;

	@SuppressLint("NewApi")
	public MyDrawButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public MyDrawButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public MyDrawButton(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		resources = getResources();
		leftBitmap = BitmapFactory.decodeResource(resources, R.drawable.open_left);
		rightBitmap = BitmapFactory.decodeResource(resources, R.drawable.right_close);
		paint = new Paint();
		width = PhoneUtil.getDisplayWidth(context);

		speed = width / defaultDurtion;// 计算滑动速度
		density = context.getResources().getDisplayMetrics().density;// 获取屏幕密度
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mode == 1) // 向右滑动
		{
			Rect rect = new Rect(0, 0, (int) (leftBitmap.getWidth() + offset), height);
			paint.setColor(Color.TRANSPARENT);
			canvas.drawRect(rect, paint);
			// 绘制滑动长度颜色
			Rect bg = new Rect(rect.left, rect.top, (int) offset, height);
			// TODO 设置滑动长度颜色，可以自由设置
			paint.setColor(getResources().getColor(R.color.grey_color));
			canvas.drawRect(bg, paint);
			// 绘制图片
			// canvas.drawBitmap(leftBitmap, offset, 0, paint);
			Rect dst = new Rect((int) offset, 0, (int) (offset + (13 * density)), height);
			canvas.drawBitmap(leftBitmap, null, dst, paint);
			// 画扫描框下面的字
			paint.setColor(Color.WHITE);
			paint.setTextSize(TEXT_SIZE * density);
			// paint.setAlpha(0x40);
			paint.setTypeface(Typeface.SANS_SERIF);
			paint.setTextAlign(Align.CENTER);
			FontMetrics fontMetrics = paint.getFontMetrics();
			// 计算文字高度
			float fontHeight = fontMetrics.bottom - fontMetrics.top;
			// 计算文字baseline
			float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
			canvas.drawText(modeFirStr, offset - (width) / 2, textBaseY, paint);
		} else if (mode == 2) // 向左滑动
		{
			Rect rect = new Rect((int) (width + offset) - rightBitmap.getWidth(), 0, width, height);
			paint.setColor(Color.TRANSPARENT);
			canvas.drawRect(rect, paint);
			// 绘制滑动长度颜色
			Rect bg = new Rect((int) (width + offset), rect.top, rect.right, height);
			// TODO 设置滑动长度颜色，可以自由设置
			paint.setColor(getResources().getColor(R.color.grey_color));
			canvas.drawRect(bg, paint);
			// 绘制
			Rect dst = new Rect((int) ((width + offset) - 13 * density), 0, (int) (width + offset), height);
			canvas.drawBitmap(rightBitmap, null, dst, paint);
			// 画扫描框下面的字
			paint.setColor(Color.WHITE);
			paint.setTextSize(TEXT_SIZE * density);
			// paint.setAlpha(0x40);
			paint.setTypeface(Typeface.SANS_SERIF);
			FontMetrics fontMetrics = paint.getFontMetrics();
			// 计算文字高度
			float fontHeight = fontMetrics.bottom - fontMetrics.top;
			// 计算文字baseline
			float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
			canvas.drawText(modeSecStr, width + offset + (width) / 2, textBaseY, paint);
		} else // 默认显示
		{
			// 绘制
			canvas.drawBitmap(leftBitmap, offset, 0, paint);
		}
	}

	private float startX = 0;
	private float lastX = 0;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mode == 0) {
			return super.onTouchEvent(event);
		}
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:// 触摸
			startX = event.getX();
			if (mode == 1) // 从左往右滑动
			{
				if (event.getX() > 23 * density)// 没有按住图标，不往下执行，直接返回
				{
					return super.onTouchEvent(event);
				}
			}
			if (mode == 2) // 从右往左滑动
			{
				if (event.getX() < width + offset - 23 * density) // 没有按住图标，不往下执行。直接返回
				{
					return super.onTouchEvent(event);
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:// 移动
			if (event.getX() - lastX > 0) // 与上一个点比较，向左移动
			{
				direction = 1;
			} else if (event.getX() - lastX < 0)// 向右移动
			{
				direction = 0;
			}
			offset = event.getX() - startX;// 偏移量
			lastX = event.getX();// 记住这个点
			if (listener != null) {
				listener.onScrolling(event.getX(), event.getY(), offset);
			}
			invalidate();// 重绘界面
			break;
		case MotionEvent.ACTION_UP:// 滑动结束
			if (direction == 1) // 最后时刻移动方向，根据不同情况做相应处理
			{
				if (mode == 1 && offset > width / 2) // 此时为向右滑动，且滑动距离已超过总长度一半，直接滑动到顶端
				{
					handler.sendEmptyMessage(2);
				} else {
					// TODO 回到原始状态
					if (mode == 1) {
						handler.sendEmptyMessage(0);
					} else {
						handler.sendEmptyMessage(1);
					}
				}
			} else if (direction == 0) {
				if (mode == 2 && offset < -width / 2) // 此时为向左滑动，且滑动距离已超过总长度一半，直接滑动到顶端
				{
					handler.sendEmptyMessage(3);
				} else {
					// TODO 回到原始状态
					if (mode == 1) {
						handler.sendEmptyMessage(0);
					} else {
						handler.sendEmptyMessage(1);
					}
				}
			}
			invalidate();
			break;
		default:
			break;
		}
		return true;
	}

	/***
	 * 默认时间滑动
	 */
	public void renewAnimation() {
		mode = 1;
		offset = width;
		handler.sendEmptyMessage(0);
	}

	/***
	 * 
	 * @param 自定义滑动时间
	 */
	public void renewAnimation(int durtion) {
		speed = width / durtion;
		mode = 1;
		offset = width;
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				offset = offset - speed * 10;
				if (offset < 0) {
					offset = 0;
				}
				invalidate();
				if (offset > 0) {
					handler.sendEmptyMessageDelayed(0, 10);
				}
				break;
			case 1:
				offset = offset + speed * 10;
				if (offset > 0) {
					offset = 0;
				}
				invalidate();
				if (offset < 0) {
					handler.sendEmptyMessageDelayed(1, 10);
				}
				break;
			case 2:
				offset = offset + 10 * speed;
				if (offset >= width) {
					offset = width;
				}
				invalidate();
				if (offset < width) {
					handler.sendEmptyMessageDelayed(2, 10);
				} else {
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							mode = 2;
							offset = 0;
							if (listener != null) {
								listener.onModeChange(2);
							}
							invalidate();
						}
					}, 100);
				}
				break;
			case 3:
				offset = offset - 10 * speed;
				if (offset <= -width) {
					offset = -width;
				}
				invalidate();
				if (offset > -width) {
					handler.sendEmptyMessageDelayed(3, 10);
				} else {
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							mode = 1;
							offset = 0;
							if (listener != null) {
								listener.onModeChange(1);
							}
							invalidate();
						}
					}, 100);
				}
				break;
			default:
				break;
			}
		};
	};

	public void setMyDrawButtonListener(MyDrawButtonListener listener) {
		this.listener = listener;
	}

	public interface MyDrawButtonListener {
		/***
		 * 
		 * 模式改变
		 * 
		 * @param mode
		 *            1：正常模式 2：秘密模式
		 */
		public void onModeChange(int mode);

		/***
		 * 模式正在改变
		 */
		public void onScrolling(float x, float y, float offset);

	}
}
