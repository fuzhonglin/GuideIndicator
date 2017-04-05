package com.example.guidetest;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GuideIndicator extends RelativeLayout {

	private ArrayList<ImageView> mPagerRes = new ArrayList<ImageView>();//ViewPager的显示资源
	private ViewPager mPager;//显示图片的ViewPager
	
	private LinearLayout mPointContainer;//指示器的容器
	private GradientDrawable mSelectedPoint;//指示器中选中状态圆点
	private GradientDrawable mDefaultPoint;//指示器中未选中状态的圆点
	private ImageView mIndicatorPoint;//可移动的圆点
	
	private Button mButton = new Button(getContext());//进入应用的按钮
	private OnClickListener mListener;//按钮的点击事件监听类
	
	/**
	 * 设置指示器中圆点的颜色
	 * @param selectColor
	 * 		选中状态的圆点颜色
	 * @param defaultColor
	 * 		未选中状态的圆点颜色
	 */
	public void setPointColor(int selectColor, int defaultColor){
		mSelectedPoint.setColor(selectColor);
		mDefaultPoint.setColor(defaultColor);
	}
	
	/**
	 * 设置进入应用的按钮背景
	 *@param pressedDrawableId
	 * 		按下状态的背景
	 * @param defaultDrawableId
	 * 		未按下状态的背景
	 */
	public void setButtonBackgroud(int pressedDrawableId, int defaultDrawableId){
		StateListDrawable drawable = new StateListDrawable();
		Drawable pressedDrawable = getResources().getDrawable(pressedDrawableId);
		Drawable defaultDrawable = getResources().getDrawable(defaultDrawableId);
        
		drawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        drawable.addState(new int[]{}, defaultDrawable);
        
        mButton.setBackground(drawable);
	}
	
	/**
	 * 设置进入应用的按钮文字
	 */
	public void setButtonText(String text){
		mButton.setText(text);
	}
	
	/**
	 * 设置按钮文字的颜色
	 * @param pressedColor
	 * 		按下时文字的颜色
	 * @param defaultColor
	 * 		未按下时文字的颜色
	 */
	public void setButtonTextColor(int pressedColor, int defaultColor){
		int[] colors = new int[]{pressedColor, defaultColor};
		
		int[][] states =new int[2][];
		states[0] = new int[]{android.R.attr.state_pressed};
		states[1] = new int[]{};
		
		ColorStateList colorStateList = new ColorStateList(states, colors);
		
		mButton.setTextColor(colorStateList);
	}
	
	/**
	 * 设置按钮的内边距
	 */
	public void setButtonPadding(int left, int top, int right, int bottom){
		mButton.setPadding(left, top, right, bottom);
	}

	public GuideIndicator(Context context) {
		this(context, null);
	}

	public GuideIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GuideIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		//初始化ViewPager
		initViewPager();
		
		//初始化指示器中的圆点
		initPoint();
		
		//初始化指示器容器
		initPointContainer();
		
		//初始化进入应用按钮
		initButton();
	}

	private void initViewPager() {
		mPager = new ViewPager(getContext());
		LayoutParams pagerParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
																   LayoutParams.MATCH_PARENT);
		addView(mPager, pagerParams);
	}
	
	private void initPoint() {
		//初始化选中时的指示器圆点
		mSelectedPoint = new GradientDrawable();
		mSelectedPoint.setShape(GradientDrawable.OVAL);
		mSelectedPoint.setColor(Color.parseColor("#ff0000"));
		mSelectedPoint.setSize(dip2px(10), dip2px(10));
		
		//初始化未选中时的指示器圆点
		mDefaultPoint = new GradientDrawable();
		mDefaultPoint.setShape(GradientDrawable.OVAL);
		mDefaultPoint.setColor(Color.parseColor("#cccccc"));
		mDefaultPoint.setSize(dip2px(10), dip2px(10));
	}

	private void initPointContainer() {
		
		//生成一个相对布局
		RelativeLayout relativeLayout = new RelativeLayout(getContext());
		LayoutParams relativeParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		relativeParams.addRule(CENTER_HORIZONTAL);
		relativeParams.addRule(ALIGN_PARENT_BOTTOM);
		relativeParams.bottomMargin = dip2px(50);
		
		//生成一个水平的线性布局并添加到上述相对布局中
		mPointContainer = new LinearLayout(getContext());
		mPointContainer.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams containerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		relativeLayout.addView(mPointContainer, containerParams);
		
		//生成一个选中状态的圆点添加到上述相对布局中
		mIndicatorPoint = new ImageView(getContext());
		mIndicatorPoint.setImageDrawable(mSelectedPoint);
		relativeLayout.addView(mIndicatorPoint);
		
		//将相对布局添加到当前布局中
		addView(relativeLayout, relativeParams);
	}

	private void initButton() {
	
		//隐藏按钮
		mButton.setVisibility(View.GONE);
	
		//设置按钮的点击事件
		mButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(mListener!=null){
					mListener.onClick(v);
				}	
			}
		});	
		
		//设置按钮的位置
		LayoutParams buttonParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		buttonParams.addRule(ALIGN_PARENT_BOTTOM);
		buttonParams.addRule(CENTER_HORIZONTAL);
		buttonParams.bottomMargin = dip2px(85);
		
		addView(mButton, buttonParams);
	}

	/** 
	 * 该方法用于设置进入应用的按钮的点击事件
	 */
	public void setOnClickListener(OnClickListener listener){
		mListener = listener;
	}
	
	/**
	 * 设置图片资源
	 * @param resIds
	 * 		图片资源的id数组
	 */
	public void setPagerRes(int[] resIds){
		ImageView imageView;
		ImageView point;
		
		for(int i=0; i<resIds.length; i++){
			//填充ImageView的集合
			imageView = new ImageView(getContext());
			imageView.setBackgroundResource(resIds[i]);
			mPagerRes.add(imageView);
			
			//填充指示器的圆点
			point = new ImageView(getContext());
			point.setImageDrawable(mDefaultPoint);
			
			//根据圆点的位置设置圆点的边距
			LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			int pointMargin = dip2px(5);
			if(i==0){
				pointParams.leftMargin = 0;
				pointParams.rightMargin = pointMargin;
			}
			if(i==resIds.length-1){
				pointParams.leftMargin = pointMargin;
				pointParams.rightMargin = 0;
			}
			if(i!=0 && i!=resIds.length-1){
				pointParams.leftMargin = pointMargin;
				pointParams.rightMargin = pointMargin;
			}
			
			mPointContainer.addView(point, pointParams);
		}
		
		if(mPagerRes!=null && mPagerRes.size()>0){
			MyAdapter adapter = new MyAdapter();
			mPager.setAdapter(adapter);
			
			//获取指示器中两个圆点之间的距离
			getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onGlobalLayout() {
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
					int pointDis = mPointContainer.getChildAt(1).getLeft()-mPointContainer.getChildAt(0).getLeft();
					//处理ViewPager的滑动事件
					initPagerScroller(pointDis);
				}
			});
		}
	}
	
	private void initPagerScroller(final int pointDis) {			
		
		//设置页面滑动的监听事件
		mPager.setOnPageChangeListener(new OnPageChangeListener() {		
			@Override
			public void onPageScrollStateChanged(int arg0){}
			@Override
			public void onPageSelected(int position) {
				//当前页面为最后一个页面时，显示进入应用的按钮，否则隐藏该按钮
				if(position == mPagerRes.size()-1){
					mButton.setVisibility(View.VISIBLE);
				}else{
					mButton.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				//计算移动后被选中圆点的左边距
				int leftMargin = (int) (pointDis*(position+positionOffset));
				//重新设置圆点的布局
				LayoutParams selectedPointParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				selectedPointParams.leftMargin = leftMargin;
				mIndicatorPoint.setLayoutParams(selectedPointParams);
			}
		});
	}
	
	//该方法用于将dip值转换为px值
	private int dip2px(float dip) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}
	
	//ViewPager的适配器
	private class MyAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			return mPagerRes.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = mPagerRes.get(position);
			container.addView(imageView);
			return imageView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

}
