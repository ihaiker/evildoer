package la.renzhen.basis.hash;

import java.util.*;

/**
 * @author <a href="mailto:wo@renzhen.la">haiker</a>
 * @version 2019-02-24 15:47
 */
public class ConsistentHash<S extends ServerNode> {

    /** 保存虚拟节点 */
    TreeMap<Integer, S> serverNodes;

    /** 每个物理节点对应的虚拟节点的个数 */
    private final int virtualNum;

    public final static int MOLDING = Integer.MAX_VALUE;

    public ConsistentHash() {
        this(5);
    }

    public ConsistentHash(final int virtualNum) {
        this.virtualNum = virtualNum;
    }

    public void addServerNodes(List<S> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            S node = nodes.get(i);
            this.addServerNode(node);
        }
    }

    /**
     * 添加一个负载服务器，注意：最好参数node实现
     *
     * @param node
     */
    public void addServerNode(S node) {
        if (null == serverNodes) {
            initNodes();
        }

        for (int i = 0; i < virtualNum; i++) {
            int hash = JumpConsistentHash.jumpConsistentHash(node.hashCode(i), MOLDING);
            serverNodes.put(hash, node);
        }
    }

    public Optional<Map.Entry<Integer, S>> getFirstServerNode() {
        if (null == serverNodes || serverNodes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(serverNodes.firstEntry());
    }

    public Optional<Map.Entry<Integer, S>> getLastServerNode() {
        if (null == serverNodes || serverNodes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(serverNodes.lastEntry());
    }

    public SortedMap<Integer, S> getServerNode(Object key) {
        if (serverNodes == null) {
            initNodes();
        }

        int hash = JumpConsistentHash.jumpConsistentHash(key, MOLDING);
        return serverNodes.tailMap(hash, true);
    }

    public Optional<S> getFirstServerNode(Object key, ServerSelector<S> selector) {
        SortedMap<Integer, S> head = getServerNode(key);
        if (!head.isEmpty()) {
            for (Map.Entry<Integer, S> serverNodeEntry : head.entrySet()) {
                if (selector == null || selector.select(serverNodeEntry.getValue())) {
                    return Optional.of(serverNodeEntry.getValue());
                }
            }
        }

        for (Map.Entry<Integer, S> serverNodeEntry : serverNodes.entrySet()) {
            if (selector == null || selector.select(serverNodeEntry.getValue())) {
                return Optional.of(serverNodeEntry.getValue());
            }
        }
        return Optional.empty();
    }


    protected void initNodes() {
        serverNodes = new TreeMap<>();
    }

}