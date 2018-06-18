package la.renzhen.basis.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 网络工具类
 */
@Slf4j
public class Nets {

    public static final String LOCALHOST = "127.0.0.1";
    public static final String ANY_HOST = "0.0.0.0";

    //随机端口结束位置
    private static final int RND_PORT_START = 1000;
    //随机端口开始位置
    private static final int RND_PORT_RANGE = Character.MAX_VALUE - RND_PORT_START;

    private static final int MIN_PORT = RND_PORT_START;
    private static final int MAX_PORT = RND_PORT_RANGE + RND_PORT_START;

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    private static final Pattern IP_HOST_PATTERN = Pattern.compile("^\\d{1,3}(\\.\\d{1,3}){3}\\:\\d{1,5}$");


    private static volatile InetAddress LOCAL_ADDRESS_CACHE = null;

    /**
     * 获取一个随机可用的端口<br>
     * 次随机端口在10000~65535之间
     *
     * @return 端口号
     */
    public static int randomAvailablePort() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket();
            ss.bind(null);
            return ss.getLocalPort();
        } catch (IOException e) {
            return 0;
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 随机获取一个端口，并且此端口开始于port
     *
     * @param port 端口开始位置
     * @return 随机端口
     */
    public static int getAvailablePort(int port) {
        if (!isValidPort(port)) {
            return randomAvailablePort();
        }
        for (int i = port; i < MAX_PORT; i++) {
            ServerSocket ss = null;
            try {
                ss = new ServerSocket(i);
                return i;
            } catch (IOException e) {
                // continue
            } finally {
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 检测是否是合理的端口
     *
     * @param port 判断的端口
     * @return 是否可用
     */
    public static boolean isValidPort(int port) {
        return (port > MIN_PORT) && (port <= MAX_PORT);
    }

    /**
     * 检测是否是一个可用户IP:PORT书写方式
     *
     * @param address 是否地址可用
     * @return T/F
     */
    public static boolean isValidAddress(String address) {
        return IP_HOST_PATTERN.matcher(address).matches();
    }

    /**
     * 检查是否是本地IP
     *
     * @param host IP地址
     * @return T/F
     */
    public static boolean isLocalHost(String host) {
        return host != null
                && (LOCALHOST.equals(host)
                || host.equalsIgnoreCase("localhost"));
    }

    /**
     * 是否并绑定到任何IP
     *
     * @param host address
     * @return T/F
     */
    public static boolean isAnyHost(String host) {
        return ANY_HOST.equals(host);
    }

    /**
     * 检查给定地址是否是无效的可访问地址
     *
     * @param host 地址
     * @return true
     */
    public static boolean isInvalidHost(String host) {
        return host == null
                || host.length() == 0
                || host.equalsIgnoreCase("localhost")
                || host.equals("0.0.0.0")
                || !(IP_PATTERN.matcher(host).matches());
    }

    /**
     * 检查是否是一个有效的可访问地址
     *
     * @param host host地址
     * @return true/false
     */
    public static boolean isValidHost(String host) {
        return !isInvalidHost(host);
    }

    public static InetSocketAddress getLocalSocketAddress(String host, int port) {
        return isAnyHost(host) ?
                new InetSocketAddress(port) : new InetSocketAddress(host, port);
    }

    /**
     * 是否是一个可用的外部地址
     *
     * @param address 地址
     * @return T/F
     */
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress())
            return false;

        String name = address.getHostAddress();
        return (name != null
                && !ANY_HOST.equals(name)
                && !LOCALHOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }

    /**
     * 获取本地地址
     *
     * @return 本机地址（如果存在多块网卡给出第一个网卡的地址）
     */
    public static String getLocalHost() {
        return getLocalHost(null);
    }

    /**
     * @param prefix 本地地址开始段
     * @return ip地址
     */
    public static String getLocalHost(String prefix) {
        InetAddress address = getLocalAddress(prefix);
        return address == null ? LOCALHOST : address.getHostAddress();
    }

    public static InetAddress getLocalAddress() {
        return getLocalAddress(null);
    }

    /**
     * 遍历本地网卡，返回第一个合理的IP。
     *
     * @param prefix 前段
     * @return 本地网卡IP
     */
    public static InetAddress getLocalAddress(String prefix) {
        if (LOCAL_ADDRESS_CACHE != null)
            return LOCAL_ADDRESS_CACHE;

        InetAddress localAddress = getLocalAddress0(prefix);
        LOCAL_ADDRESS_CACHE = localAddress;
        return localAddress;
    }

    /**
     * 是否是真是网卡
     *
     * @param address 地址
     * @return T/F
     */
    private static boolean isRealNet(InetAddress address) {
        boolean link = address.isLinkLocalAddress();
        boolean loop = address.isLoopbackAddress();
        boolean site = address.isSiteLocalAddress();
        return !link & !loop & site;
    }

    private static boolean isPrefix(InetAddress address, String prefix) {
        if (prefix == null || "".equals(prefix.trim())) {
            return true;
        }
        String[] items = prefix.trim().split("[;,\\|]");
        for (String item : items) {
            if (address.getHostAddress().startsWith(item)) {
                return true;
            }
        }
        return false;
    }

    private static InetAddress getLocalAddress0(String prefix) {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isRealNet(localAddress) && isValidAddress(localAddress) && isPrefix(localAddress, prefix)) {
                return localAddress;
            }
        } catch (Throwable e) {
            log.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        String name = network.getName();
                        if (name.startsWith("docker") // docker 虚拟网络
                                || name.startsWith("w")) {//虚拟网络
                            continue;
                        }
                        if (network.isVirtual() || network.isLoopback() || !network.isUp()) {
                            continue;
                        }
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isRealNet(address) && isValidAddress(address) && isPrefix(address, prefix)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
                                    log.warn("Failed to retriving ip address, " + e.getMessage(), e);
                                }
                            }
                        }
                    } catch (Throwable e) {
                        log.warn("Failed to retriving ip address, " + e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            log.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }
        log.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    private static final Map<String, String> hostNameCache = new HashMap<>();

    public static String getHostName(String address) {
        try {
            int i = address.indexOf(':');
            if (i > -1) {
                address = address.substring(0, i);
            }
            String hostname = hostNameCache.get(address);
            if (hostname != null && hostname.length() > 0) {
                return hostname;
            }
            InetAddress inetAddress = InetAddress.getByName(address);
            if (inetAddress != null) {
                hostname = inetAddress.getHostName();
                hostNameCache.put(address, hostname);
                return hostname;
            }
        } catch (Throwable e) {
            // ignore
        }
        return address;
    }

    /**
     * @param hostName hostname
     * @return ip address or hostName if UnknownHostException
     */
    public static String getIpByHost(String hostName) {
        try {
            return InetAddress.getByName(hostName).getHostAddress();
        } catch (UnknownHostException e) {
            return hostName;
        }
    }


    public static String hostname() {
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            return getHostNameForWindow();
        }
        return getHostNameForLinux();
    }

    private static String getHostNameForLinux() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }

    private static String getHostNameForWindow() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLinux();
        }
    }

    public static String toAddressString(InetSocketAddress address) {
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }

    public static InetSocketAddress toAddress(String address) {
        int i = address.indexOf(':');
        String host;
        int port;
        if (i > -1) {
            host = address.substring(0, i);
            port = Integer.parseInt(address.substring(i + 1));
        } else {
            host = address;
            port = 0;
        }
        return new InetSocketAddress(host, port);
    }

    public static String toURL(String protocol, String host, int port, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(protocol).append("://");
        sb.append(host).append(':').append(port);
        if (path.charAt(0) != '/')
            sb.append('/');
        sb.append(path);
        return sb.toString();
    }

}