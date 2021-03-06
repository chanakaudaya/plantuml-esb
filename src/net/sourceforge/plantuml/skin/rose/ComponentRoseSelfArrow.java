/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2014, Arnaud Roques
 *
 * Project Info:  http://plantuml.sourceforge.net
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * Original Author:  Arnaud Roques
 * 
 * Revision $Revision: 15934 $
 *
 */
package net.sourceforge.plantuml.skin.rose;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.skin.ArrowConfiguration;
import net.sourceforge.plantuml.skin.ArrowDecoration;
import net.sourceforge.plantuml.skin.ArrowHead;
import net.sourceforge.plantuml.skin.ArrowPart;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class ComponentRoseSelfArrow extends AbstractComponentRoseArrow {

	private final double arrowWidth = 45;
	private final boolean niceArrow;

	public ComponentRoseSelfArrow(HtmlColor foregroundColor, FontConfiguration font, Display stringsToDisplay,
			ArrowConfiguration arrowConfiguration, ISkinSimple spriteContainer, double maxMessageSize,
			boolean niceArrow) {
		super(foregroundColor, font, stringsToDisplay, arrowConfiguration, spriteContainer, HorizontalAlignment.LEFT,
				maxMessageSize);
		this.niceArrow = niceArrow;
	}

	@Override
	protected void drawInternalU(UGraphic ug, Area area) {
		final StringBounder stringBounder = ug.getStringBounder();
		final double textHeight = getTextHeight(stringBounder);

		ug = ug.apply(new UChangeColor(getForegroundColor()));
		final double xRight = arrowWidth - 3;

		if (getArrowConfiguration().isDotted()) {
			ug = stroke(ug, 2, 2);
		}

		double x1 = area.getDeltaX1() < 0 ? area.getDeltaX1() : 0;
		double x2 = area.getDeltaX1() > 0 ? -area.getDeltaX1() : 0 + 1;

		final double textAndArrowHeight = textHeight + getArrowOnlyHeight(stringBounder);
		final UEllipse circle = new UEllipse(ComponentRoseArrow.diamCircle, ComponentRoseArrow.diamCircle);
		if (getArrowConfiguration().getDecoration1() == ArrowDecoration.CIRCLE) {
			ug.apply(new UStroke(ComponentRoseArrow.thinCircle))
					.apply(new UChangeColor(getForegroundColor()))
					.apply(new UTranslate(x1 + 1 - ComponentRoseArrow.diamCircle / 2 - ComponentRoseArrow.thinCircle,
							textHeight - ComponentRoseArrow.diamCircle / 2 - ComponentRoseArrow.thinCircle / 2))
					.draw(circle);
			x1 += ComponentRoseArrow.diamCircle / 2;
		}
		if (getArrowConfiguration().getDecoration2() == ArrowDecoration.CIRCLE) {
			ug.apply(new UStroke(ComponentRoseArrow.thinCircle))
					.apply(new UChangeColor(getForegroundColor()))
					.apply(new UTranslate(x2 - ComponentRoseArrow.diamCircle / 2 - ComponentRoseArrow.thinCircle,
							textAndArrowHeight - ComponentRoseArrow.diamCircle / 2 - ComponentRoseArrow.thinCircle / 2))
					.draw(circle);
			x2 += ComponentRoseArrow.diamCircle / 2;
		}
		final boolean hasFinalCrossX = getArrowConfiguration().getDressing2().getHead() == ArrowHead.CROSSX;
		if (hasFinalCrossX) {
			x2 += 2 * ComponentRoseArrow.spaceCrossX;
		}

		final double arrowHeight = textAndArrowHeight - textHeight;
		ug.apply(new UTranslate(x1, textHeight)).draw(new ULine(xRight - x1, 0));
		ug.apply(new UTranslate(xRight, textHeight)).draw(new ULine(0, arrowHeight));
		ug.apply(new UTranslate(x2, textAndArrowHeight)).draw(new ULine(xRight - x2, 0));

		if (getArrowConfiguration().isDotted()) {
			ug = ug.apply(new UStroke());
		}

		if (getArrowConfiguration().isAsync()) {
			if (getArrowConfiguration().getPart() != ArrowPart.BOTTOM_PART) {
				ug.apply(new UTranslate(x2, textAndArrowHeight)).draw(new ULine(getArrowDeltaX(), -getArrowDeltaY()));
			}
			if (getArrowConfiguration().getPart() != ArrowPart.TOP_PART) {
				ug.apply(new UTranslate(x2, textAndArrowHeight)).draw(new ULine(getArrowDeltaX(), getArrowDeltaY()));
			}
		} else if (hasFinalCrossX) {
			ug = ug.apply(new UStroke(2));
			ug.apply(
					new UTranslate(ComponentRoseArrow.spaceCrossX, textHeight - getArrowDeltaX() / 2
							+ getArrowOnlyHeight(stringBounder))).draw(new ULine(getArrowDeltaX(), getArrowDeltaX()));
			ug.apply(
					new UTranslate(ComponentRoseArrow.spaceCrossX, textHeight + getArrowDeltaX() / 2
							+ getArrowOnlyHeight(stringBounder))).draw(new ULine(getArrowDeltaX(), -getArrowDeltaX()));

		} else {
			final UPolygon polygon = getPolygon(textAndArrowHeight);
			ug.apply(new UChangeBackColor(getForegroundColor())).apply(new UTranslate(x2, 0)).draw(polygon);

		}

		getTextBlock().drawU(ug.apply(new UTranslate(getMarginX1(), 0)));
	}

	private UPolygon getPolygon(final double textAndArrowHeight) {
		final UPolygon polygon = new UPolygon();
		if (getArrowConfiguration().getPart() == ArrowPart.TOP_PART) {
			polygon.addPoint(getArrowDeltaX(), textAndArrowHeight - getArrowDeltaY());
			polygon.addPoint(0, textAndArrowHeight);
			polygon.addPoint(getArrowDeltaX(), textAndArrowHeight);
		} else if (getArrowConfiguration().getPart() == ArrowPart.BOTTOM_PART) {
			polygon.addPoint(getArrowDeltaX(), textAndArrowHeight);
			polygon.addPoint(0, textAndArrowHeight);
			polygon.addPoint(getArrowDeltaX(), textAndArrowHeight + getArrowDeltaY());
		} else {
			polygon.addPoint(getArrowDeltaX(), textAndArrowHeight - getArrowDeltaY());
			polygon.addPoint(0, textAndArrowHeight);
			polygon.addPoint(getArrowDeltaX(), textAndArrowHeight + getArrowDeltaY());
			if (niceArrow) {
				polygon.addPoint(getArrowDeltaX() - 4, textAndArrowHeight);
			}
		}
		return polygon;
	}

	public Point2D getStartPoint(StringBounder stringBounder, Dimension2D dimensionToUse) {
		final int textHeight = (int) getTextHeight(stringBounder);
		return new Point2D.Double(getPaddingX(), textHeight + getPaddingY());
	}

	public Point2D getEndPoint(StringBounder stringBounder, Dimension2D dimensionToUse) {
		final int textHeight = (int) getTextHeight(stringBounder);
		final int textAndArrowHeight = (int) (textHeight + getArrowOnlyHeight(stringBounder));
		return new Point2D.Double(getPaddingX(), textAndArrowHeight + getPaddingY());
	}

	@Override
	public double getPreferredHeight(StringBounder stringBounder) {
		return getTextHeight(stringBounder) + getArrowDeltaY() + getArrowOnlyHeight(stringBounder) + 2 * getPaddingY();
	}

	private double getArrowOnlyHeight(StringBounder stringBounder) {
		return 13;
	}

	@Override
	public double getPreferredWidth(StringBounder stringBounder) {
		return Math.max(getTextWidth(stringBounder), arrowWidth);
	}

}
