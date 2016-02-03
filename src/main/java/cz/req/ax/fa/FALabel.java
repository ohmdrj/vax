package cz.req.ax.fa;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class FALabel extends Label {

	private static final long serialVersionUID = -1987552834791565983L;

	private String size, fixedWidth, border, spin, pull, rotate, flip, inverse, stackOrder, custom, style;
	private FALabel stacked;
	private FA icon;

	public FALabel(FA icon) {
		this.icon = icon;
		setSizeUndefined();
    	setContentMode(ContentMode.HTML);
    	addAttachListener(new AttachListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void attach(AttachEvent event) {
				applyChanges();
			}
    	});
    }

	public void setIcon(FA icon) {
		this.icon = icon;
		applyChanges();
	}

	public void applyChanges() {
    	super.setValue(getCssHtml());
    }
    
    public String getCssClasses(boolean ignoreSize) {
    	String classes = "fa " + icon.clazz;
    	classes += !ignoreSize && size != null ? " " + size : "";
    	classes += fixedWidth != null ? " " + fixedWidth : "";
    	classes += border != null ? " " + border : "";
    	classes += spin != null ? " " + spin : "";
    	classes += pull != null ? " " + pull : "";
    	classes += rotate != null ? " " + rotate : "";
    	classes += flip != null ? " " + flip : "";
    	classes += inverse != null ? " " + inverse : "";
    	classes += stackOrder != null ? " " + stackOrder : "";
    	return classes;
    }
    
    public String getCssHtml() {
    	if (stacked == null) {
    		return "<i class=\"" + getCssClasses(false) + "\"" 
    				+ (custom != null ? "style=\"" + custom + "\"" : "") + "></i>";
    	} 
    	// Get stack size (bottom object takes precedence). Clear individual sizes.
    	String stackSize = size != null ? size : stacked.size != null ? stacked.size : null;
    	return "<span class=\"fa-stack " + (stackSize != null ? stackSize : "") + "\">"
    				+ "<i class=\"" + getCssClasses(true) + "\""
    					+ (custom != null ? "style=\"" + custom + "\"" : "") + "></i>"
    				+ "<i class=\"" + stacked.getCssClasses(true) + "\""
        				+ (stacked.custom != null ? "style=\"" + stacked.custom + "\"" : "") + "></i>"
    			+ "</span>";
    }

	public FALabel setCustomStyle(String css) {
		custom = css; return this;
    }

	public FALabel setStyle(String style) {
		setStyleName(style);
		return this;
	}

	public FALabel setSizeLg() {
		size = "fa-lg"; return this;
    }

	public FALabel setSize2x() {
		size = "fa-2x"; return this;
    }

	public FALabel setSize3x() {
		size = "fa-3x"; return this;
    }

	public FALabel setSize4x() {
		size = "fa-4x"; return this;
    }

	public FALabel setSize5x() {
		size = "fa-5x"; return this;
    }

	public FALabel setSize6x() {
		size = "fa-6x"; return this;
    }

	public FALabel fixedWidth() {
		fixedWidth = "fa-fw"; return this;
    }

	public FALabel enableBorder() {
		border = "fa-border"; return this;
    }

	public FALabel pullLeft() {
		pull = "pull-left"; return this;
    }

	public FALabel pullRight() {
		pull = "pull-right"; return this;
    }

	public FALabel spin() {
		spin = "fa-spin"; return this;
    }

	public FALabel rotate90() {
		rotate = "fa-rotate-90"; return this;
    }

	public FALabel rotate180() {
		rotate = "fa-rotate-180"; return this;
    }

	public FALabel rotate270() {
		rotate = "fa-rotate-270"; return this;
    }

	public FALabel flipHorizontal() {
		flip = "fa-flip-horizontal"; return this;
    }

	public FALabel flipVertical() {
		flip = "fa-flip-vertical"; return this;
    }

	public FALabel inverseColor() {
		inverse = "fa-inverse"; return this;
    }

	public FALabel stack(FA overlay) {
		return stack(overlay.getLabel());
    }

	public FALabel stack(FALabel overlay) {
		stacked = overlay;
    	stackOrder = "fa-stack-1x";
    	stacked.stackOrder = "fa-stack-2x";
    	return this;
    }

	public FALabel reverseStackSize() {
		if (stackOrder != null && stacked != null) {
	    	stackOrder = "fa-stack-2x"; 
	    	stacked.stackOrder = "fa-stack-1x";
	    	return this;
    	}
    	throw new UnsupportedOperationException("Cannot call reverseStackSize() before a stacked icon has been set.");
    }

	public FALabel clearStack() {
		if (stacked != null) stacked.stackOrder = null;
    	stacked = null;
    	stackOrder = null;
    	return this;
    }
}