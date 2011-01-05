package org.bowlerframework.view.scalate

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 03/01/2011
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */

case class Layout(name: String, viewId: String = "doLayout", layoutModel: Option[Request => Option[List[Any]]] = None,
                  parentLayout: Option[Layout] = None, childLayouts: Option[List[Tuple2[String, Layout]]] = None)