package rmutt.cs53.phongsakon.currencyexch;

import android.graphics.drawable.Drawable;

public class ListEntry {

	private Drawable drawables;
	private String unit;
	private String title;
	private String rate;

	public Drawable getDrawables() {
		return drawables;
	}

	public void setDrawables(Drawable drawables) {
		this.drawables = drawables;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

}
