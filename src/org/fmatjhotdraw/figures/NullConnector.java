/*
 * @(#)Connector.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	� by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */

package org.fmatjhotdraw.figures;

import org.fmatjhotdraw.standard.AbstractConnector;
import org.fmatjhotdraw.framework.Figure;

public class NullConnector extends AbstractConnector {
	// AbstractConnector implements already all methods but cannot be instantiated

	private NullConnector() {
		// do nothing: for JDO-compliance only
	}

	public NullConnector(Figure owner) {
		super(owner);
	}
}
