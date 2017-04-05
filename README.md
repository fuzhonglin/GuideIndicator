# GuideIndicator #
基于ViewPager的新手引导页，使用时只需要设置需要显示的资源，并且通过设置页面中按钮的点击事件处理用户点击按钮后需进行的操作即可，资源以资源id的形式传入。

具有以下几个常用方法：

* public void setPointColor(int selectColor, int defaultColor)；设置指示器中圆点的颜色。
* public void setButtonBackgroud(int pressedDrawableId, int defaultDrawableId)；设置进入应用的按钮背景。
* public void setButtonText(String text)；设置进入应用的按钮文字。
* public void setButtonTextColor(int pressedColor, int defaultColor)；设置进入应用的按钮文字颜色。
* public void setButtonPadding(int left, int top, int right, int bottom)；设置按钮的内边距。
* public void setOnClickListener(OnClickListener listener)；设置按钮的点击事件监听。
* public void setPagerRes(int[] resIds)；设置图片资源。
	