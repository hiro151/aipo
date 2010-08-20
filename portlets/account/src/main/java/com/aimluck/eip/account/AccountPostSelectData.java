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
package com.aimluck.eip.account;

import java.util.List;
import java.util.jar.Attributes;

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.account.util.AccountUtils;
import com.aimluck.eip.cayenne.om.account.EipMPost;
import com.aimluck.eip.common.ALAbstractSelectData;
import com.aimluck.eip.common.ALDBErrorException;
import com.aimluck.eip.common.ALEipUser;
import com.aimluck.eip.common.ALPageNotFoundException;
import com.aimluck.eip.modules.actions.common.ALAction;
import com.aimluck.eip.orm.query.ResultList;
import com.aimluck.eip.orm.query.SelectQuery;
import com.aimluck.eip.util.ALEipUtils;

/**
 * 部署の検索データを管理するためのクラスです。 <br />
 */
public class AccountPostSelectData extends
    ALAbstractSelectData<EipMPost, EipMPost> {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(AccountPostSelectData.class.getName());

  /**
   * 
   * @param action
   * @param rundata
   * @param context
   * @see com.aimluck.eip.common.ALAbstractSelectData#init(com.aimluck.eip.modules.actions.common.ALAction,
   *      org.apache.turbine.util.RunData, org.apache.velocity.context.Context)
   */
  @Override
  public void init(ALAction action, RunData rundata, Context context)
      throws ALPageNotFoundException, ALDBErrorException {
    String sort = ALEipUtils.getTemp(rundata, context, LIST_SORT_STR);
    if (sort == null || sort.equals("")) {
      ALEipUtils.setTemp(rundata, context, LIST_SORT_STR, ALEipUtils
        .getPortlet(rundata, context)
        .getPortletConfig()
        .getInitParameter("p1a-sort"));
    }

    super.init(action, rundata, context);
  }

  /**
   * @param rundata
   * @param context
   * @return
   * @see com.aimluck.eip.common.ALAbstractSelectData#selectList(org.apache.turbine.util.RunData,
   *      org.apache.velocity.context.Context)
   */
  @Override
  protected ResultList<EipMPost> selectList(RunData rundata, Context context) {
    try {
      SelectQuery<EipMPost> query = getSelectQuery(rundata, context);
      buildSelectQueryForListView(query);
      buildSelectQueryForListViewSort(query, rundata, context);

      return query.getResultList();
    } catch (Exception ex) {
      logger.error("Exception", ex);
      return null;
    }
  }

  /**
   * 検索条件を設定した SelectQuery を返します。 <BR>
   * 
   * @param rundata
   * @param context
   * @return
   */
  private SelectQuery<EipMPost> getSelectQuery(RunData rundata, Context context) {
    return new SelectQuery<EipMPost>(EipMPost.class);
  }

  /**
   * 詳細情報を取得します。
   * 
   * @param rundata
   * @param context
   * @return
   * @see com.aimluck.eip.common.ALAbstractSelectData#selectDetail(org.apache.turbine.util.RunData,
   *      org.apache.velocity.context.Context)
   */
  @Override
  protected EipMPost selectDetail(RunData rundata, Context context) {
    return AccountUtils.getEipMPost(rundata, context);
  }

  /**
   * @param obj
   * @return
   * @see com.aimluck.eip.common.ALAbstractSelectData#getResultData(java.lang.Object)
   */
  @Override
  protected Object getResultData(EipMPost record) {
    AccountPostResultData rd = new AccountPostResultData();
    rd.initField();
    rd.setPostId(record.getPostId().intValue());
    rd.setPostName(record.getPostName());
    return rd;
  }

  /**
   * @param obj
   * @return
   * @see com.aimluck.eip.common.ALAbstractSelectData#getResultDataDetail(java.lang.Object)
   */
  @Override
  protected Object getResultDataDetail(EipMPost record) {
    AccountPostResultData rd = new AccountPostResultData();
    rd.initField();
    rd.setPostId(record.getPostId().intValue());
    rd.setPostName(record.getPostName());
    rd.setZipcode(record.getZipcode());
    rd.setAddress(record.getAddress());
    rd.setOutTelephone(record.getOutTelephone());
    rd.setInTelephone(record.getInTelephone());
    rd.setFaxNumber(record.getFaxNumber());
    rd.setGroupName(record.getGroupName());
    return rd;
  }

  /**
   * @return
   * @see com.aimluck.eip.common.ALAbstractSelectData#getColumnMap()
   */
  @Override
  protected Attributes getColumnMap() {
    Attributes map = new Attributes();
    map.putValue("post_name", EipMPost.POST_NAME_PROPERTY);
    return map;
  }

  /**
   * 
   * @param postid
   * @return
   */
  public List<ALEipUser> getMemberList(long postid) {
    return ALEipUtils.getUsersFromPost((int) postid);
  }

  /**
   * あるグループに所属するメンバーのリストを取得します
   * 
   * @param postid
   * @return
   */
  public List<ALEipUser> getMemberListByGroup(String groupname) {
    return ALEipUtils.getUsers(groupname);
  }
}
