package com.sts.sinorita.svc.role.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

@Component
public class IPUtil {
    Logger log = LoggerFactory.getLogger(IPUtil.class);

    public String getLocalIP() {
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            ArrayList<String> ipv4Result = new ArrayList<>();
            ArrayList<String> ipv6Result = new ArrayList<>();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = enumeration.nextElement();
                Enumeration<InetAddress> en = networkInterface.getInetAddresses();
                while (en.hasMoreElements()) {
                    InetAddress address = en.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address instanceof java.net.Inet6Address) {
                            ipv6Result.add(normalizeHostAddress(address));
                            continue;
                        }
                        ipv4Result.add(normalizeHostAddress(address));
                    }
                }
            }
            if (!ipv4Result.isEmpty()) {
                for (String ip : ipv4Result) {
                    if (ip.startsWith("127.0") || ip.startsWith("192.168"))
                        continue;
                    return ip;
                }
                return ipv4Result.get(ipv4Result.size() - 1);
            }
            if (!ipv6Result.isEmpty())
                return ipv6Result.get(0);
            InetAddress localHost = InetAddress.getLocalHost();
            return normalizeHostAddress(localHost);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    private static String normalizeHostAddress(InetAddress localHost) {
        if (localHost instanceof java.net.Inet6Address)
            return "[" + localHost.getHostAddress() + "]";
        return localHost.getHostAddress();
    }

}
