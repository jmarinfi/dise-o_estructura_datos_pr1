package uoc.ds.pr.util;

import static uoc.ds.pr.SportEvents4Club.*;

public class ResourceUtil {

    public static byte getFlag(byte ... flags) {
        byte res = 0;
        for (byte flag: flags) {
            res |= flag;
        }
        return res;
    }

    public static boolean hasPublicSecurity(byte resource) {
        return (resource & FLAG_PUBLIC_SECURITY) == FLAG_PUBLIC_SECURITY;
    }

    public static boolean hasPrivateSecurity(byte resource) {
        return (resource & FLAG_PRIVATE_SECURITY) == FLAG_PRIVATE_SECURITY;
    }

    public static boolean hasBasicLifeSupport(byte resource) {
        return (resource & FLAG_BASIC_LIFE_SUPPORT) == FLAG_BASIC_LIFE_SUPPORT;
    }

    public static boolean hasVolunteers(byte resource) {
        return (resource & FLAG_VOLUNTEERS) == FLAG_VOLUNTEERS;
    }

    public static boolean hasAllOpts(byte resource) {
        return (resource & FLAG_ALL_OPTS) == FLAG_ALL_OPTS;
    }
}
