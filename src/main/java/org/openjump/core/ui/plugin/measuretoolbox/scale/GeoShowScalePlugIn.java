/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * JUMP is Copyright (C) 2003 Vivid Solutions
 *
 * This program implements extensions to JUMP and is
 * Copyright (C) Stefan Steiniger.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * For more information, contact:
 * Stefan Steiniger
 * perriger@gmx.de
 */

package org.openjump.core.ui.plugin.measuretoolbox.scale;

import com.vividsolutions.jump.I18N;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.MenuNames;
import com.vividsolutions.jump.workbench.ui.images.IconLoader;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

/**
 * Original version by Vivid Solution
 *
 * modified:  03.06.2005
 * - initializes renderplugin
 * - plugin calculates the actual scale and draws the text
 *   (and a white rectangle around) in the map window
 *   all things are done in ShowScaleRenderer
 *
 * @author sstein
 * TODO how to put a mark on the menue item if tool is activated?

 * modified and renamed to work with Geographic coordinates (EPSG 4326)
 * @author Giuseppe Aruta - Sept 1th 2015
 */
public class GeoShowScalePlugIn extends AbstractPlugIn {

  private static final I18N i18n = I18N.getInstance("org.openjump.core.ui.plugin.measuretoolbox");

  public String Name = "Show scale bar";

  @Override
  public void initialize(PlugInContext context) {
    FeatureInstaller featureInstaller = context.getFeatureInstaller();

    featureInstaller.addMainMenuPlugin(this, new String[]{
            MenuNames.PLUGINS, i18n.get("Menu.Measure"),
            // I18NPlug.getI18N("Measure_geographic_coordinates")
            "Frame"}, Name, true, IconLoader.icon("show_scale_text.png"),
        getEnableCheck(context));
  }

  @Override
  public boolean execute(PlugInContext context) throws Exception {
    reportNothingToUndoYet(context);


    GeoShowScaleRenderer.setEnabled(
        !GeoShowScaleRenderer.isEnabled(context.getLayerViewPanel()),
        context.getLayerViewPanel());

    context.getLayerViewPanel().getRenderingManager()
        .render(GeoShowScaleRenderer.CONTENT_ID);

    return true;
  }

  public MultiEnableCheck getEnableCheck(PlugInContext context) {

    EnableCheckFactory checkFactory = context.getCheckFactory();

    return new MultiEnableCheck().add(checkFactory
        .createWindowWithSelectionManagerMustBeActiveCheck());
  }
}
