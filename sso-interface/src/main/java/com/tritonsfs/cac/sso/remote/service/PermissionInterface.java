/**
 * 
 */
package com.tritonsfs.cac.sso.remote.service;

import com.tritonsfs.cac.sso.remote.entity.PermissionDTO;

import java.util.Map;

/**
 * @author chenshunyu
 *
 */
public interface PermissionInterface {

	public PermissionDTO verifyPermission(String url, String redisUserKey);

	public Map<String, String> getUrlAndUri(String str);
}
