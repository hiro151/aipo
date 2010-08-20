/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2008 Aimluck,Inc.
 * http://aipostyle.com/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aimluck.eip.util;

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.jetspeed.services.rundata.JetspeedRunData;

/**
 * ポートレット画面表示時に各アプリケーションのセッションを初期化するための ユーティリティクラスです。 <br />
 * 
 */
public class ALSessionInitializer {

  /** logger */
  @SuppressWarnings("unused")
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(ALSessionInitializer.class.getName());

  public void initializeSession(JetspeedRunData jdata, String peid) {

    // for debug
    // Hashtable ht = jdata.getUser().getTempStorage();
    // Enumeration keys = ht.keys();
    // System.out.println("===========================================");
    // for (; keys.hasMoreElements();) {
    // System.out.println("--------------------------------------------");
    // Object obj = keys.nextElement();
    // System.out.println("[key ] " + obj);
    // System.out.println("[Valeu] " + ht.get(obj));
    // }
    // end for debug

    String portletName =
      jdata.getProfile().getDocument().getEntryById(peid).getParent();

    if (portletName.startsWith("Schedule")) {
    } else if (portletName.startsWith("ToDo")) {
    } else if (portletName.startsWith("Note")) {
    } else if (portletName.startsWith("WebMail")) {
    } else if (portletName.startsWith("AddressBook")) {
    }
  }

}
