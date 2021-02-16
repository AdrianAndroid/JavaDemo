class Test {
    private int checkUidPermission(String permName, PackageParser.Package pkg,
                                   int uid,
                                   int callingUid) {
        final int callingUserId = UserHandle.getUserId(callingUid);
        final boolean isCallerInstantApp =
                mPackageManagerInt.getInstantAppPackageName(callingUid) != null;
        final boolean isUidInstantApp =
                mPackageManagerInt.getInstantAppPackageName(uid) != null;
        final int userId = UserHandle.getUserId(uid);
        if (!mUserManagerInt.exists(userId)) {
            return PackageManager.PERMISSION_DENIED;
        }
        if (pkg != null) {
            if (pkg.mSharedUserId != null) {
                if (isCallerInstantApp) {
                    return PackageManager.PERMISSION_DENIED;
                }
            } else if (mPackageManagerInt.filterAppAccess(pkg, callingUid,
                    callingUserId)) {
                return PackageManager.PERMISSION_DENIED;
            }
            final PermissionsState permissionsState =
                    ((PackageSetting) pkg.mExtras).getPermissionsState();
            if (permissionsState.hasPermission(permName, userId)) {
                if (isUidInstantApp) {
                    if (mSettings.isPermissionInstant(permName)) {
                        return PackageManager.PERMISSION_GRANTED;
                    }
                } else {
                    return PackageManager.PERMISSION_GRANTED;
                }
            }
// Special case: ACCESS_FINE_LOCATION permission includes
            ACCESS_COARSE_LOCATION
            if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(permName) &&
                    permissionsState
                            .hasPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                                    userId)) {
                return PackageManager.PERMISSION_GRANTED;
            }
        } else {
            ArraySet<String> perms = mSystemPermissions.get(uid);
            if (perms != null) {
                if (perms.contains(permName)) {
                    return PackageManager.PERMISSION_GRANTED;
                }
                if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(permName)
                        && perms
                        .contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    return PackageManager.PERMISSION_GRANTED;
                }
            }
        }
        return PackageManager.PERMISSION_DENIED;
    }


}