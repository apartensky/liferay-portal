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

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCProductVersionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService
 * @see com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceFactory
 *
 */
public class SCProductVersionLocalServiceUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion addSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.addSCProductVersion(scProductVersion);
	}

	public static void deleteSCProductVersion(long productVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		scProductVersionLocalService.deleteSCProductVersion(productVersionId);
	}

	public static void deleteSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		scProductVersionLocalService.deleteSCProductVersion(scProductVersion);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion getSCProductVersion(
		long productVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.getSCProductVersion(productVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion updateSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.updateSCProductVersion(scProductVersion);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		long userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		long userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		long userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void deleteProductVersion(long productVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		scProductVersionLocalService.deleteProductVersion(productVersionId);
	}

	public static void deleteProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion productVersion)
		throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		scProductVersionLocalService.deleteProductVersion(productVersion);
	}

	public static void deleteProductVersions(long productEntryId)
		throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		scProductVersionLocalService.deleteProductVersions(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersion(
		long productVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.getProductVersion(productVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersionByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.getProductVersionByDirectDownloadURL(directDownloadURL);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getProductVersions(
		long productEntryId, int start, int end)
		throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.getProductVersions(productEntryId,
			start, end);
	}

	public static int getProductVersionsCount(long productEntryId)
		throws com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.getProductVersionsCount(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean repoStoreArtifact,
		long[] frameworkVersionIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductVersionLocalService scProductVersionLocalService = SCProductVersionLocalServiceFactory.getService();

		return scProductVersionLocalService.updateProductVersion(productVersionId,
			version, changeLog, downloadPageURL, directDownloadURL,
			repoStoreArtifact, frameworkVersionIds);
	}
}