/*
 * @(#)NullPainter.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	\x{FFFD} by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */
package org.fmatjhotdraw.standard;

import java.awt.Graphics;

import org.fmatjhotdraw.framework.DrawingView;
import org.fmatjhotdraw.framework.Painter;

/**
 * Default implementation support for a Painter.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b>NullObject</b><br>
 * NullObject enables to treat handles that don't do
 * anything in the same way as other handles.
 *
 * @see Painter
 *
 * @version <$CURRENT_VERSION$>
 */

public class NullPainter implements Painter {
	/* (non-Javadoc)
	 * @see org.fmatjhotdraw.framework.Painter#draw(java.awt.Graphics, org.fmatjhotdraw.framework.DrawingView)
	 */
	public void draw(Graphics g, DrawingView view) {
		// Do nothing in a reasonable way.
	}
}
