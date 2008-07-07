/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchStatsUserException;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBStatsUserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBStatsUserPersistenceImpl extends BasePersistenceImpl
	implements MBStatsUserPersistence {
	public MBStatsUser create(long statsUserId) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();

		mbStatsUser.setNew(true);
		mbStatsUser.setPrimaryKey(statsUserId);

		return mbStatsUser;
	}

	public MBStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBStatsUser mbStatsUser = (MBStatsUser)session.get(MBStatsUserImpl.class,
					new Long(statsUserId));

			if (mbStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBStatsUser exists with the primary key " +
						statsUserId);
				}

				throw new NoSuchStatsUserException(
					"No MBStatsUser exists with the primary key " +
					statsUserId);
			}

			return remove(mbStatsUser);
		}
		catch (NoSuchStatsUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBStatsUser remove(MBStatsUser mbStatsUser)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(mbStatsUser);
			}
		}

		mbStatsUser = removeImpl(mbStatsUser);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(mbStatsUser);
			}
		}

		return mbStatsUser;
	}

	protected MBStatsUser removeImpl(MBStatsUser mbStatsUser)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(mbStatsUser);

			session.flush();

			return mbStatsUser;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(MBStatsUser.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(MBStatsUser mbStatsUser, boolean merge)</code>.
	 */
	public MBStatsUser update(MBStatsUser mbStatsUser)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(MBStatsUser mbStatsUser) method. Use update(MBStatsUser mbStatsUser, boolean merge) instead.");
		}

		return update(mbStatsUser, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbStatsUser the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbStatsUser is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public MBStatsUser update(MBStatsUser mbStatsUser, boolean merge)
		throws SystemException {
		boolean isNew = mbStatsUser.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(mbStatsUser);
				}
				else {
					listener.onBeforeUpdate(mbStatsUser);
				}
			}
		}

		mbStatsUser = updateImpl(mbStatsUser, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(mbStatsUser);
				}
				else {
					listener.onAfterUpdate(mbStatsUser);
				}
			}
		}

		return mbStatsUser;
	}

	public MBStatsUser updateImpl(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(mbStatsUser);
			}
			else {
				if (mbStatsUser.isNew()) {
					session.save(mbStatsUser);
				}
			}

			session.flush();

			mbStatsUser.setNew(false);

			return mbStatsUser;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(MBStatsUser.class.getName());
		}
	}

	public MBStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByPrimaryKey(statsUserId);

		if (mbStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBStatsUser exists with the primary key " +
					statsUserId);
			}

			throw new NoSuchStatsUserException(
				"No MBStatsUser exists with the primary key " + statsUserId);
		}

		return mbStatsUser;
	}

	public MBStatsUser fetchByPrimaryKey(long statsUserId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBStatsUser)session.get(MBStatsUserImpl.class,
				new Long(statsUserId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBStatsUser> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("messageCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<MBStatsUser> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<MBStatsUser>)result;
		}
	}

	public List<MBStatsUser> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MBStatsUser> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("messageCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<MBStatsUser> list = (List<MBStatsUser>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<MBStatsUser>)result;
		}
	}

	public MBStatsUser findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		List<MBStatsUser> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		int count = countByGroupId(groupId);

		List<MBStatsUser> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser);

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = (MBStatsUser)objArray[0];
			array[1] = (MBStatsUser)objArray[1];
			array[2] = (MBStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBStatsUser> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("messageCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<MBStatsUser> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<MBStatsUser>)result;
		}
	}

	public List<MBStatsUser> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<MBStatsUser> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("messageCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<MBStatsUser> list = (List<MBStatsUser>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<MBStatsUser>)result;
		}
	}

	public MBStatsUser findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		List<MBStatsUser> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBStatsUser exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		int count = countByUserId(userId);

		List<MBStatsUser> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBStatsUser exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser);

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = (MBStatsUser)objArray[0];
			array[1] = (MBStatsUser)objArray[1];
			array[2] = (MBStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByG_U(groupId, userId);

		if (mbStatsUser == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStatsUserException(msg.toString());
		}

		return mbStatsUser;
	}

	public MBStatsUser fetchByG_U(long groupId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "fetchByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("messageCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<MBStatsUser> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<MBStatsUser> list = (List<MBStatsUser>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<MBStatsUser> findByG_M(long groupId, int messageCount)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "findByG_M";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(messageCount)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("messageCount != ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("messageCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(messageCount);

				List<MBStatsUser> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<MBStatsUser>)result;
		}
	}

	public List<MBStatsUser> findByG_M(long groupId, int messageCount,
		int start, int end) throws SystemException {
		return findByG_M(groupId, messageCount, start, end, null);
	}

	public List<MBStatsUser> findByG_M(long groupId, int messageCount,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "findByG_M";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(messageCount),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("messageCount != ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("messageCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(messageCount);

				List<MBStatsUser> list = (List<MBStatsUser>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<MBStatsUser>)result;
		}
	}

	public MBStatsUser findByG_M_First(long groupId, int messageCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		List<MBStatsUser> list = findByG_M(groupId, messageCount, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("messageCount=" + messageCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser findByG_M_Last(long groupId, int messageCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		int count = countByG_M(groupId, messageCount);

		List<MBStatsUser> list = findByG_M(groupId, messageCount, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("messageCount=" + messageCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBStatsUser[] findByG_M_PrevAndNext(long statsUserId, long groupId,
		int messageCount, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		int count = countByG_M(groupId, messageCount);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("messageCount != ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("messageCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(messageCount);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbStatsUser);

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = (MBStatsUser)objArray[0];
			array[1] = (MBStatsUser)objArray[1];
			array[2] = (MBStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBStatsUser> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBStatsUser> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBStatsUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBStatsUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBStatsUser> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("messageCount DESC");
				}

				Query q = session.createQuery(query.toString());

				List<MBStatsUser> list = (List<MBStatsUser>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<MBStatsUser>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (MBStatsUser mbStatsUser : findByGroupId(groupId)) {
			remove(mbStatsUser);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (MBStatsUser mbStatsUser : findByUserId(userId)) {
			remove(mbStatsUser);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByG_U(groupId, userId);

		remove(mbStatsUser);
	}

	public void removeByG_M(long groupId, int messageCount)
		throws SystemException {
		for (MBStatsUser mbStatsUser : findByG_M(groupId, messageCount)) {
			remove(mbStatsUser);
		}
	}

	public void removeAll() throws SystemException {
		for (MBStatsUser mbStatsUser : findAll()) {
			remove(mbStatsUser);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByG_U(long groupId, long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "countByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByG_M(long groupId, int messageCount)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "countByG_M";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(messageCount)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("messageCount != ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(messageCount);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = MBStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = MBStatsUser.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.messageboards.model.MBStatsUser");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBStatsUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(MBStatsUserPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}